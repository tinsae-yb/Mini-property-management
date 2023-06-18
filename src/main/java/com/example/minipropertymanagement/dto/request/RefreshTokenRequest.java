package com.example.minipropertymanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class RefreshTokenRequest {

    @NotNull
    private String refreshToken;
}
