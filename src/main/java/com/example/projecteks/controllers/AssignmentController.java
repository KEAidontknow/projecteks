package com.example.projecteks.controllers;

import com.example.projecteks.dto.DaysFromNowDTO;
import com.example.projecteks.models.Assign;
import com.example.projecteks.models.Task;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;
import com.example.projecteks.utilities.DateGenerator;
import com.example.projecteks.utilities.TimeCalc;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;


@Controller
public class AssignmentController {
    DatabaseInterface database = new Database();


    @GetMapping("/addAssignment/{projectId}/{taskId}")
    public String addAssignment(@PathVariable int projectId, @PathVariable int taskId, Model model,
                                HttpSession session) throws RuntimeException{
        DaysFromNowDTO daysFromNow =  (DaysFromNowDTO) session.getAttribute("daysFromNow");
        if(daysFromNow==null){
            daysFromNow = new DaysFromNowDTO();
            daysFromNow.setDaysFromNow(0);
            session.setAttribute("daysFromNow", new DaysFromNowDTO());
        }

        Assign assignment = new Assign();
        model.addAttribute("taskId",taskId);
        model.addAttribute("projectId",projectId);
        model.addAttribute("assignment",assignment);
        model.addAttribute("userList", database.getUser());
        Task task = database.getTaskById(taskId);
        model.addAttribute("taskStart",task.getStartDate());
        model.addAttribute("taskDeadline",task.getDeadline());
        ArrayList<Assign> assignmentsList = database.getAssignmentsByTaskId(taskId);
        model.addAttribute("objectList", assignmentsList);
        model.addAttribute("dateDTOList", DateGenerator.getDateDTOList(daysFromNow.getDaysFromNow()));
        model.addAttribute("deploymentRate",TimeCalc.deploymentRate(task,assignmentsList));

        return "Assignment/addAssignment";
    }
    @PostMapping ("/assignmentAdded")
    public String addAssignment(@ModelAttribute("projectId") int projectId,@ModelAttribute("taskId") int taskId,
                                @ModelAttribute("assignment") Assign assignment) throws RuntimeException{
        database.addAssignment(taskId,assignment.getUserName(),assignment.getStartDate(),assignment.getEndDate());
        return "redirect:/addAssignment/"+projectId+"/"+taskId;
    }
    @GetMapping("/myAssignment")
    public String getAssignmentByUserId(Model model) throws RuntimeException{
        model.addAttribute("list", database.getAssignedTasks());
        model.addAttribute("objectList", database.getAssignmentsByUserName(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("dateDTOList", DateGenerator.getDateDTOList(0));
        return "Assignment/myAssignment";
    }

    @GetMapping("updateStateAssignment/{taskId}/{state}")
    private String updateTask(@PathVariable int taskId, @PathVariable int state) throws RuntimeException {
        database.updateState(taskId, state);
        return "redirect:/myAssignment";
    }
    @GetMapping("/assignDate/increment/{projectId}/{taskId}")
    public String increment(@PathVariable int projectId, @PathVariable int taskId,HttpSession session){
        DaysFromNowDTO daysFromNow =  (DaysFromNowDTO) session.getAttribute("daysFromNow");
        daysFromNow.addDaysFromNow(30);
        session.setAttribute("daysFromNow",daysFromNow);
        return "redirect:/addAssignment/"+projectId+"/"+taskId;
    }
    @GetMapping("/assignDate/now/{projectId}/{taskId}")
    public String now(@PathVariable int projectId, @PathVariable int taskId,HttpSession session){
        DaysFromNowDTO daysFromNow =  (DaysFromNowDTO) session.getAttribute("daysFromNow");
        daysFromNow.addDaysFromNow(0);
        session.setAttribute("daysFromNow",daysFromNow);
        return "redirect:/addAssignment/"+projectId+"/"+taskId;
    }


    @GetMapping("/assignDate/decrement/{projectId}/{taskId}")
    public String decrement(@PathVariable int projectId, @PathVariable int taskId,HttpSession session){
        DaysFromNowDTO daysFromNow =  (DaysFromNowDTO) session.getAttribute("daysFromNow");
        daysFromNow.addDaysFromNow(-30);
        session.setAttribute("daysFromNow",daysFromNow);
        return "redirect:/addAssignment/"+projectId+"/"+taskId;
    }


    @ExceptionHandler(SQLException.class)
    public String handleError(Model model, Exception exception, HttpServletRequest request) {
        model.addAttribute("message",exception.getMessage());
        model.addAttribute("urlBack",request.getHeader("Referer"));
        return "exceptions/SQLExceptionPage";
    }


}
