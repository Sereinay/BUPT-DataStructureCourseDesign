package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.Node;
import com.example.entity.dto.NodeInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface NodeNavigateService extends IService<NodeInfo>{
    List<Integer> findShortestPathWithTwo(String from, String to,String type);

    List<Integer> findShortestPathWithSome(String placeList,String type);
}
