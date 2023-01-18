package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name="card")
public class Card {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="number")
    private String cardNumber;
    @Column(name="name")
    private String cardName;
    @Column(name="cvv")
    private String cvv;
    @Column(name="expire")
    private String expireDate;
    @Column(name="sold")
    private int soldCard;
    @Column(name="iban")
    private String IBAN;



}