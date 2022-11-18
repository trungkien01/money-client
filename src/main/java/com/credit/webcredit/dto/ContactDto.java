package com.credit.webcredit.dto;

import lombok.Data;

import javax.persistence.Column;
@Data
public class ContactDto {
    private Long id;

    private String fullName;

    private String phone;

    private String email;

    private String message;

    private Long userId;
}
