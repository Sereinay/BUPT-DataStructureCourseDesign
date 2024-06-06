package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.Food;
import org.springframework.stereotype.Service;

import java.util.List;


public interface FoodService{
    List<Food> findFoodByType(String name, String type, String orderBy);
}
