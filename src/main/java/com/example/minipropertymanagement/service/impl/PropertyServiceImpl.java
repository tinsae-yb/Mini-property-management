package com.example.minipropertymanagement.service.impl;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.minipropertymanagement.domain.Offer;
import com.example.minipropertymanagement.domain.Property;
import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.domain.enums.OfferStatus;
import com.example.minipropertymanagement.domain.enums.Role;
import com.example.minipropertymanagement.dto.request.CreateOfferRequest;
import com.example.minipropertymanagement.dto.request.PostPropertyRequest;
import com.example.minipropertymanagement.dto.response.OfferResponse;
import com.example.minipropertymanagement.dto.response.OffersResponse;
import com.example.minipropertymanagement.dto.response.PostPropertyResponse;
import com.example.minipropertymanagement.dto.response.PropertiesPaginatedResponse;
import com.example.minipropertymanagement.filter.ModelMappingUtil;
import com.example.minipropertymanagement.repo.OfferRepository;
import com.example.minipropertymanagement.repo.PropertyCriteriaRepository;
import com.example.minipropertymanagement.repo.PropertyRepository;
import com.example.minipropertymanagement.repo.UserRepository;
import com.example.minipropertymanagement.service.PropertyService;
import com.example.minipropertymanagement.util.AuthUtil;
import com.example.minipropertymanagement.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    private final UserRepository userRepository;

    private final S3Util s3Util;
    private final ModelMapper modelMapper;

    private final AuthUtil authUtil;

    private final PropertyCriteriaRepository propertyCriteriaRepository;
    private final ModelMappingUtil modelMappingUtil;

    private final OfferRepository offerRepository;

    @Override
    public PostPropertyResponse postProperty(PostPropertyRequest postPropertyRequest) throws IOException {

        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User not found"));
        Property property = modelMapper.map(postPropertyRequest, Property.class);
        property.setOwner(user);
        String[] parts = postPropertyRequest.getImage().split(",");
        String contentType = parts[0].split(":")[1].split(";")[0];
        String base64Data = parts[1];
        byte[] imageBytes = Base64.getDecoder().decode(base64Data);
        String image = s3Util.uploadFile(imageBytes, contentType);
        property.setImage(image);
        propertyRepository.save(property);

        PostPropertyResponse postPropertyResponse = modelMapper.map(property, PostPropertyResponse.class);
        return postPropertyResponse;

    }

    @Override
    public PropertiesPaginatedResponse getProperties(BigDecimal minPrice, BigDecimal maxPrice, Integer bedRooms, Integer bathRooms, String zipCode, String city, String state, Pageable pageable) {
//
        Page<Property> properties = propertyCriteriaRepository.searchProperties(minPrice, maxPrice, bedRooms, bathRooms, zipCode, city, state, pageable);
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(properties.getSize(), properties.getNumber() + 1, properties.getTotalElements(), properties.getTotalPages());

        return modelMappingUtil.convertToPropertiesPaginatedResponse(pageMetadata, properties.getContent());

    }

    @Override
    public OfferResponse postOffer(Long propertyId, CreateOfferRequest createOfferRequest) {
        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User not found"));
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("Property not found"));
        Offer offer = new Offer();
        offer.setOfferPrice(createOfferRequest.getOfferPrice());
        offer.setOfferStatus(OfferStatus.PENDING);
        offer.setProperty(property);
        offer.setCustomer(user);
        offerRepository.save(offer);
        OfferResponse offerResponse = modelMapper.map(offer, OfferResponse.class);
        return offerResponse;

    }

    @Override
    public OffersResponse getPropertyOffers(Long propertyId) {
        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User not found"));


        List<Offer> offers = null;
        if (user.getRole().equals(Role.USER)) {
            offers = offerRepository.findByPropertyIdAndCustomerId(propertyId, user.getId());
        }
        if (user.getRole().equals(Role.OWNER)) {
            offers = offerRepository.findByPropertyId(propertyId);
        }

        OffersResponse offersResponse = new OffersResponse();
        offersResponse.setOffers(offers.stream().map(offer -> modelMapper.map(offer, OfferResponse.class)).toList());


        return offersResponse;

    }
}
