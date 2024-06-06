package com.example.controller;
import com.example.entity.RestBean;
import com.example.entity.dto.Food;
import com.example.service.FoodService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Resource
    private FoodService foodService;

    @GetMapping("/findFoodByType")
    public RestBean<List<Food>> findFoodByType(@RequestParam String name,
                                               @RequestParam String type,
                                               @RequestParam String orderBy) {
        List<Food> foods = foodService.findFoodByType(name, type, orderBy);
        return RestBean.success(foods);
    }
}
