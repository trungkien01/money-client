package com.credit.webcredit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/user")
    public String user(){
        return "users/user";
    }

    @GetMapping("/myLoan")
    public String myLoan(){
        return "users/myLoan";
    }

    @GetMapping("/payMyDebt")
    public String payMyDebt(){
        return "users/payMyDebt";
    }
}
