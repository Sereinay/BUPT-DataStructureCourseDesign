package com.example.controller;

import com.example.entity.RestBean;
import com.example.service.AccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    @Resource
    AccountService service;
    @GetMapping("/ask-code")
    public RestBean<Void> askVerifyCode(@RequestParam String email,
                                        @RequestParam String type,
                                        HttpServletRequest request){
        String message = service.registerEmailVerifyCode(type,email,request.getRemoteAddr());
        return message==null? RestBean.success():RestBean.failure(400,message);
    }
}
