package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

//@Data
@Entity
@Table(name="user")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
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

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private Set<Card> cards;


}
