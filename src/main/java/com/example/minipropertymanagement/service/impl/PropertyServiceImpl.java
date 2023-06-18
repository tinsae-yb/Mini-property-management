package com.example.minipropertymanagement.service.impl;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.minipropertymanagement.domain.Property;
import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.dto.request.PostPropertyRequest;
import com.example.minipropertymanagement.dto.response.PostPropertyResponse;
import com.example.minipropertymanagement.repo.PropertyRepository;
import com.example.minipropertymanagement.repo.UserRepository;
import com.example.minipropertymanagement.service.PropertyService;
import com.example.minipropertymanagement.util.AuthUtil;
import com.example.minipropertymanagement.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Base64;

@Service
@Transactional
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    private final UserRepository userRepository;

    private final S3Util s3Util;
    private final ModelMapper modelMapper;

    private final AuthUtil authUtil;


    @Override
    public PostPropertyResponse postProperty(PostPropertyRequest postPropertyRequest) throws IOException {

        String username = authUtil.getUsername();

        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User not found"));
        Property property = modelMapper.map(postPropertyRequest, Property.class);
        property.setUser(user);


        String[] parts = postPropertyRequest.getImage().split(",");
        String contentType = parts[0].split(":")[1].split(";")[0];
        String base64Data = parts[1];

        byte[] imageBytes = Base64.getDecoder().decode(base64Data);


        System.out.println("contentType: " + contentType);
        System.out.println("imageBytes: " + imageBytes);
        String image = s3Util.uploadFile(imageBytes, contentType);

        property.setImage(image);

        propertyRepository.save(property);

        PostPropertyResponse postPropertyResponse = modelMapper.map(property, PostPropertyResponse.class);
        return postPropertyResponse;

    }
}
