package com.example.projecteks.controllers;

import com.example.projecteks.models.Assign;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AssignmentController {
    DatabaseInterface database = new Database();

    @GetMapping("/addAssignment/{projectId}/{taskId}")
    public String addAssignment(@PathVariable int projectId, @PathVariable int taskId, Model model){
        Assign assignment = new Assign();
        model.addAttribute("taskId",taskId);
        model.addAttribute("projectId",projectId);
        model.addAttribute("assignment",assignment);
        model.addAttribute("userList", database.getUser());

        return "Assignment/addAssignment";
    }
    @PostMapping ("/assignmentAdded")
    public String addAssignment(@ModelAttribute("projectId") int projectId,@ModelAttribute("taskId") int taskId, @ModelAttribute("assignment") Assign assignment){
        System.out.println("PostMapping: TaskId: "+taskId+", UserId: "+assignment.getUserId());
        database.addAssignment(taskId,assignment.getUserId());

        return "redirect:/showTask/"+projectId;
    }
    @GetMapping("/myAssignment/{userId}")
    public String getAssignmentByUserId(@PathVariable("userId") int userId, Model model){
        model.addAttribute("userId",userId);
        model.addAttribute("list", database.getAssignedTasksByUserId(userId));
        return "Assignment/myAssignment";
    }
    @GetMapping("/getAssigmentByTaskId/{projectId}/{userId}")
    public String getAssignmentByTaskId(@PathVariable int projectId,@PathVariable int userId){
        database.getAssignmentsByUserId(userId);
        return "redirect:/showTask/"+projectId;
    }
    @GetMapping("updateStateAssignment/{userId}/{taskId}/{state}")
    private String updateTask(@PathVariable int userId, @PathVariable int taskId, @PathVariable int state) {
        database.updateState(taskId, state);
        return "redirect:/myAssignment/"+userId;
    }


}
