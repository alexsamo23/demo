package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name="card")
public class Card {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="number",nullable = false)
    private String number;
    @Column(name="name",nullable = false)
    private String name;
    @Column(name="cvv",nullable = false)
    private String cvv;
    @Column(name="expire",nullable = false)
    private String expireDate;//validare conversie
    @Column(name="sold",nullable = false)
    private int sold;
    @Column(name="iban",nullable = false)
    private String IBAN;
    @Column(name="pin",nullable = false)
    private String PIN;


}