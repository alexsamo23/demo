package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="lastname",nullable = false)
    private String lastName;
    @Column(name="firstname",nullable = false)
    private String firstName;
    @Column(name="email",nullable = false)
    private String email;
    @Column(name="password",nullable = false)
    private String password;
    @Column(name="phonenumber",nullable = false)
    private String phoneNumber;

}
