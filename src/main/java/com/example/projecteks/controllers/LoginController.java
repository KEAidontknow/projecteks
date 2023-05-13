package com.example.projecteks.controllers;

import com.example.projecteks.models.User;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    DatabaseInterface database = new Database();
    @GetMapping("showUser")
    private String showTasks(Model model){
        model.addAttribute("userList", database.getUser());

        return "users/showUser";
    }
    @GetMapping("/createUser")
    private String createUser(Model model){
        model.addAttribute("user", new User());
        return "users/createUser";
    }
    @PostMapping("/createUser")
    private String userCreated(@ModelAttribute User user){
        database.addUser(user);
        return "redirect:/";
    }


}
