package com.example.minipropertymanagement.service.impl;


import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.domain.enums.AccountStatus;
import com.example.minipropertymanagement.exception.NotFoundException;
import com.example.minipropertymanagement.repo.UserRepository;
import com.example.minipropertymanagement.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;


    @Override
    public void approveOwner(Long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        user.setAccountStatus(AccountStatus.ACTIVE);
    }

    @Override
    public void blockOwner(Long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        user.setAccountStatus(AccountStatus.BLOCKED);
    }
}
