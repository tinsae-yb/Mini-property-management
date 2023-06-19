package com.example.minipropertymanagement.dto.response;

import com.example.minipropertymanagement.domain.enums.PropertyType;
import com.example.minipropertymanagement.dto.common.AddressDto;
import com.example.minipropertymanagement.dto.common.TagDTO;
import lombok.Data;
import com.example.minipropertymanagement.domain.enums.PropertyStatus;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PostPropertyResponse {

    private long id;


    private String image;

    private BigDecimal price;


    private AddressDto address;


    private int area;

    private int numberOfBedrooms;

    private int numberOfBathrooms;

    private int numberOfFloors;

    private int yearBuilt;

    private String description;

    private PropertyType propertyType;
    private PropertyStatus propertyStatus;

    private List<TagDTO> tags;


}
