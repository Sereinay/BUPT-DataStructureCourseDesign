package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.SortOption;
import com.example.entity.dto.Diary;
import com.example.mapper.DiaryMapper;
import com.example.service.DiaryService;
import jakarta.annotation.Resource;

import java.time.LocalDateTime;
import java.util.List;

public class DiaryServiceImpl extends ServiceImpl<DiaryMapper, Diary> implements DiaryService {
    @Resource
    private DiaryMapper diaryMapper;

    @Override
    public Diary createDiary(Diary diary) {
        diary.setCreateTime(LocalDateTime.now());
        diary.setUpdateTime(LocalDateTime.now());
        diaryMapper.insert(diary);
        return diary;
    }

    @Override
    public Diary updateDiary(Diary diary) {
        diary.setUpdateTime(LocalDateTime.now());
        diaryMapper.updateById(diary);
        return diary;
    }

    @Override
    public void deleteDiary(Long id) {
        diaryMapper.deleteById(id);
    }

    @Override
    public List<Diary> findDiaryByTitle(String title, SortOption sortOption) {
        QueryWrapper<Diary> queryWrapper = new QueryWrapper<>();
        if (title == null || title.isEmpty()) {
            // 如果输入为空或null，则按照用户选择的排序方式降序排列
            if (sortOption == SortOption.RATING) {
                queryWrapper.orderByDesc("rating");
            } else {
                queryWrapper.orderByDesc("popularity");
            }
        } else {
            // 如果输入不为空，则进行模糊匹配，并按照用户选择的排序方式降序排列
            queryWrapper.like("title", title);
            if (sortOption == SortOption.RATING) {
                queryWrapper.orderByDesc("rating");
            } else {
                queryWrapper.orderByDesc("popularity");
            }
        }
        // 执行查询并获取所有结果

        return diaryMapper.selectList(queryWrapper);
    }
}