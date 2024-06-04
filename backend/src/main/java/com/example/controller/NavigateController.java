package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.dto.Node;
import com.example.service.NodeNavigateService;
import com.example.service.NodeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/navigate")
public class NavigateController {
    @Resource
    private NodeNavigateService nodeNavigateService;

    @Resource
    private NodeService nodeService;

    @GetMapping("/shortest-path-with-two")
    public RestBean<List<Node>> getShortestPathWithTwo(@RequestParam String from,
                                                       @RequestParam String to,
                                                       @RequestParam String type) {
        List<Integer> nodeIds = nodeNavigateService.findShortestPathWithTwo(from, to, type);
        if (nodeIds.isEmpty()) {
            return RestBean.failure(404, "路径未找到！");
        }
        List<Node> nodes = nodeService.findNodeById(nodeIds);
        return RestBean.success(nodes);
    }

    @GetMapping("/shortest-path-with-some")
    public RestBean<List<Node>> getShortestPathWithSome(@RequestParam String placeList,
                                                        @RequestParam String type) {
        List<Integer> nodeIds = nodeNavigateService.findShortestPathWithSome(placeList, type);
        if (nodeIds.isEmpty()) {
            return RestBean.failure(404, "路径未找到！");
        }
        List<Node> nodes = nodeService.findNodeById(nodeIds);
        return RestBean.success(nodes);
    }
}
