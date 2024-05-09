package com.example.service;

import com.example.entity.dto.Place;

import java.util.List;

public interface PlaceService {
    List<Place> findPlaceByName(String name);
    List<Place> findPlaceByInformation(String info);
}
