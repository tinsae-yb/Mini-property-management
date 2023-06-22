package com.example.minipropertymanagement.service;

import com.example.minipropertymanagement.dto.request.CreateOfferRequest;
import com.example.minipropertymanagement.dto.request.PostPropertyRequest;
import com.example.minipropertymanagement.dto.response.OfferResponse;
import com.example.minipropertymanagement.dto.response.OffersResponse;
import com.example.minipropertymanagement.dto.response.PropertyResponse;
import com.example.minipropertymanagement.dto.response.PropertiesPaginatedResponse;
import com.example.minipropertymanagement.enums.PropertyType;
import com.example.minipropertymanagement.exception.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.math.BigDecimal;

public interface PropertyService {

    PropertyResponse postProperty(PostPropertyRequest postPropertyRequest) throws IOException, NotFoundException;

    PropertiesPaginatedResponse getProperties(BigDecimal minPrice, BigDecimal maxPrice, Integer bedRooms, Integer bathRooms, String zipCode, String city, String state, PropertyType propertyType, Pageable pageable) ;

    OfferResponse postOffer(Long propertyId, CreateOfferRequest createOfferRequest) throws NotFoundException;

    OffersResponse getPropertyOffers(Long propertyId) throws NotFoundException;

    PropertyResponse getProperty(Long propertyId) throws NotFoundException;

    void addFavorite(Long propertyId) throws NotFoundException;

    void removeFavorite(Long propertyId) throws NotFoundException;

    void isFavorite(Long propertyId) throws NotFoundException;

    void deleteProperty(Long propertyId) throws NotFoundException;
}
