package com.credit.webcredit.controller;

import com.credit.webcredit.forms.RegisterForm;
import com.credit.webcredit.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegisterController {
    @Autowired
    private final UsersService usersService;

    public RegisterController(UsersService usersService) {
        this.usersService = usersService;
    }

    @ModelAttribute("registration")
    public RegisterForm userRegistrationDto() {
        return new RegisterForm();
    }

    @GetMapping
    public String registration(Model model){
        model.addAttribute("registration",new RegisterForm());
        return "register";
    }
    @PostMapping
    public String saveUser(@ModelAttribute("registration") RegisterForm registerForm, Model model){
        return usersService.saveUser(registerForm,model);
    }
}
