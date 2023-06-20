package com.example.minipropertymanagement.service;

import com.example.minipropertymanagement.dto.request.CreateOfferRequest;
import com.example.minipropertymanagement.dto.request.PostPropertyRequest;
import com.example.minipropertymanagement.dto.response.OfferResponse;
import com.example.minipropertymanagement.dto.response.OffersResponse;
import com.example.minipropertymanagement.dto.response.PostPropertyResponse;
import com.example.minipropertymanagement.dto.response.PropertiesPaginatedResponse;
import com.example.minipropertymanagement.enums.PropertyType;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.math.BigDecimal;

public interface PropertyService {

    PostPropertyResponse postProperty(PostPropertyRequest postPropertyRequest) throws IOException;

    PropertiesPaginatedResponse getProperties(BigDecimal minPrice, BigDecimal maxPrice, Integer bedRooms, Integer bathRooms, String zipCode, String city, String state, PropertyType propertyType, Pageable pageable) ;

    OfferResponse postOffer(Long propertyId, CreateOfferRequest createOfferRequest);

    OffersResponse getPropertyOffers(Long propertyId);
}
