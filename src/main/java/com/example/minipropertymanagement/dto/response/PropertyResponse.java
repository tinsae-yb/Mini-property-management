package com.example.minipropertymanagement.dto.response;


import com.example.minipropertymanagement.dto.common.AddressDto;
import com.example.minipropertymanagement.dto.common.TagDTO;
import com.example.minipropertymanagement.enums.PropertyStatus;
import com.example.minipropertymanagement.enums.PropertyType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PropertyResponse {

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

    private UserResponse owner;

    private List<TagDTO> tags;

    private Object createdDate;

    private Object lastModifiedDate;


}
