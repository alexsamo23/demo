package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;


@Entity
@Table(name="account_transaction")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="amount",nullable = false)
    private int amount;
    @Column(name="date",nullable = false)
    private Date date;
    @Column(name="type",nullable = false)
    private String type;
    @ManyToOne
    @JoinColumn(name="card_id", nullable=false)
    private Card card;
}