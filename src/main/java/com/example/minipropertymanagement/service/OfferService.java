package com.example.minipropertymanagement.service;

import com.example.minipropertymanagement.dto.response.OfferResponse;
import com.example.minipropertymanagement.dto.response.OffersResponse;
import com.example.minipropertymanagement.enums.OfferActions;
import com.example.minipropertymanagement.exception.InvalidCredential;
import com.example.minipropertymanagement.exception.NotFoundException;

public interface OfferService {

    OffersResponse getOffers() throws InvalidCredential;

    OfferResponse acceptOffer(Long offerId) throws InvalidCredential, NotFoundException;

    OfferResponse rejectOffer(Long offerId) throws InvalidCredential, NotFoundException;

    OfferResponse cancelOffer(Long offerId) throws InvalidCredential, NotFoundException;

    OfferResponse acceptContingent(Long offerId) throws InvalidCredential, NotFoundException;
}
