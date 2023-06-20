package com.example.minipropertymanagement.dto.request;


import com.example.minipropertymanagement.dto.common.AddressDto;
import com.example.minipropertymanagement.dto.common.TagDTO;
import com.example.minipropertymanagement.enums.PropertyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

@Validated
@Data
public class PostPropertyRequest {



    @NotNull
    private String image;

    @NotNull
    private BigDecimal price;

    @Valid
    @NotNull
    private AddressDto address;


    @Min(1)
    private int area;
    @Min(0)
    private int numberOfBedrooms;
    @Min(0)
    private int numberOfBathrooms;
    @Min(1)
    private int numberOfFloors;
    @Min(1990)
    private int yearBuilt;
    @NotNull
    private String description;
    @NotNull
    private PropertyType propertyType;


    private List<TagDTO> tags;


}
