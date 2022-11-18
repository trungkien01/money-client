package com.credit.webcredit.controller;

import com.credit.webcredit.forms.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
