package com.example.projecteks.controllers;

import com.example.projecteks.models.Task;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class LoginController {

    @GetMapping("addUser")
    private String addTask(Model model){
        model.addAttribute("task", new Task());
        return "addUser";
    }
}
