package com.example.minipropertymanagement.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class LoginRequest {


    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;


}
