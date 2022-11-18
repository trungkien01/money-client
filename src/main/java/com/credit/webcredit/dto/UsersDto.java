package com.credit.webcredit.dto;

import com.credit.webcredit.enums.Role;
import lombok.Data;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class UsersDto {

    private Long id;

    private String fullName;

    private String phone;

    private String email;

    private String password;

    private String checkPassword;

    private boolean isCheckUser;

    @Enumerated(EnumType.STRING)
    private Role role;
}
