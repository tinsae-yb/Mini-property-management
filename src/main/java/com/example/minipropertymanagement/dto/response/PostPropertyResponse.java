package com.example.minipropertymanagement.dto.response;

import com.example.minipropertymanagement.domain.Tag;
import com.example.minipropertymanagement.domain.enums.PropertyType;
import com.example.minipropertymanagement.dto.common.AddressDto;
import com.example.minipropertymanagement.dto.common.TagDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PostPropertyResponse {



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


    private List<TagDTO> tags;


}
