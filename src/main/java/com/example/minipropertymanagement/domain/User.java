package com.example.minipropertymanagement.domain;


import com.example.minipropertymanagement.domain.enums.AccountStatus;
import com.example.minipropertymanagement.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name = "unique_email" )})
public class User {

    @Id
    @GeneratedValue
    private long id;

    private String firstName;
    private String lastName;
    private Role role;


//    @Column(unique = true)
    private String email;
    private String password;
    private AccountStatus accountStatus;

    @OneToMany(mappedBy = "user")
    private List<Property> properties;
}
