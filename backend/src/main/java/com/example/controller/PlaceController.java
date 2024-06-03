package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.dto.Place;
import com.example.service.PlaceService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlaceController {

    @Resource
    private PlaceService placeService;

    @GetMapping("/api/place/search")
    public RestBean<List<Place>> searchPlaces(
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "query", required = false) String query) {
        List<Place> places;
        if ("name".equalsIgnoreCase(type)) {
            places = placeService.findPlaceByName(query);
        } else if ("info".equalsIgnoreCase(type)) {
            places = placeService.findPlaceByInformation(query);
        } else {
            return RestBean.failure(400, "Invalid search type");
        }
        return RestBean.success(places);
    }
}
