package com.example.service.impl;

import com.example.entity.dto.Node;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class NodeServiceImplTest {

    @Resource
    NodeServiceImpl nodeService;


    @Test
    public void testFindNodeByIdWithDemo() {
        System.out.println("测试Demo");
        List<Node> nodes = nodeService.findNodeById(Arrays.asList(new  Integer[]{73, 1, 14, 47}));
        if (nodes == null || nodes.isEmpty()) System.out.println("没有找到！");
        else {
            System.out.println("找到的节点为：");
            nodes.forEach(System.out::println);
        }
    }

    /**
     * 测试空列表
     */
    @Test
    public void testFindNodeByIdWithEmptyList() {
        System.out.println("测试空列表");
        List<Node> nodes = nodeService.findNodeById(Arrays.asList());
        if (nodes == null || nodes.isEmpty()) System.out.println("没有找到！");
        else {
            System.out.println("找到的节点为：");
            nodes.forEach(System.out::println);
        }
    }

    /**
     * 测试包含无效ID的列表
     */
    @Test
    public void testFindNodeByIdWithInvalidId() {
        System.out.println("测试包含无效ID的列表");
        List<Node> nodes = nodeService.findNodeById(Arrays.asList(100));
        if (nodes == null || nodes.isEmpty()) System.out.println("没有找到！");
        else {
            System.out.println("找到的节点为：");
            nodes.forEach(System.out::println);
        }
    }

    /**
     * 测试包含重复ID的列表
     */
    @Test
    public void testFindNodeByIdWithDuplicateIds() {
        System.out.println("测试包含重复ID的列表");
        List<Node> nodes = nodeService.findNodeById(Arrays.asList(1, 1));
        if (nodes == null || nodes.isEmpty()) System.out.println("没有找到！");
        else {
            System.out.println("找到的节点为：");
            nodes.forEach(System.out::println);
        }
    }

    /**
     * 测试边界值ID 0
     */
    @Test
    public void testFindNodeByIdWithBoundaryId0() {
        System.out.println("测试边界值ID 0");
        List<Node> nodes = nodeService.findNodeById(Arrays.asList(0));
        if (nodes == null || nodes.isEmpty()) System.out.println("没有找到！");
        else {
            System.out.println("找到的节点为：");
            nodes.forEach(System.out::println);
        }
    }

    /**
     * 测试边界值ID 99
     */
    @Test
    public void testFindNodeByIdWithBoundaryId99() {
        System.out.println("测试边界值ID 99");
        List<Node> nodes = nodeService.findNodeById(Arrays.asList(99));
        if (nodes == null || nodes.isEmpty()) System.out.println("没有找到！");
        else {
            System.out.println("找到的节点为：");
            nodes.forEach(System.out::println);
        }
    }

    /**
     * 测试包含多个有效ID的列表
     */
    @Test
    public void testFindNodeByIdWithMultipleValidIds() {
        System.out.println("测试包含多个有效ID的列表");
        List<Node> nodes = nodeService.findNodeById(Arrays.asList(1, 2, 3));
        if (nodes == null || nodes.isEmpty()) System.out.println("没有找到！");
        else {
            System.out.println("找到的节点为：");
            nodes.forEach(System.out::println);
        }
    }

    /**
     * 测试包含混合ID（有效和无效）的列表
     */
    @Test
    public void testFindNodeByIdWithMixedIds() {
        System.out.println("测试包含混合ID（有效和无效）的列表");
        List<Node> nodes = nodeService.findNodeById(Arrays.asList(1, 101, 2));
        if (nodes == null || nodes.isEmpty()) System.out.println("没有找到！");
        else {
            System.out.println("找到的节点为：");
            nodes.forEach(System.out::println);
        }
    }

    /**
     * 测试包含负数ID的列表
     */
    @Test
    public void testFindNodeByIdWithNegativeIds() {
        System.out.println("测试包含负数ID的列表");
        List<Node> nodes = nodeService.findNodeById(Arrays.asList(-1));
        if (nodes == null || nodes.isEmpty()) System.out.println("没有找到！");
        else {
            System.out.println("找到的节点为：");
            nodes.forEach(System.out::println);
        }
    }

}