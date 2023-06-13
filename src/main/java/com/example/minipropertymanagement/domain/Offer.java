package com.example.minipropertymanagement.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Offer {

    @Id
    @GeneratedValue
    private long id;

}
