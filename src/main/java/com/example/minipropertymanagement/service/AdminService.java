package com.example.minipropertymanagement.service;

import com.example.minipropertymanagement.dto.response.UserResponse;
import com.example.minipropertymanagement.exception.NotFoundException;

public interface AdminService {

    UserResponse approveOwner(Long id) throws NotFoundException;
    UserResponse blockOwner(Long id) throws NotFoundException;
}
