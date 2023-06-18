package com.example.minipropertymanagement.controller;


import com.example.minipropertymanagement.dto.request.PostPropertyRequest;
import com.example.minipropertymanagement.dto.response.PostPropertyResponse;
import com.example.minipropertymanagement.service.PropertyService;
import com.example.minipropertymanagement.util.S3Util;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {

private final PropertyService propertyService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PostPropertyResponse postProperty(@RequestBody @Valid PostPropertyRequest postPropertyRequest) throws IOException {

     return propertyService.postProperty(postPropertyRequest);
     }





}
