package com.example.projecteks.controllers;

import ch.qos.logback.core.model.Model;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginRedirect() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "users/login";
        }

        return "redirect:/userSite"; //Todo "1" er hardcodet, og skal substitueres med den brugerID på den der rent faktisk er logget ind
    }
}
