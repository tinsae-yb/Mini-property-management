package com.example.minipropertymanagement.service.impl;

import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.dto.response.UserPaginatedResponse;
import com.example.minipropertymanagement.enums.AccountStatus;
import com.example.minipropertymanagement.enums.Role;
import com.example.minipropertymanagement.filter.ModelMappingUtil;
import com.example.minipropertymanagement.repo.UserCriteriaRepository;
import com.example.minipropertymanagement.repo.UserRepository;
import com.example.minipropertymanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final UserCriteriaRepository userCriteriaRepository;


    private final ModelMappingUtil modelMappingUtil;
    @Override
    public UserPaginatedResponse getUsers(Pageable pageable, AccountStatus status, Role role) {

        Page<User> users = userCriteriaRepository.findAllByRoleAccountStatusPageable(pageable, status, role);
//        if (status == null) users = userRepository.findAllByRole(Role.OWNER, pageable);
//        if (status != null) users = userRepository.findAllByRoleAndAccountStatus(Role.OWNER, status, pageable);
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(users.getSize(), users.getNumber()+1, users.getTotalElements(), users.getTotalPages());
//
//

return modelMappingUtil.convertToUserPaginatedResponse(pageMetadata, users.getContent());

    }
}
