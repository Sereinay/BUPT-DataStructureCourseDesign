package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.Node;
import com.example.mapper.NodeMapper;
import com.example.service.NodeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node> implements NodeService {

    @Resource
    private NodeMapper nodeMapper;

    @Override
    public List<Node> findNodeById(List<Integer> idList) {
        if (idList.isEmpty()) {
            return new ArrayList<>();
        }

        // 执行查询
        List<Node> nodes = nodeMapper.selectBatchIds(idList);

        // 根据传入的ID列表顺序对查询结果进行排序
        return idList.stream()
                .map(id -> nodes.stream()
                        .filter(node -> node.getNodeId().equals(id))
                        .findFirst()
                        .orElse(null))
                .filter(node -> node != null)
                .collect(Collectors.toList());
    }

    @Override
    public List<Node> findNodeByPlaceType(String placeType) {
        QueryWrapper<Node> queryWrapper = new QueryWrapper<>();
        if (!"All".equalsIgnoreCase(placeType)) {
            queryWrapper.eq("placeType", placeType);
        }
        return this.list(queryWrapper);
    }
}
