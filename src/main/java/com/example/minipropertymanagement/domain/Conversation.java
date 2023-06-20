package com.example.minipropertymanagement.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Conversation {

    @Id
    @GeneratedValue
    private long id;



    @ManyToOne(fetch = FetchType.LAZY)
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;


    @ManyToOne(fetch = FetchType.LAZY)
    private Property property;

    @OneToMany
    private List<Message> messages;



}
