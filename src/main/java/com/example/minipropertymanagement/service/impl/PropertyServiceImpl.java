package com.example.minipropertymanagement.service.impl;

import com.example.minipropertymanagement.domain.Favorite;
import com.example.minipropertymanagement.domain.Offer;
import com.example.minipropertymanagement.domain.Property;
import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.dto.request.CreateOfferRequest;
import com.example.minipropertymanagement.dto.request.PostPropertyRequest;
import com.example.minipropertymanagement.dto.response.OfferResponse;
import com.example.minipropertymanagement.dto.response.OffersResponse;
import com.example.minipropertymanagement.dto.response.PropertyResponse;
import com.example.minipropertymanagement.dto.response.PropertiesPaginatedResponse;
import com.example.minipropertymanagement.enums.*;
import com.example.minipropertymanagement.exception.ForbiddenAccess;
import com.example.minipropertymanagement.exception.NotFoundException;
import com.example.minipropertymanagement.filter.ModelMappingUtil;
import com.example.minipropertymanagement.repo.*;
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

    private final FavoriteRepository favoriteRepository;

    @Override
    public PropertyResponse postProperty(PostPropertyRequest postPropertyRequest) throws IOException, NotFoundException {

        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User not found"));
        if(user.getAccountStatus().equals(AccountStatus.BLOCKED)){
            throw new ForbiddenAccess("Your account is blocked");
        }
        if(user.getAccountStatus().equals(AccountStatus.PENDING)){
            throw new ForbiddenAccess("Your account is pending");
        }


        Property property = modelMapper.map(postPropertyRequest, Property.class);
        property.setOwner(user);
        property.setPropertyStatus(PropertyStatus.AVAILABLE);
        String[] parts = postPropertyRequest.getImage().split(",");
        String contentType = parts[0].split(":")[1].split(";")[0];
        String base64Data = parts[1];
        byte[] imageBytes = Base64.getDecoder().decode(base64Data);
        String image = s3Util.uploadFile(imageBytes, contentType);
        property.setImage(image);
        propertyRepository.save(property);

        PropertyResponse postPropertyResponse = modelMapper.map(property, PropertyResponse.class);
        return postPropertyResponse;

    }

    @Override
    public PropertiesPaginatedResponse getProperties(BigDecimal minPrice, BigDecimal maxPrice, Integer bedRooms, Integer bathRooms, String zipCode, String city, String state, PropertyType propertyType, Pageable pageable) {
//

        User owner = null;
        try {
            String username = authUtil.getUsername();
            User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User not found"));
            if (user.getRole().equals(Role.OWNER)) {
                owner = user;
            }
        } catch (Exception e) {
        }


        Page<Property> properties = propertyCriteriaRepository.searchProperties(minPrice, maxPrice, bedRooms, bathRooms, zipCode, city, state, propertyType, pageable, owner == null ? null : owner.getId());
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(properties.getSize(), properties.getNumber() + 1, properties.getTotalElements(), properties.getTotalPages());
        return modelMappingUtil.convertToPropertiesPaginatedResponse(pageMetadata, properties.getContent());

    }

    @Override
    public OfferResponse postOffer(Long propertyId, CreateOfferRequest createOfferRequest) throws NotFoundException {
        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User not found"));
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("Property not found"));
        if (property.getPropertyStatus().equals(PropertyStatus.SOLD)) {
            throw new ForbiddenAccess("Property is sold");
        }
        if (property.getPropertyStatus().equals(PropertyStatus.CONTINGENT)) {
            throw new ForbiddenAccess("Property is contingent");
        }

        Offer offer = new Offer();
        offer.setOfferPrice(createOfferRequest.getOfferPrice());
        offer.setOfferStatus(OfferStatus.PENDING);
        offer.setProperty(property);
        offer.setCustomer(user);
        offerRepository.save(offer);
        property.setPropertyStatus(PropertyStatus.PENDING);
        OfferResponse offerResponse = modelMapper.map(offer, OfferResponse.class);


        return offerResponse;

    }

    @Override
    public OffersResponse getPropertyOffers(Long propertyId) throws NotFoundException {
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

    @Override
    public PropertyResponse getProperty(Long propertyId) throws NotFoundException {

        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("Property not found"));

        PropertyResponse propertyResponse = modelMapper.map(property, PropertyResponse.class);

        return propertyResponse;
    }

    @Override
    public void addFavorite(Long propertyId) throws NotFoundException {
        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User not found"));
        Favorite favorite = new Favorite();
        favorite.setProperty(propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("Property not found")));
        favorite.setUser(user);
        favoriteRepository.save(favorite);


    }

    @Override
    public void removeFavorite(Long propertyId) throws NotFoundException {
        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User not found"));
        Favorite favorite = favoriteRepository.findByPropertyIdAndUserId(propertyId, user.getId()).orElseThrow(() -> new NotFoundException("Favorite not found"));
        favoriteRepository.delete(favorite);

    }

    @Override
    public void isFavorite(Long propertyId) throws NotFoundException {

        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException("User not found"));
        favoriteRepository.findByPropertyIdAndUserId(propertyId, user.getId()).orElseThrow(() -> new NotFoundException("Favorite not found"));


    }
}
