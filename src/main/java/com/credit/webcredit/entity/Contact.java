package com.credit.webcredit.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "contact")
@Data
public class Contact extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "message")
    private String message;

    @Column(name = "user_id")
    private Long userId;

}
