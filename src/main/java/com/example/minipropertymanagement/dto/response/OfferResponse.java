package com.example.minipropertymanagement.dto.response;

import com.example.minipropertymanagement.domain.Property;
import com.example.minipropertymanagement.domain.User;
import com.example.minipropertymanagement.domain.enums.OfferStatus;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfferResponse {


    private long id;

    private BigDecimal offerPrice;

    private OfferStatus offerStatus;

    private UserSmallResponse customer;

    private PropertySmallResponse property;

}
