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
        System.out.println("PostMapping: TaskId: "+taskId+", UserName: "+assignment.getUserName());
        database.addAssignment(taskId,assignment.getUserName());

        return "redirect:/showTask/"+projectId;
    }
    @GetMapping("/myAssignment")
    public String getAssignmentByUserId(Model model){
        model.addAttribute("list", database.getAssignedTasks());
        return "Assignment/myAssignment";
    }
    /*@GetMapping("/getAssigmentByTaskId/{projectId}")
    public String getAssignmentByTaskId(@PathVariable int projectId){
        database.getAssignmentsByUserName();
        return "redirect:/showTask/"+projectId;
    }*/
    @GetMapping("updateStateAssignment/{taskId}/{state}")
    private String updateTask(@PathVariable int taskId, @PathVariable int state) {
        database.updateState(taskId, state);
        return "redirect:/myAssignment";
    }


}
