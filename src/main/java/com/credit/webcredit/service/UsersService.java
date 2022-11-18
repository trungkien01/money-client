package com.credit.webcredit.service;

import com.credit.webcredit.entity.Users;
import com.credit.webcredit.forms.RegisterForm;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;

import java.util.List;

public interface UsersService extends UserDetailsService {
    String saveUser(RegisterForm registerForm, Model model);
    Users getCurrentUser();
    Long getUserId();
    void changePassword(Users users, String newPassword);
    boolean matches(String oldPassWord, String newPassWord);
    String sendEmailPassword(String email,Model mode);
}
