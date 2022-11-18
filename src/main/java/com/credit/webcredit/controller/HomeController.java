package com.credit.webcredit.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    private final Logger logger = LogManager.getLogger(HomeController.class);
    @GetMapping(value = {"/index", "/"})
    public String index(){
        logger.info("----- index ---- ");
        return "index";
    }
}
