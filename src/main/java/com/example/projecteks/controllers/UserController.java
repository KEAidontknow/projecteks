package com.example.projecteks.controllers;

import com.example.projecteks.models.User;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;

@Controller
public class UserController {
    DatabaseInterface database = new Database();
    @GetMapping("showUser")
    private String showTasks(Model model) throws RuntimeException {
        model.addAttribute("userList", database.getUser());

        return "users/showUser";
    }
    @GetMapping("/createUser")
    private String createUser(Model model){
        model.addAttribute("user", new User());
        return "users/createUser";
    }
    @PostMapping("/createUser")
    private String userCreated(@ModelAttribute User user) throws RuntimeException{

        database.addUser(user);
        return "redirect:/login";
    }

    @GetMapping ("/userSite")
    private String userSite(Model model){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("userName",userName);
        return "users/userSite";
    }

    @ExceptionHandler(SQLException.class)
    public String handleError(Model model, Exception exception, HttpServletRequest request) {
        model.addAttribute("message",exception.getMessage());
        model.addAttribute("urlBack",request.getHeader("Referer"));
        return "exceptions/SQLExceptionPage";
    }
}
