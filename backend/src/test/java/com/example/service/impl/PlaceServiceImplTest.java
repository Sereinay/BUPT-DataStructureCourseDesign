package com.example.service.impl;

import com.example.entity.dto.Place;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
class PlaceServiceImplTest {

    @Resource
    PlaceServiceImpl placeService;

    @BeforeEach
    void setUp() {
        System.out.println("测试开始");
    }

    @Test
    void testFindPlaceByName_WithUniversity() {
        List<Place> placeList = placeService.findPlaceByName("大学");
        assertNotNull(placeList, "结果列表不应为空");
        assertFalse(placeList.isEmpty(), "结果列表不应为空");
        placeList.forEach(System.out::println);
    }

    @Test
    void testFindPlaceByName_WithSchool() {
        List<Place> placeList = placeService.findPlaceByName("学校");
        assertNotNull(placeList, "结果列表不应为空");
        assertFalse(placeList.isEmpty(), "结果列表不应为空");
        placeList.forEach(System.out::println);
    }

    @Test
    void testFindPlaceByName_WithYongHeGong() {
        List<Place> placeList = placeService.findPlaceByName("雍和宫");
        assertNotNull(placeList, "结果列表不应为空");
        assertFalse(placeList.isEmpty(), "结果列表不应为空");
        placeList.forEach(System.out::println);
    }

    @Test
    void testFindPlaceByName_WithGuGong() {
        List<Place> placeList = placeService.findPlaceByName("故宫");
        assertNotNull(placeList, "结果列表不应为空");
        assertFalse(placeList.isEmpty(), "结果列表不应为空");
        placeList.forEach(System.out::println);
    }

    @Test
    void testFindPlaceByName_WithTianTan() {
        List<Place> placeList = placeService.findPlaceByName("天坛");
        assertNotNull(placeList, "结果列表不应为空");
        assertFalse(placeList.isEmpty(), "结果列表不应为空");
        placeList.forEach(System.out::println);
    }

    @Test
    void testFindPlaceByName_WithTiaAnMen() {
        List<Place> placeList = placeService.findPlaceByName("天安门");
        assertNotNull(placeList, "结果列表不应为空");
        assertFalse(placeList.isEmpty(), "结果列表不应为空");
        placeList.forEach(System.out::println);
    }


    @Test
    void testFindPlaceByName_WithEmptyString() {
        List<Place> placeList = placeService.findPlaceByName("");
        assertNotNull(placeList, "结果列表不应为空");
        placeList.forEach(System.out::println);
    }

    @Test
    void testFindPlaceByName_WithNull() {
        List<Place> placeList = placeService.findPlaceByName(null);
        placeList.forEach(System.out::println);
    }

    @Test
    void testFindPlaceByInformation() {
        List<Place> placeList = placeService.findPlaceByInformation("旅游");
        assertNotNull(placeList, "结果列表不应为空");
        assertFalse(placeList.isEmpty(), "结果列表不应为空");
        placeList.forEach(System.out::println);
    }
    @Test
    void testFindPlaceByAncientChina() {
        List<Place> placeList = placeService.findPlaceByInformation("中国古代");
        assertNotNull(placeList, "结果列表不应为空");
        assertFalse(placeList.isEmpty(), "结果列表不应为空");
        placeList.forEach(System.out::println);
    }

    @Test
    void testFindPlaceByRelaxation() {
        List<Place> placeList = placeService.findPlaceByInformation("放松休闲");
        assertNotNull(placeList, "结果列表不应为空");
        assertFalse(placeList.isEmpty(), "结果列表不应为空");
        placeList.forEach(System.out::println);
    }

    @Test
    void testFindPlaceByPhilosophy() {
        List<Place> placeList = placeService.findPlaceByInformation("哲学思想");
        assertNotNull(placeList, "结果列表不应为空");
        assertFalse(placeList.isEmpty(), "结果列表不应为空");
        placeList.forEach(System.out::println);
    }

    @Test
    void testFindPlaceByPark() {
        List<Place> placeList = placeService.findPlaceByInformation("公园");
        assertNotNull(placeList, "结果列表不应为空");
        assertFalse(placeList.isEmpty(), "结果列表不应为空");
        placeList.forEach(System.out::println);
    }

    @Test
    void testFindPlaceByScenic() {
        List<Place> placeList = placeService.findPlaceByInformation("赏景踏青");
        assertNotNull(placeList, "结果列表不应为空");
        assertFalse(placeList.isEmpty(), "结果列表不应为空");
        placeList.forEach(System.out::println);
    }

    @Test
    void testFindPlaceByHistory() {
        List<Place> placeList = placeService.findPlaceByInformation("历史文化");
        assertNotNull(placeList, "结果列表不应为空");
        assertFalse(placeList.isEmpty(), "结果列表不应为空");
        placeList.forEach(System.out::println);
    }

    @Test
    void testFindPlaceByArchitecture() {
        List<Place> placeList = placeService.findPlaceByInformation("建筑风格");
        assertNotNull(placeList, "结果列表不应为空");
        assertFalse(placeList.isEmpty(), "结果列表不应为空");
        placeList.forEach(System.out::println);
    }

    @Test
    void testFindPlaceByHumanity() {
        List<Place> placeList = placeService.findPlaceByInformation("人文情怀");
        assertNotNull(placeList, "结果列表不应为空");
        assertFalse(placeList.isEmpty(), "结果列表不应为空");
        placeList.forEach(System.out::println);
    }

    @Test
    void testFindPlaceByReligion() {
        List<Place> placeList = placeService.findPlaceByInformation("宗教信仰");
        assertNotNull(placeList, "结果列表不应为空");
        assertFalse(placeList.isEmpty(), "结果列表不应为空");
        placeList.forEach(System.out::println);
    }



}