package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.SortOption;
import com.example.entity.dto.Diary;

import java.util.List;

public interface DiaryService {
    Diary createDiary(Diary diary);
    Diary updateDiary(Diary diary);
    void deleteDiary(Long id);
    List<Diary> findDiaryByTitle(String title, SortOption sortOption);
}
