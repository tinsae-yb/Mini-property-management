package com.example.minipropertymanagement.dto.common;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AddressDto {
    @NotNull
    private String street;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String zip;
}
