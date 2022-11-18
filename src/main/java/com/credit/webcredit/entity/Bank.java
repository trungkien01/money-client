package com.credit.webcredit.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "bank")
@Data
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
}
