package com.example.minipropertymanagement.dto.response;


import com.example.minipropertymanagement.enums.OfferStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfferResponse {


    private long id;

    private BigDecimal offerPrice;

    private OfferStatus offerStatus;

    private UserResponse customer;

    private PropertySmallResponse property;
    private boolean acceptedByOwner;
    private boolean acceptedByCustomer;

}
