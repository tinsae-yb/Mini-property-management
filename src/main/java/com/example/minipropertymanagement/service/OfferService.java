package com.example.minipropertymanagement.service;

import com.example.minipropertymanagement.dto.response.OfferResponse;
import com.example.minipropertymanagement.dto.response.OffersResponse;
import com.example.minipropertymanagement.enums.OfferActions;
import com.example.minipropertymanagement.exception.InvalidCredential;
import com.example.minipropertymanagement.exception.NotFoundException;

public interface OfferService {
    OfferResponse updateOffer(Long offerId, OfferActions action) throws NotFoundException, InvalidCredential;

    OffersResponse getOffers() throws InvalidCredential;
}
