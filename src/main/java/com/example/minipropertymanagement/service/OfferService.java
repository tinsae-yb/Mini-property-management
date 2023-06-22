package com.example.minipropertymanagement.service;

import com.example.minipropertymanagement.dto.response.OfferResponse;
import com.example.minipropertymanagement.dto.response.OffersResponse;
import com.example.minipropertymanagement.enums.OfferActions;
import com.example.minipropertymanagement.exception.ForbiddenAccess;
import com.example.minipropertymanagement.exception.NotFoundException;

public interface OfferService {

    OffersResponse getOffers() throws ForbiddenAccess;

    OfferResponse acceptOffer(Long offerId) throws ForbiddenAccess, NotFoundException;

    OfferResponse rejectOffer(Long offerId) throws ForbiddenAccess, NotFoundException;

    OfferResponse cancelOffer(Long offerId) throws ForbiddenAccess, NotFoundException;

    OfferResponse acceptContingent(Long offerId) throws ForbiddenAccess, NotFoundException;
}
