package com.example.minipropertymanagement.service.impl;

import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.domain.enums.AccountStatus;
import com.example.minipropertymanagement.domain.enums.Role;
import com.example.minipropertymanagement.dto.request.CreateUserRequest;
import com.example.minipropertymanagement.dto.request.LoginRequest;
import com.example.minipropertymanagement.dto.request.RefreshTokenRequest;
import com.example.minipropertymanagement.dto.response.LoginResponse;
import com.example.minipropertymanagement.dto.response.RefreshTokenResponse;
import com.example.minipropertymanagement.exception.InvalidCredential;
import com.example.minipropertymanagement.repo.UserRepository;
import com.example.minipropertymanagement.service.AuthService;
import com.example.minipropertymanagement.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final DaoAuthenticationProvider authenticationProvider;

    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        Authentication auth = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        User user = userRepository.findByEmail(auth.getName()).orElseThrow(() -> new InvalidCredential("User not found with this email"));


        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(jwtUtil.generateToken(user, false));
        loginResponse.setRefreshToken(jwtUtil.generateToken(user, true));
        return loginResponse;
    }

    @Override
    public LoginResponse register(CreateUserRequest createUserRequest) {
        User user = modelMapper.map(createUserRequest, User.class);
        if (createUserRequest.getRole().equals(Role.OWNER)) {
            user.setAccountStatus(AccountStatus.PENDING);
        } else {
            user.setAccountStatus(AccountStatus.ACTIVE);
        }
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        userRepository.save(user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(jwtUtil.generateToken(user, false));
        loginResponse.setRefreshToken(jwtUtil.generateToken(user, true));
        return loginResponse;

    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws InvalidCredential {
        boolean valid = jwtUtil.validateToken(refreshTokenRequest.getRefreshToken());
        if (!valid) {
            throw new InvalidCredential("Invalid refresh token");
        }
        Claims claims = jwtUtil.getClaims(refreshTokenRequest.getRefreshToken());
        String email = claims.getSubject();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new InvalidCredential("User not found with this email"));
        RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
        refreshTokenResponse.setAccessToken(jwtUtil.generateToken(user, false));
        refreshTokenResponse.setRefreshToken(refreshTokenRequest.getRefreshToken());

        return refreshTokenResponse;
    }
}
