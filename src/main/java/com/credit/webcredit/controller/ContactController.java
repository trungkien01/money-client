package com.credit.webcredit.controller;

import com.credit.webcredit.dto.ContactDto;
import com.credit.webcredit.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contact")
    public String contact(Model model){
        model.addAttribute("contact", new ContactDto());
        return "contact";
    }

    @PostMapping("saveContact")
    public String saveContact(@ModelAttribute("contact")ContactDto contactDto, Model model){
        return contactService.saveConteact(contactDto, model);
    }
}
