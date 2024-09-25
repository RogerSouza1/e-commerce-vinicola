package com.orange.vinicola.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping("/dashboard")
    public String index() {
        return "dashboard";
    }

    @PostMapping("/dashboard")
    public String indexPost() {
        return "dashboard";
    }
}
