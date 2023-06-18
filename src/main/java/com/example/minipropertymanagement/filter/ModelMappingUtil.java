package com.example.minipropertymanagement.filter;


import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.dto.response.UserPaginatedResponse;
import com.example.minipropertymanagement.dto.response.UserSmallResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ModelMappingUtil {
    private final ModelMapper modelMapper;


    public UserPaginatedResponse convertToUserPaginatedResponse(PagedModel.PageMetadata metadata, List<User> users) {
        List<UserSmallResponse> userSmallResponses = users.stream().map(user -> modelMapper.map(user, UserSmallResponse.class)).collect(Collectors.toList());

        PagedModel<UserSmallResponse> model = PagedModel.of(userSmallResponses, metadata);

       UserPaginatedResponse userPaginatedResponse = new UserPaginatedResponse();
         userPaginatedResponse.setPage(model.getMetadata());
            userPaginatedResponse.setUsers(model.getContent().stream().toList());
        return userPaginatedResponse;
    }
}
