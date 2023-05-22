package com.example.projecteks.controllers;

import com.example.projecteks.models.Assign;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;
import org.springframework.data.relational.core.sql.Assignment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AssignmentController {
    DatabaseInterface database = new Database();

    @GetMapping("/addAssignment/{projectId}/{taskId}")
    public String addAssignment(@PathVariable int projectId, @PathVariable int taskId, Model model){

        model.addAttribute("projectId",projectId);
        model.addAttribute("taskId", taskId);
        model.addAttribute("assignment",new Assign());
        model.addAttribute("userList", database.getUser());

        return "/addAssignment";
    }
    @PostMapping ("/assignmentAdded")
    public String addAssignment(@ModelAttribute("projectId") int projectId, @ModelAttribute("assignment") Assign assignment){
        database.addAssignment(assignment.getUserId(),assignment.getUserId());
        return "redirect:/showTask/"+projectId;
    }
    @PostMapping("/getAssigmentByUserId")
    public String getAssignmentByUserId(@RequestParam("userId") int userId){
        database.getAssignmentsByUserId(userId);
        return "redirect: /userPage";
    }
    @GetMapping("/getAssigmentByTaskId/{projectId}/{userId}")
    public String getAssignmentByTaskId(@PathVariable int projectId,@PathVariable int userId){
        database.getAssignmentsByUserId(userId);
        return "redirect: /showTask/"+projectId;
    }


}
