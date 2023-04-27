package com.example.projecteks.controllers;

import com.example.projecteks.models.Task;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {
    DatabaseInterface database = new Database();
    @GetMapping("showTask")
    private String showTasks(Model model){
        model.addAttribute("list", database.getTasks());

        return "showTasks";
    }
    @GetMapping("addTask")
    private String addTask(Model model){
        model.addAttribute("task", new Task());
        return "addTask";
    }
    @PostMapping("taskAdded")
    private String taskAdded(@ModelAttribute Task task){
        database.addTask(task);
        return "redirect:/showTask";
    }
    @GetMapping("removeTask/{taskId}")
    private String removeTask(@PathVariable int taskId){
        database.removeTask(taskId);
        return "redirect:/showTask";
    }
    private String taskRemoved(@ModelAttribute int taskId){

        database.removeTask(taskId);

        return "redirect:/showTask";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute("task") Task task) {

        return "redirect:/taskList";
    }


}
