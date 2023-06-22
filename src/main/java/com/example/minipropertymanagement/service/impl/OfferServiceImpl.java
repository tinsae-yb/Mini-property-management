package com.example.minipropertymanagement.service.impl;

import com.example.minipropertymanagement.domain.Offer;
import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.dto.response.OfferResponse;
import com.example.minipropertymanagement.dto.response.OffersResponse;
import com.example.minipropertymanagement.enums.OfferActions;
import com.example.minipropertymanagement.enums.OfferStatus;
import com.example.minipropertymanagement.enums.PropertyStatus;
import com.example.minipropertymanagement.enums.Role;
import com.example.minipropertymanagement.exception.ForbiddenAccess;
import com.example.minipropertymanagement.exception.ForbiddenAccess;
import com.example.minipropertymanagement.exception.NotFoundException;
import com.example.minipropertymanagement.repo.OfferCustomerRepository;
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
    private final OfferCustomerRepository offerCustomerRepository;

    private final ModelMapper modelMapper;


    private final AuthUtil authUtil;


    @Override
    public OffersResponse getOffers() throws ForbiddenAccess {
        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new ForbiddenAccess("User not found"));
        List<OfferResponse> offers;
        if (user.getRole().equals(Role.USER)) {

            offers = offerRepository.findAllByCustomerIdOrderByCreatedDateDesc(user.getId()).stream().map(offer -> modelMapper.map(offer, OfferResponse.class)).toList();
        } else {
            offers = offerRepository.findAllByPropertyOwnerId(user.getId()).stream().map(offer -> modelMapper.map(offer, OfferResponse.class)).toList();
        }


        OffersResponse offersResponse = new OffersResponse();
        offersResponse.setOffers(offers);
        return offersResponse;
    }

    @Override
    public OfferResponse acceptOffer(Long offerId) throws ForbiddenAccess, NotFoundException {

        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new ForbiddenAccess("User not found"));

        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new NotFoundException("Offer not found"));
        OfferStatus offerStatus = offer.getOfferStatus();
        PropertyStatus propertyStatus = offer.getProperty().getPropertyStatus();

        boolean isOwner = offer.getProperty().getOwner().getId() == user.getId();
        if (!isOwner) {
            throw new ForbiddenAccess("Only owner can accept offer");
        }
        if (propertyStatus.equals(PropertyStatus.SOLD)|| propertyStatus.equals(PropertyStatus.CONTINGENT)) {
            throw new ForbiddenAccess("You can't accept offer at this stage");
        }
        if (offerStatus.equals(OfferStatus.CANCELLED)) {
            throw new ForbiddenAccess("Offer is already cancelled");
        }
        offer.setOfferStatus(OfferStatus.ACCEPTED);
        offer.getProperty().setPropertyStatus(PropertyStatus.CONTINGENT);

        offerCustomerRepository.updateOffersForAPropertyOtherThanGivenOffer(offerId, offer.getProperty().getId(), OfferStatus.REJECTED);

        OfferResponse offerResponse = modelMapper.map(offer, OfferResponse.class);
        return offerResponse;
    }

    @Override
    public OfferResponse rejectOffer(Long offerId) throws ForbiddenAccess, NotFoundException {

        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new ForbiddenAccess("User not found"));
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new NotFoundException("Offer not found"));
        OfferStatus offerStatus = offer.getOfferStatus();
        PropertyStatus propertyStatus = offer.getProperty().getPropertyStatus();
        boolean isOwner = offer.getProperty().getOwner().getId() == user.getId();

        if (!isOwner) {
            throw new ForbiddenAccess("Only owner can reject offer");
        }

        if (propertyStatus.equals(PropertyStatus.SOLD)) {
            throw new ForbiddenAccess("Property is already sold");
        }

        if (propertyStatus.equals(PropertyStatus.CONTINGENT) || propertyStatus.equals(PropertyStatus.PENDING)) {
            offer.setOfferStatus(OfferStatus.REJECTED);
            offer.getProperty().setPropertyStatus(PropertyStatus.AVAILABLE);
        }
        if (offerStatus.equals(OfferStatus.CANCELLED)) {
            throw new ForbiddenAccess("Offer is already cancelled");
        }

        OfferResponse offerResponse = modelMapper.map(offer, OfferResponse.class);
        return offerResponse;


    }

    @Override
    public OfferResponse cancelOffer(Long offerId) throws ForbiddenAccess, NotFoundException {
        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new ForbiddenAccess("User not found"));
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new NotFoundException("Offer not found"));
        OfferStatus offerStatus = offer.getOfferStatus();
        PropertyStatus propertyStatus = offer.getProperty().getPropertyStatus();
        boolean isOwner = offer.getProperty().getOwner().getId() == user.getId();
        if (isOwner) {
            throw new ForbiddenAccess("Only customer can cancel offer");
        }
        if (!offerStatus.equals(OfferStatus.PENDING)) {
            throw new ForbiddenAccess("You cannot modify this offer");
        }
        offer.setOfferStatus(OfferStatus.CANCELLED);
        OfferResponse offerResponse = modelMapper.map(offer, OfferResponse.class);
        if (propertyStatus.equals(PropertyStatus.PENDING)  ) {
            List<Offer> offers = offerRepository.findAllByPropertyIdAndOfferStatus(offer.getProperty().getId(), OfferStatus.PENDING);
            if (offers.isEmpty()) {
                offer.getProperty().setPropertyStatus(PropertyStatus.AVAILABLE);
            }
        }
        return offerResponse;
    }


    @Override
    public OfferResponse acceptContingent(Long offerId) throws ForbiddenAccess, NotFoundException {

        String username = authUtil.getUsername();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new ForbiddenAccess("User not found"));
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new NotFoundException("Offer not found"));
        OfferStatus offerStatus = offer.getOfferStatus();
        PropertyStatus propertyStatus = offer.getProperty().getPropertyStatus();
        boolean isOwner = offer.getProperty().getOwner().getId() == user.getId();


        if (!propertyStatus.equals(PropertyStatus.CONTINGENT)) {
            throw new ForbiddenAccess("Property is not contingent");
        }

        if (isOwner) {
            offer.setAcceptedByOwner(true);
        } else {
            offer.setAcceptedByCustomer(true);
        }
        offer.getProperty().setPropertyStatus(PropertyStatus.SOLD);


        OfferResponse offerResponse = modelMapper.map(offer, OfferResponse.class);
        return offerResponse;

    }
}
