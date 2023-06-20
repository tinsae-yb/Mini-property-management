package com.example.minipropertymanagement.dto.response;


import com.example.minipropertymanagement.enums.AccountStatus;
import com.example.minipropertymanagement.enums.Role;
import lombok.Data;

@Data
public class UserSmallResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private AccountStatus accountStatus;
    private Role role;


}
