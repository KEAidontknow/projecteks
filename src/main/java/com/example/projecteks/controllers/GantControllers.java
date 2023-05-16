package com.example.projecteks.controllers;


import com.example.projecteks.DTO.IntDTO;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;

import com.example.projecteks.utilities.DateGenerator;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GantControllers {
    DatabaseInterface database = new Database();
    private int daysFromNow= 0;
    @GetMapping("/showGant/{projectId}")
    public String showGant(Model model, @PathVariable int projectId, HttpSession session){
        model.addAttribute("objectList", database.getTasks(projectId));
        model.addAttribute("dateDTOList", DateGenerator.getDateDTOList(daysFromNow));
        model.addAttribute("projectId",projectId);
        return "showGant";
    }

    @GetMapping("/increment/{projectId}")
    public String increment(@PathVariable int projectId){
        daysFromNow += 30;
        return "redirect:/showGant/"+ projectId;
    }
    @GetMapping("/now/{projectId}")
    public String now(@PathVariable int projectId){
        daysFromNow = 0;
        return "redirect:/showGant/"+ projectId;
    }


    @GetMapping("/decrement/{projectId}")
    public String decrement(@PathVariable int projectId){
        daysFromNow -= 30;
        return "redirect:/showGant/"+ projectId;
    }

}
