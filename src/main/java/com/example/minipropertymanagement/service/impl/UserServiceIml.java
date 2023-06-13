package com.example.minipropertymanagement.service.impl;

import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.dto.request.CreateUserRequest;
import com.example.minipropertymanagement.repo.UserRepository;
import com.example.minipropertymanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceIml implements UserService {

    private final UserRepository personRepository;
    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void register(CreateUserRequest createUserRequest) {

        User person = modelMapper.map(createUserRequest, User.class);
        person.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        personRepository.save(person);

    }
}
