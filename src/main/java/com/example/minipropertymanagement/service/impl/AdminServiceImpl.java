package com.example.minipropertymanagement.service.impl;


import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.dto.response.UserResponse;
import com.example.minipropertymanagement.enums.AccountStatus;
import com.example.minipropertymanagement.exception.NotFoundException;
import com.example.minipropertymanagement.repo.UserRepository;
import com.example.minipropertymanagement.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponse approveOwner(Long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        user.setAccountStatus(AccountStatus.ACTIVE);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return userResponse;

    }

    @Override
    public UserResponse blockOwner(Long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        user.setAccountStatus(AccountStatus.BLOCKED);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return userResponse;

    }
}
