package com.example.minipropertymanagement.controller;


import com.example.minipropertymanagement.domain.enums.AccountStatus;
import com.example.minipropertymanagement.domain.enums.Role;
import com.example.minipropertymanagement.dto.response.UserPaginatedResponse;
import com.example.minipropertymanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("")
    public UserPaginatedResponse getOwners(Pageable pageable, @RequestParam(required = false) AccountStatus status, @RequestParam(required = false) Role role) {
        return userService.getUsers(pageable, status, role);
    }
}
