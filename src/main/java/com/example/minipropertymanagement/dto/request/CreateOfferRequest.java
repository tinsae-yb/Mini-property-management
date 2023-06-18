package com.example.minipropertymanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Data
@Validated
public class CreateOfferRequest {


    @NotNull

    private BigDecimal offerPrice;

}
