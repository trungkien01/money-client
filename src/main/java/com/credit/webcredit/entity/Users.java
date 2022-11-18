package com.credit.webcredit.entity;

import com.credit.webcredit.enums.Role;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class Users extends BaseEntity{
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

    @Column(name = "password")
    private String password;

    @Column(name = "is_check_user")
    private boolean isCheckUser;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

}
