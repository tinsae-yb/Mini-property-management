package com.example.minipropertymanagement.domain;


import com.example.minipropertymanagement.domain.enums.PropertyType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Property {
    @Id
    @GeneratedValue
    private long id;

    private String image;
    private BigDecimal price;

    private Address address;

    @OneToMany
    @JoinColumn(name = "property_id")
    private List<Bid> bids;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

    private int area;
    private int numberOfBedrooms;
    private int numberOfBathrooms;
    private int numberOfFloors;
    private int yearBuilt;
    private String description;
    private PropertyType propertyType;

    @ManyToMany
    private List<Tag> tags;


}
