package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.Place;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlaceMapper extends BaseMapper<Place> {
}
