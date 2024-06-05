package com.example.service;

import com.example.entity.dto.Node;

import java.util.List;

public interface NodeService {
    List<Node> findNodeById(List<Integer> list);

    List<Node> findNodeByPlaceType(String placeType);
}
