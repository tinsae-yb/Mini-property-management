package com.example.minipropertymanagement.service;

import com.example.minipropertymanagement.domain.enums.AccountStatus;
import com.example.minipropertymanagement.domain.enums.Role;
import com.example.minipropertymanagement.dto.response.UserPaginatedResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {



    UserPaginatedResponse getUsers(Pageable pageable , AccountStatus status, Role role);
}
