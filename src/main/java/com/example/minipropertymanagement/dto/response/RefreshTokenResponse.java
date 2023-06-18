package com.example.minipropertymanagement.dto.response;

import lombok.Data;

@Data
public class RefreshTokenResponse {
    private String accessToken;
    private String refreshToken;
}
