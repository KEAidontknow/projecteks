package com.example.projecteks.controllers;

import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class AssignmentController {
    DatabaseInterface database = new Database();

    @GetMapping("/addAssignment/{projectId}/{taskId}")
    public String addAssignment(@PathVariable int projectId, @PathVariable int taskId, Model model){
        int userId = 0;
        model.addAttribute("projectId",projectId);
        model.addAttribute("taskId",taskId);
        model.addAttribute("userId", userId);
        model.addAttribute("userList", database.getUser());

        return "/addAssignment";
    }
    @PostMapping ("/assignmentAdded")
    public String addAssignment(@RequestParam("projectId") int projectId, @RequestParam("taskId") int taskId,@RequestParam("userId") int userId){
        database.addAssignment(taskId,userId);
        return "redirect: /showTask/"+projectId;
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
