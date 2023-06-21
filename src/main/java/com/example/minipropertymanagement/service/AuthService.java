package com.example.minipropertymanagement.service;


import com.example.minipropertymanagement.dto.request.CreateUserRequest;
import com.example.minipropertymanagement.dto.request.LoginRequest;
import com.example.minipropertymanagement.dto.request.RefreshTokenRequest;
import com.example.minipropertymanagement.dto.response.LoginResponse;
import com.example.minipropertymanagement.dto.response.RefreshTokenResponse;
import com.example.minipropertymanagement.exception.InvalidCredential;

public interface AuthService {

LoginResponse login(LoginRequest loginRequest) throws InvalidCredential;

     LoginResponse register(CreateUserRequest createUserRequest);

     RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws InvalidCredential;
}
