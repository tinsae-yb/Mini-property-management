package com.example.minipropertymanagement.service;

import com.example.minipropertymanagement.dto.request.CreateOfferRequest;
import com.example.minipropertymanagement.dto.request.PostPropertyRequest;
import com.example.minipropertymanagement.dto.response.OfferResponse;
import com.example.minipropertymanagement.dto.response.OffersResponse;
import com.example.minipropertymanagement.dto.response.PropertyResponse;
import com.example.minipropertymanagement.dto.response.PropertiesPaginatedResponse;
import com.example.minipropertymanagement.enums.PropertyType;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.math.BigDecimal;

public interface PropertyService {

    PropertyResponse postProperty(PostPropertyRequest postPropertyRequest) throws IOException;

    PropertiesPaginatedResponse getProperties(BigDecimal minPrice, BigDecimal maxPrice, Integer bedRooms, Integer bathRooms, String zipCode, String city, String state, PropertyType propertyType, Pageable pageable) ;

    OfferResponse postOffer(Long propertyId, CreateOfferRequest createOfferRequest);

    OffersResponse getPropertyOffers(Long propertyId);

    PropertyResponse getProperty(Long propertyId);
}
