package com.example.minipropertymanagement.service;

import com.example.minipropertymanagement.dto.response.UserPaginatedResponse;
import com.example.minipropertymanagement.enums.AccountStatus;
import com.example.minipropertymanagement.enums.Role;
import org.springframework.data.domain.Pageable;

public interface UserService {



    UserPaginatedResponse getUsers(Pageable pageable , AccountStatus status, Role role);
}
