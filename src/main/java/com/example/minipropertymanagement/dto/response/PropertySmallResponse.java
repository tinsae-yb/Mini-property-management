package com.example.minipropertymanagement.dto.response;

import com.example.minipropertymanagement.domain.enums.PropertyType;
import com.example.minipropertymanagement.dto.common.AddressDto;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class PropertySmallResponse {

    private long id;


    private String image;

    private BigDecimal price;

    private PropertyType propertyType;

    private AddressDto address;


    private int area;

    private int numberOfBedrooms;

    private int numberOfBathrooms;

    private int numberOfFloors;

}