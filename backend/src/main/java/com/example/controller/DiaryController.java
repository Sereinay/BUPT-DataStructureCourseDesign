package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.SortOption;
import com.example.entity.dto.Diary;
import com.example.entity.DiaryStatusEnum;
import com.example.service.DiaryService;
import com.example.entity.RestBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    @Resource
    private DiaryService diaryService;

    @PostMapping("/create")
    public RestBean<String> createDiary(@RequestBody Diary diary, @RequestParam DiaryStatusEnum status) {
        String result = diaryService.createDiary(diary, status);
        return RestBean.success(result);
    }

    @PutMapping("/update")
    public RestBean<String> updateDiary(@RequestParam Long id, @RequestBody Diary diary, @RequestParam String studentName) throws JsonProcessingException {
        diary.setId(id);
        String result = diaryService.updateDiary(diary, studentName);
        return RestBean.success(result);
    }

    @PostMapping("/rate")
    public RestBean<Boolean> rateDiary(@RequestParam Long id, @RequestParam double score) {
        boolean result = diaryService.rateDiary(id, score);
        return RestBean.success(result);
    }

    @DeleteMapping("/delete")
    public RestBean<Boolean> deleteDiary(@RequestParam Long id, @RequestParam String studentName) {
        boolean result = diaryService.deleteDiary(id,studentName);
        return RestBean.success(result);
    }

    @GetMapping("/student")
    public RestBean<List<Diary>> getDiariesByStudentName(@RequestParam String studentName) {
        List<Diary> diaries = diaryService.getDiariesByStudentName(studentName);
        return RestBean.success(diaries);
    }

    @GetMapping
    public RestBean<Diary> getDiary(@RequestParam Long id) {
        Diary diary = diaryService.getDiary(id);
        return RestBean.success(diary);
    }

    @GetMapping("/search")
    public RestBean<IPage<Diary>> findDiaryByTitleAndSite(
            @RequestParam String title,
            @RequestParam String siteName,
            @RequestParam SortOption sortOption,
            @RequestParam int page,
            @RequestParam int size) {
        IPage<Diary> diaries = diaryService.findDiaryByTitleAndSite(title, siteName, sortOption, page, size);
        return RestBean.success(diaries);
    }
}