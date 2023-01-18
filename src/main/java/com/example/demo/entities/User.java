package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="lastname")
    private String lastName;
    @Column(name="firstname")
    private String firstName;
    @Column(name="email")
    private String email;
    @Column(name="pin")
    private String PIN;
    @Column(name="phonenumber")
    private String phoneNumber;

}
