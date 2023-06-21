package com.example.minipropertymanagement.dto.response;


import com.example.minipropertymanagement.enums.OfferStatus;
import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Data
public class OfferResponse {


    private long id;

    private BigDecimal offerPrice;

    private OfferStatus offerStatus;

    private UserResponse customer;

    private PropertySmallResponse property;
    private boolean acceptedByOwner;
    private boolean acceptedByCustomer;


    private Object createdDate;

    private Object lastModifiedDate;

}
