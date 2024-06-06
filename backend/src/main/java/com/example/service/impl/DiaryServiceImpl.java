package com.example.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.SortOption;
import com.example.entity.dto.Diary;
import com.example.entity.DiaryStatusEnum;
import com.example.mapper.DiaryMapper;
import com.example.service.DiaryService;
import com.example.utils.CustomSortUtil;
import com.example.utils.HuffmanUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiaryServiceImpl extends ServiceImpl<DiaryMapper, Diary> implements DiaryService {
    @Resource
    private DiaryMapper diaryMapper;

    @Resource
    private CustomSortUtil customSort;

    @Override
    public String createDiary(Diary diary, DiaryStatusEnum status) {
        try {
            diary.setCreateTime(LocalDateTime.now());
            diary.setUpdateTime(LocalDateTime.now());
            diary.setStatus(status);
            // 压缩内容
            compressContent(diary);
            // 插入日记
            if(status == DiaryStatusEnum.PUBLISHED){
                diary.setRating((double) 0);
                diary.setRateNum(0);
                diary.setPopularity(0);
            }
            diaryMapper.insert(diary);
            return "日记创建成功！";
        } catch (Exception e) {
            return "创建日记失败: " + e.getMessage();
        }
    }

    @Override
    public String updateDiary(Diary diary, String studentName){
        try {
            if (!diary.getStudentName().equals(studentName)) {
                return "只能修改自己的日记！！";
            }
            diary.setUpdateTime(LocalDateTime.now());
            // 压缩内容
            compressContent(diary);
            diaryMapper.updateById(diary);
            return "日记修改成功";
        } catch (Exception e){
            return "修改失败：" + e.getMessage();
        }
    }

    @Override
    public boolean rateDiary(Long diaryId, double score) {
        Diary diary = this.getById(diaryId);

        int newRateNum = diary.getRateNum() + 1;
        double newRating = diary.getRating() + score;

        diary.setRateNum(newRateNum);
        diary.setRating(newRating);

        return this.updateById(diary);
    }

    @Override
    public boolean deleteDiary(Long id, String studentName) {
        Diary diary = diaryMapper.selectById(id);
        if (diary == null || !diary.getStudentName().equals(studentName)) {
            // 日记不存在或者用户没有权限
            return false;
        }
        int result = diaryMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public List<Diary> getDiariesByStudentName(String studentName) {
        QueryWrapper<Diary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentName", studentName);
        queryWrapper.orderByDesc("updateTime");
        return diaryMapper.selectList(queryWrapper);
    }
    @Override
    public Diary getDiary(Long diaryId) {
        try {
            Diary diary = getById(diaryId);
            diary.setPopularity(diary.getPopularity() + 10);
            diaryMapper.updateById(diary);
            diary.setContent(decompressContent(diary));
            return diary;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public IPage<Diary> findDiaryByTitleAndSite(String title, String siteName, SortOption sortOption, int page, int size) {
        QueryWrapper<Diary> queryWrapper = new QueryWrapper<>();

        // 判断siteName是否为空，若为空则不限制siteName
        if (siteName != null && !siteName.isEmpty()) {
            queryWrapper.eq("siteName", siteName);
        }

        // 判断关键词，若为空则查看该景点所有标题的日记
        if (title != null && !title.isEmpty()) {
            queryWrapper.like("title", title);
        }

        // 仅返回已发布的日记
        queryWrapper.eq("status", DiaryStatusEnum.PUBLISHED);

        // 分页功能
        Page<Diary> pageObj = new Page<>(page, size);
        IPage<Diary> diaryPage = diaryMapper.selectPage(pageObj, queryWrapper);

        // 自定义排序
        List<Diary> diaries = diaryPage.getRecords();
        customSort.sort(diaries, sortOption);

        // 将排序后的结果重新设置到分页对象中
        pageObj.setRecords(diaries);

        return diaryPage;
    }

    private void compressContent(Diary diary) throws IOException {
        Map<String, Object> compressedData = HuffmanUtil.compress(diary.getContent());
        byte[] encodedBytes = (byte[]) compressedData.get("encodedBytes");
        String encodedText = Base64.getEncoder().encodeToString(encodedBytes);
        String huffmanCode = JSON.toJSONString(compressedData.get("huffmanCode"));
        int padding = (int) compressedData.get("padding");  // 获取 padding 参数

        // 设置压缩后的内容为Base64字符串
        diary.setContent(encodedText);
        diary.setCode(huffmanCode);
        diary.setPadding(padding);  // 保存 padding 信息
    }

    public String decompressContent(Diary diary) throws IOException {
        String encodedText = diary.getContent();
        byte[] encodedBytes = Base64.getDecoder().decode(encodedText);
        int padding = diary.getPadding();  // 获取 padding 参数

        // 使用 fastjson 反序列化哈夫曼编码表
        Map<String, String> huffmanCodeAsString = JSON.parseObject(diary.getCode(), new TypeReference<Map<String, String>>() {});

        Map<Character, String> huffmanCode = new HashMap<>();

        // 将键从 String 转换为 Character
        for (Map.Entry<String, String> entry : huffmanCodeAsString.entrySet()) {
            huffmanCode.put(entry.getKey().charAt(0), entry.getValue());
        }

        return HuffmanUtil.decompress(encodedBytes, huffmanCode, padding);  // 传递 padding 参数
    }

}