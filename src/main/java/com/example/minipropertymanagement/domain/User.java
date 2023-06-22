package com.example.minipropertymanagement.domain;


import com.example.minipropertymanagement.enums.AccountStatus;
import com.example.minipropertymanagement.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name = "unique_email")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private Role role;


    private String email;
    private String password;
    private AccountStatus accountStatus;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Property> properties;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Offer> offers;
}
