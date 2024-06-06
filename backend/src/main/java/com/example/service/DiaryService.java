package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.DiaryStatusEnum;
import com.example.entity.SortOption;
import com.example.entity.dto.Diary;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface DiaryService {
    String createDiary(String title, String content, String studentName,String siteName, DiaryStatusEnum status);
    String updateDiary(String title, String content, String studentName,Long diaryId) throws JsonProcessingException;
    boolean deleteDiary(Long id, String studentName);
    List<Diary> getDiariesByStudentName(String studentName);
    Diary getDiary(Long diaryId);
    boolean rateDiary(Long diaryId, double score);
    IPage<Diary> findDiaryByTitleAndSite(String title, String siteName, String studentName,SortOption sortOption, int page, int size, Boolean isPublished);
}
