package com.example.minipropertymanagement.controller;


import com.example.minipropertymanagement.dto.request.CreateUserRequest;
import com.example.minipropertymanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor

public class UserController {


    private final UserService registerService;


    @PostMapping
    public void register(@Valid @RequestBody CreateUserRequest createUserRequest) {
        registerService.register(createUserRequest);
    }
}
