package com.example.minipropertymanagement.service.impl;

import com.example.minipropertymanagement.domain.Offer;
import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.domain.enums.OfferStatus;
import com.example.minipropertymanagement.domain.enums.PropertyStatus;
import com.example.minipropertymanagement.dto.response.OfferResponse;
import com.example.minipropertymanagement.dto.response.OffersResponse;
import com.example.minipropertymanagement.enums.OfferActions;
import com.example.minipropertymanagement.exception.ForbiddenAccess;
import com.example.minipropertymanagement.exception.InvalidCredential;
import com.example.minipropertymanagement.exception.NotFoundException;
import com.example.minipropertymanagement.repo.OfferRepository;
import com.example.minipropertymanagement.repo.UserRepository;
import com.example.minipropertymanagement.service.OfferService;
import com.example.minipropertymanagement.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {


    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;


    private final AuthUtil authUtil;

    @Override
    public OfferResponse updateOffer(Long offerId, OfferActions action) throws NotFoundException, InvalidCredential {

        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new NotFoundException("Offer not found"));
        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new InvalidCredential("User not found"));

        if (action.equals(OfferActions.CANCEL)) {
            if (offer.getCustomer().getId() == user.getId()) {
                offer.setOfferStatus(OfferStatus.CANCELLED);
                offerRepository.save(offer);
                return modelMapper.map(offer, OfferResponse.class);
            }
        }

        if (offer.getProperty().getOwner().getId() == user.getId()) {
            if (action.equals(OfferActions.ACCEPT)) {
                offer.getProperty().setPropertyStatus(PropertyStatus.PENDING);
                offer.setOfferStatus(OfferStatus.ACCEPTED);
                offerRepository.save(offer);
                return modelMapper.map(offer, OfferResponse.class);
            }
            if (action.equals(OfferActions.REJECT)) {
                offer.setOfferStatus(OfferStatus.REJECTED);
                offerRepository.save(offer);
                return modelMapper.map(offer, OfferResponse.class);
            }
        }
        throw new ForbiddenAccess("You are not allowed to modify this offer");


    }

    @Override
    public OffersResponse getOffers() throws InvalidCredential {
        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new InvalidCredential("User not found"));
        List<OfferResponse> offers = offerRepository.findAllByCustomerId(user.getId()).stream().map(offer -> modelMapper.map(offer, OfferResponse.class)).toList();
        OffersResponse offersResponse = new OffersResponse();
        offersResponse.setOffers(offers);
        return offersResponse;
    }
}
