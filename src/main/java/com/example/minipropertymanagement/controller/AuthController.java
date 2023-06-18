package com.example.minipropertymanagement.controller;


import com.example.minipropertymanagement.dto.request.CreateUserRequest;
import com.example.minipropertymanagement.dto.request.LoginRequest;
import com.example.minipropertymanagement.dto.request.RefreshTokenRequest;
import com.example.minipropertymanagement.dto.response.LoginResponse;
import com.example.minipropertymanagement.dto.response.RefreshTokenResponse;
import com.example.minipropertymanagement.exception.InvalidCredential;
import com.example.minipropertymanagement.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")

    @ResponseStatus(HttpStatus.ACCEPTED)
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse register(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return authService.register(createUserRequest);
    }
    @PostMapping("/refreshToken")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RefreshTokenResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws InvalidCredential {
        return authService.refreshToken(refreshTokenRequest);
    }

}
