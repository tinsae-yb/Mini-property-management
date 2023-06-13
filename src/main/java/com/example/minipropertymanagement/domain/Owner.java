package com.example.minipropertymanagement.domain;


import com.example.minipropertymanagement.domain.enums.AccountStatus;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Owner extends Person {

    private AccountStatus accountStatus;
}
