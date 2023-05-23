package com.example.projecteks.controllers;

import com.example.projecteks.models.User;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
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
        return "redirect:/login";
    }

    @GetMapping ("/userSite")
    private String userSite(Model model){
        model.addAttribute("userId",2); //Todo "2" er hardcodet, og skal substitueres med den brugerID på den der rent faktisk er logget ind
        return "users/userSite";
    }
}
