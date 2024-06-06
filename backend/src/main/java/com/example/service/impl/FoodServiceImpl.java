package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.Food;
import com.example.mapper.FoodMapper;
import com.example.service.FoodService;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {
    @Override
    public List<Food> findFoodByType(String name, String type, String orderBy) {
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();

        // 构建查询条件
        if (name == null || name.isEmpty()) {
            // 如果输入为空或null，则按照rating降序排列，并限制结果为10条
            queryWrapper.orderByDesc(orderBy != null ? orderBy : "rating").last("LIMIT 10");
        } else {
            // 根据type选择字段进行模糊匹配
            switch (type != null ? type : "") {
                case "serious":
                    queryWrapper.like("serious", name);
                    break;
                case "windowName":
                    queryWrapper.like("windowName", name);
                    break;
                case "name":
                default:
                    queryWrapper.like("name", name);
                    break;
            }

            // 根据orderBy选择排序方式，默认使用rating字段
            queryWrapper.orderByDesc(orderBy != null ? orderBy : "rating").last("LIMIT 10");
        }

        List<Food> resultList = list(queryWrapper);

        // 如果结果数量不足10个，则需要用默认列表填充
        if (resultList.size() < 10) {
            // 获取所有Food对象，并按orderBy降序排列，默认使用rating字段
            List<Food> allFoods = list(new QueryWrapper<Food>().orderByDesc(orderBy != null ? orderBy : "rating"));
            // 如果所有Food对象的数量不足10个，则直接返回所有Food对象
            if (allFoods.size() <= 10) {
                return allFoods;
            }
            // 否则，截取前10个Food对象
            List<Food> topTenFoods = allFoods.subList(0, 10);
            // 创建一个新的列表，先添加查询结果，再用默认列表填充不足的部分
            List<Food> finalList = new ArrayList<>(10);
            finalList.addAll(resultList);
            for (Food food : topTenFoods) {
                // 检查默认列表中的Food是否已经存在于结果列表中
                if (!finalList.contains(food)) {
                    finalList.add(food);
                }
                // 当列表数量达到10个时停止添加
                if (finalList.size() == 10) {
                    break;
                }
            }
            return finalList;
        }

        return resultList;
    }
}
