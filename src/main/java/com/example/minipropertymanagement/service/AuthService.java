package com.example.minipropertymanagement.service;


import com.example.minipropertymanagement.dto.request.CreateUserRequest;
import com.example.minipropertymanagement.dto.request.LoginRequest;
import com.example.minipropertymanagement.dto.response.LoginResponse;

public interface AuthService {

LoginResponse login(LoginRequest loginRequest) throws Exception;

     LoginResponse register(CreateUserRequest createUserRequest);
}
