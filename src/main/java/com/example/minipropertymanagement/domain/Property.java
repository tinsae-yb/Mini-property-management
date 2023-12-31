package com.example.minipropertymanagement.domain;


import com.example.minipropertymanagement.enums.PropertyStatus;
import com.example.minipropertymanagement.enums.PropertyType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Data
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String image;
    private BigDecimal price;

    private Address address;

    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY , cascade = CascadeType.REMOVE)
    private List<Offer> offers;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User owner;

    private int area;
    private int numberOfBedrooms;
    private int numberOfBathrooms;
    private int numberOfFloors;
    private int yearBuilt;

    @Column(columnDefinition = "TEXT")
    private String description;
    private PropertyType propertyType;



    private PropertyStatus propertyStatus;


    @CreationTimestamp
    private Instant createdDate;

    @UpdateTimestamp
    private Instant lastModifiedDate;


}
