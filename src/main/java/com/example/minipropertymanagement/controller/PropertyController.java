package com.example.minipropertymanagement.controller;


import com.example.minipropertymanagement.domain.enums.PropertyType;
import com.example.minipropertymanagement.dto.request.CreateOfferRequest;
import com.example.minipropertymanagement.dto.request.PostPropertyRequest;
import com.example.minipropertymanagement.dto.response.OfferResponse;
import com.example.minipropertymanagement.dto.response.PostPropertyResponse;
import com.example.minipropertymanagement.dto.response.PropertiesPaginatedResponse;
import com.example.minipropertymanagement.service.PropertyService;
import com.example.minipropertymanagement.util.S3Util;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
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


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public PropertiesPaginatedResponse getProperties(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer bedRooms,
            @RequestParam(required = false) Integer bathRooms,
            @RequestParam(required = false) String zipCode,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            Pageable pageable) {
        return propertyService.getProperties(minPrice, maxPrice, bedRooms, bathRooms, zipCode, city, state, pageable);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{propertyId}/offers")
    public OfferResponse postOffer(@PathVariable Long propertyId, @RequestBody CreateOfferRequest createOfferRequest) {
        return propertyService.postOffer(propertyId, createOfferRequest);
    }

}
