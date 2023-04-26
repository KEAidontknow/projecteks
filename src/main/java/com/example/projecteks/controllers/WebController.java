package com.example.projecteks.controllers;

import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    DatabaseInterface database = new Database();
    @GetMapping("showTask")
    private String showTasks(Model model){
        model.addAttribute("list", database.getTasks());

        return "showTasks";
    }

}
