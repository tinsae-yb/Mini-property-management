package com.example.minipropertymanagement.dto.request;

import com.example.minipropertymanagement.domain.Address;
import com.example.minipropertymanagement.domain.Tag;
import com.example.minipropertymanagement.domain.enums.PropertyType;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

@Validated
public class PostPropertyRequest {


    @NotNull
    private BigDecimal price;

    @NotNull
    private Address address;


    @NotNull
    private int area;
    @NotNull
    private int numberOfBedrooms;
    @NotNull
    private int numberOfBathrooms;
    @NotNull
    private int numberOfFloors;
    @NotNull
    private int yearBuilt;
    @NotNull
    private String description;
    @NotNull
    private PropertyType propertyType;


    private List<Tag> tags;


}
