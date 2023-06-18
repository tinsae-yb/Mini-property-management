package com.example.minipropertymanagement.domain;


import com.example.minipropertymanagement.domain.enums.PropertyStatus;
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

    @OneToMany(mappedBy = "property")
    private List<Offer> offers;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User owner;

    private int area;
    private int numberOfBedrooms;
    private int numberOfBathrooms;
    private int numberOfFloors;
    private int yearBuilt;
    private String description;
    private PropertyType propertyType;



    private PropertyStatus propertyStatus;


}
