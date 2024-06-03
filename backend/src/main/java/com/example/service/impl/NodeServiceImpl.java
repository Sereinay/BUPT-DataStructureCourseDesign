package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.Node;
import com.example.mapper.NodeMapper;
import com.example.service.NodeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node> implements NodeService {

    @Resource
    private NodeMapper nodeMapper;
    @Override
    public List<Node> findNodeById(List<Integer> list) {
        if (list.isEmpty()) return new ArrayList<>();
        return nodeMapper.selectBatchIds(list);
    }

}
