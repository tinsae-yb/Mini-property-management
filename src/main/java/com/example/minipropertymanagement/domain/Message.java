package com.example.minipropertymanagement.domain;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue
    private long id;

    private String message;


}
