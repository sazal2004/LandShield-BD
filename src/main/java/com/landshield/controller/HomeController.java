package com.landshield.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() { return "index"; }

    @GetMapping("/verify")
    public String verify() { return "verify"; }

    @GetMapping("/about")
    public String about() { return "about"; }

    @GetMapping("/login")
    public String login() { return "login"; }
}
