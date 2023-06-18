package com.example.minipropertymanagement.service;

import com.example.minipropertymanagement.exception.NotFoundException;

public interface AdminService {

    void approveOwner(Long id) throws NotFoundException;
    void blockOwner(Long id) throws NotFoundException;
}
