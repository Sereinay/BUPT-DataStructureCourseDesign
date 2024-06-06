package com.example.service.impl;

import com.example.entity.dto.Food;
import com.example.service.FoodService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class FoodServiceImplTest {

    @Resource
    FoodService foodService;


    /**
     * 测试包含非数字字符的字符串
     */
    @Test
    void testFindFoodByType_invalidString() {
        System.out.println("测试包含有效的查询类型");
        List<Food> list = foodService.findFoodByType("川菜", "name", "hot");
        assertTrue(list != null && list.size() <= 10, "无效查询类型应返回长度小于等于10的列表");
        list.forEach(System.out::println);
    }

    /**
     * 测试有效的name和orderBy字符串
     */
    @Test
    void testFindFoodByType_validString() {
        System.out.println("测试有效的name和orderBy字符串");
        List<Food> list = foodService.findFoodByType("testName", "name", "rating");
        assertTrue(list != null && list.size() <= 10, "有效查询应返回长度小于等于10的列表");
        list.forEach(System.out::println);
    }

    /**
     * 测试没有传入orderBy参数
     */
    @Test
    void testFindFoodByType_noOrderBy() {
        System.out.println("测试没有传入orderBy参数");
        List<Food> list = foodService.findFoodByType("testName", "name", null);
        assertTrue(list != null && list.size() <= 10, "没有传入orderBy参数应返回长度小于等于10的列表");
        list.forEach(System.out::println);
    }

    /**
     * 测试没有传入type参数
     */
    @Test
    void testFindFoodByType_noType() {
        System.out.println("测试没有传入type参数");
        List<Food> list = foodService.findFoodByType("testName", null, "rating");
        assertTrue(list != null && list.size() <= 10, "没有传入type参数应返回长度小于等于10的列表");
        list.forEach(System.out::println);
    }

    /**
     * 测试没有传入name参数
     */
    @Test
    void testFindFoodByType_noName() {
        System.out.println("测试没有传入name参数");
        List<Food> list = foodService.findFoodByType(null, "name", "rating");
        assertTrue(list != null && list.size() <= 10, "没有传入name参数应返回长度小于等于10的列表");
        list.forEach(System.out::println);
    }

}