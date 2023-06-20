package com.example.minipropertymanagement.controller;


import com.example.minipropertymanagement.dto.response.OfferResponse;
import com.example.minipropertymanagement.dto.response.OffersResponse;
import com.example.minipropertymanagement.enums.OfferActions;
import com.example.minipropertymanagement.exception.InvalidCredential;
import com.example.minipropertymanagement.exception.NotFoundException;
import com.example.minipropertymanagement.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
public class OfferController {


    private final OfferService offerService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/offers")
    public OffersResponse getOffers() throws InvalidCredential {
        return offerService.getOffers();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(value = "/offers/{offerId}", params = {"action"})
    public OfferResponse updateOffer(@PathVariable Long offerId, @RequestParam OfferActions action) throws InvalidCredential, NotFoundException {

        if(action.equals(OfferActions.ACCEPT_CONTINGENT)){
            return offerService.acceptContingent(offerId);
        }
        if(action.equals(OfferActions.CANCEL)){
            return offerService.cancelOffer(offerId);
        }
        if(action.equals(OfferActions.REJECT)){
            return offerService.rejectOffer(offerId);
        }
        if(action.equals(OfferActions.ACCEPT)){
            return offerService.acceptOffer(offerId);
        }
        return null;



    }



}
