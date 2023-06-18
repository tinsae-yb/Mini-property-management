package com.example.minipropertymanagement.service;

import com.example.minipropertymanagement.dto.request.PostPropertyRequest;
import com.example.minipropertymanagement.dto.response.PostPropertyResponse;

import java.io.IOException;

public interface PropertyService {

    PostPropertyResponse postProperty(PostPropertyRequest postPropertyRequest) throws IOException;
}
