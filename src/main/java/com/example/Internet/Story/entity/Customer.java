package com.example.Internet.Story.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Customer")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    private int failedAttempts = 0;

    private LocalDateTime lockTime;
}