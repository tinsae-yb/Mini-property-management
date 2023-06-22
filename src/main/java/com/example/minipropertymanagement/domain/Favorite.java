package com.example.minipropertymanagement.domain;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Property property;
}
