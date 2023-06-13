package com.example.minipropertymanagement.domain;


import com.example.minipropertymanagement.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract public class Person {

    @Id
    @GeneratedValue
    private long id;

    private String firstName;
    private String lastName;
    private Role role;

}
