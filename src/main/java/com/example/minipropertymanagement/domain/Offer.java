package com.example.minipropertymanagement.domain;


import com.example.minipropertymanagement.enums.OfferStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Offer {

    @Id
    @GeneratedValue
    private long id;

    private BigDecimal offerPrice;

    private OfferStatus offerStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    private boolean acceptedByOwner;
    private boolean acceptedByCustomer;

}
