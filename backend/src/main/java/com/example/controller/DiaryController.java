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
    public RestBean<String> createDiary(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String studentName,
            @RequestParam String siteName,
            @RequestParam DiaryStatusEnum status) {
        String result = diaryService.createDiary(title, content, studentName, siteName, status);
        return RestBean.success(result);
    }

    @PutMapping("/update")
    public RestBean<String> updateDiary(
            @RequestParam Long id,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam DiaryStatusEnum status,
            @RequestParam String studentName) throws JsonProcessingException {
        String result = diaryService.updateDiary(title, content, status, studentName, id);
        return RestBean.success(result);
    }

    @PutMapping("/rate")
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
            @RequestParam String studentName,
            @RequestParam SortOption sortOption,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam boolean isPublished) {
        IPage<Diary> diaries = diaryService.findDiaryByTitleAndSite(title, siteName, studentName, sortOption, page, size,isPublished);
        return RestBean.success(diaries);
    }
}