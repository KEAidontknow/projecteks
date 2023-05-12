package com.example.projecteks.controllers;


import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;

import com.example.projecteks.utilities.DateGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@Controller
public class GantControllers {
    DatabaseInterface database = new Database();

    @GetMapping("showGant/{projectId}")
    public String showGant(Model model, @PathVariable int projectId){
        model.addAttribute("objectList", database.getTasks(projectId));
        model.addAttribute("dateList", DateGenerator.getDateList());
        LocalDate date;
        //date.getDayOfWeek();
        //date.getYear();
        //date.getDayOfMonth();
                //date.getMonth();
                //date.getMonthValue();
        model.addAttribute("projectId",projectId);
        return "showGant";
    }

}
