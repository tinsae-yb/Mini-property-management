package com.example.minipropertymanagement.controller;


import com.amazonaws.services.kms.model.NotFoundException;
import com.example.minipropertymanagement.dto.request.CreateOfferRequest;
import com.example.minipropertymanagement.dto.request.PostPropertyRequest;
import com.example.minipropertymanagement.dto.response.OfferResponse;
import com.example.minipropertymanagement.dto.response.OffersResponse;
import com.example.minipropertymanagement.dto.response.PropertyResponse;
import com.example.minipropertymanagement.dto.response.PropertiesPaginatedResponse;
import com.example.minipropertymanagement.enums.PropertyType;
import com.example.minipropertymanagement.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PropertyResponse postProperty(@RequestBody @Valid PostPropertyRequest postPropertyRequest) throws IOException, com.example.minipropertymanagement.exception.NotFoundException {
        return propertyService.postProperty(postPropertyRequest);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public PropertiesPaginatedResponse getProperties(@RequestParam(required = false) BigDecimal minPrice, @RequestParam(required = false) BigDecimal maxPrice, @RequestParam(required = false) Integer bedRooms, @RequestParam(required = false) Integer bathRooms, @RequestParam(required = false) String zipCode, @RequestParam(required = false) String city, @RequestParam(required = false) String state, @RequestParam(required = false) PropertyType propertyType, Pageable pageable) {
        return propertyService.getProperties(minPrice, maxPrice, bedRooms, bathRooms, zipCode, city, state, propertyType, pageable);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{propertyId}")
    public PropertyResponse getProperty(@PathVariable Long propertyId) throws com.example.minipropertymanagement.exception.NotFoundException {
        return propertyService.getProperty(propertyId);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{propertyId}/offers")
    public OfferResponse postOffer(@PathVariable Long propertyId, @RequestBody CreateOfferRequest createOfferRequest) throws com.example.minipropertymanagement.exception.NotFoundException {
        return propertyService.postOffer(propertyId, createOfferRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{propertyId}/offers")
    public OffersResponse getPropertyOffers(@PathVariable Long propertyId) throws com.example.minipropertymanagement.exception.NotFoundException {
        return propertyService.getPropertyOffers(propertyId);
    }


    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{propertyId}/favorites")
    public void addFavorite(@PathVariable Long propertyId) throws com.example.minipropertymanagement.exception.NotFoundException {
        propertyService.addFavorite(propertyId);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{propertyId}/favorites")
    public void removeFavorite(@PathVariable Long propertyId) throws com.example.minipropertymanagement.exception.NotFoundException {
        propertyService.removeFavorite(propertyId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{propertyId}/favorites")
    public void isFavorite(@PathVariable Long propertyId) throws NotFoundException, com.example.minipropertymanagement.exception.NotFoundException {
        propertyService.isFavorite(propertyId);
    }


}
