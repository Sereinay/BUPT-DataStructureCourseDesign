package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/api/test")
@RestController
public class TestController {

    @GetMapping("/hello")
    public String test(){
        return "Hello!!!!!!!!!!";
    }
}
