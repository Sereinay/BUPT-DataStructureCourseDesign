package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.Place;
import com.example.mapper.PlaceMapper;
import com.example.service.PlaceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceServiceImpl extends ServiceImpl<PlaceMapper, Place> implements PlaceService {

    @Override
    public List<Place> findPlaceByName(String name) {
        QueryWrapper<Place> queryWrapper = new QueryWrapper<>();
        if (name == null || name.isEmpty()) {
            // 如果输入为空或null，则按照rating降序排列，并限制结果为10条
            queryWrapper.orderByDesc("rating").last("LIMIT 10");
        } else {
            // 如果输入不为空，则进行模糊匹配，并按照rating降序排列，限制结果为10条
            queryWrapper.like("placeName", name).orderByDesc("rating").last("LIMIT 10");
        }
        List<Place> resultList = list(queryWrapper);

        // 如果结果数量不足10个，则需要用默认列表填充
        if (resultList.size() < 10) {
            // 获取所有Place对象，并按rating降序排列
            List<Place> allPlaces = list(new QueryWrapper<Place>().orderByDesc("rating"));
            // 如果所有Place对象的数量不足10个，则直接返回所有Place对象
            if (allPlaces.size() <= 10) {
                return allPlaces;
            }
            // 否则，截取前10个Place对象
            List<Place> topTenPlaces = allPlaces.subList(0, 10);
            // 创建一个新的列表，先添加查询结果，再用默认列表填充不足的部分
            List<Place> finalList = new ArrayList<>(10);
            finalList.addAll(resultList);
            for (Place place : topTenPlaces) {
                // 检查默认列表中的Place是否已经存在于结果列表中
                if (!finalList.contains(place)) {
                    finalList.add(place);
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

    @Override
    public List<Place> findPlaceByInformation(String info) {
        QueryWrapper<Place> queryWrapper = new QueryWrapper<>();
        if (info == null || info.isEmpty()) {
            // 如果输入为空或null，则按照rating降序排列，并限制结果为10条
            queryWrapper.orderByDesc("rating").last("LIMIT 10");
        } else {
            // 如果输入不为空，则进行模糊匹配，并按照rating降序排列，限制结果为10条
            queryWrapper.like("information", info).orderByDesc("rating").last("LIMIT 10");
        }
        List<Place> resultList = list(queryWrapper);

        // 如果结果数量不足10个，则需要用默认列表填充
        if (resultList.size() < 10) {
            // 获取所有Place对象，并按rating降序排列
            List<Place> allPlaces = list(new QueryWrapper<Place>().orderByDesc("rating"));
            // 如果所有Place对象的数量不足10个，则直接返回所有Place对象
            if (allPlaces.size() <= 10) {
                return allPlaces;
            }
            // 否则，截取前10个Place对象
            List<Place> topTenPlaces = allPlaces.subList(0, 10);
            // 创建一个新的列表，先添加查询结果，再用默认列表填充不足的部分
            List<Place> finalList = new ArrayList<>(10);
            finalList.addAll(resultList);
            for (Place place : topTenPlaces) {
                // 检查默认列表中的Place是否已经存在于结果列表中
                if (!finalList.contains(place)) {
                    finalList.add(place);
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
