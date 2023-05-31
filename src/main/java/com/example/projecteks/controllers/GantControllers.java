package com.example.projecteks.controllers;


import com.example.projecteks.dto.DaysFromNowDTO;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;

import com.example.projecteks.utilities.DateGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;

@Controller
public class GantControllers {
    DatabaseInterface database = new Database();

    @GetMapping("/showGant/{projectId}")
    public String showGant(Model model, @PathVariable int projectId, HttpSession session) throws RuntimeException{
        DaysFromNowDTO daysFromNow =  (DaysFromNowDTO) session.getAttribute("daysFromNow");
        if(daysFromNow==null){
            daysFromNow = new DaysFromNowDTO();
            daysFromNow.setDaysFromNow(0);
            session.setAttribute("daysFromNow", new DaysFromNowDTO());
        }
        model.addAttribute("objectList", database.getTasks(projectId));
        model.addAttribute("dateDTOList", DateGenerator.getDateDTOList(daysFromNow.getDaysFromNow()));
        model.addAttribute("projectId",projectId);
        return "Task/showGant";
    }

    @GetMapping("/increment/{projectId}")
    public String increment(@PathVariable int projectId,HttpSession session){
        DaysFromNowDTO daysFromNow =  (DaysFromNowDTO) session.getAttribute("daysFromNow");
        daysFromNow.addDaysFromNow(30);
        session.setAttribute("daysFromNow",daysFromNow);

        return "redirect:/showGant/"+ projectId;
    }
    @GetMapping("/now/{projectId}")
    public String now(@PathVariable int projectId,HttpSession session){
        DaysFromNowDTO daysFromNow =  (DaysFromNowDTO) session.getAttribute("daysFromNow");
        daysFromNow.addDaysFromNow(0);
        session.setAttribute("daysFromNow",daysFromNow);
        return "redirect:/showGant/"+ projectId;
    }


    @GetMapping("/decrement/{projectId}")
    public String decrement(@PathVariable int projectId,HttpSession session){
        DaysFromNowDTO daysFromNow =  (DaysFromNowDTO) session.getAttribute("daysFromNow");
        daysFromNow.addDaysFromNow(-30);
        session.setAttribute("daysFromNow",daysFromNow);
        return "redirect:/showGant/"+ projectId;
    }
    @ExceptionHandler(SQLException.class)
    public String handleError(Model model, Exception exception, HttpServletRequest request) {
        model.addAttribute("message",exception.getMessage());
        model.addAttribute("urlBack",request.getHeader("Referer"));
        return "exceptions/SQLExceptionPage";
    }

}
