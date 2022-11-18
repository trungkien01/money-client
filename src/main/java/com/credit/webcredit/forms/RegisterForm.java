package com.credit.webcredit.forms;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class RegisterForm {
    private String phone;
    private String fullName;
    @Email
    @NotEmpty
    private String email;
    private String password;
    private String confirm;
}
