package com.example.minipropertymanagement.domain;


import com.example.minipropertymanagement.domain.enums.AccountStatus;
import com.example.minipropertymanagement.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
@Table(name = "users")
 public class User {

    @Id
    @GeneratedValue
    private long id;

    private String firstName;
    private String lastName;
    private List<Role> roles;

    private String email;
    private String password;
    private AccountStatus accountStatus;
}
