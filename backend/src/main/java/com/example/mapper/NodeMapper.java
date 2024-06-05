package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.Node;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NodeMapper extends BaseMapper<Node> {
}
