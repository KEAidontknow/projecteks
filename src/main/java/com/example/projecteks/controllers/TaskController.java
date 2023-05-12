package com.example.projecteks.controllers;

import com.example.projecteks.models.Project;
import com.example.projecteks.models.Task;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class TaskController {
    DatabaseInterface database = new Database();

    @GetMapping("showTask/{user}/{projectId}")
    private String showTasks(Model model,@PathVariable int projectId, @PathVariable String user) {
        model.addAttribute("list", database.getTasks(projectId));
        model.addAttribute("projectId",projectId);
        model.addAttribute("user", user);
        return "showTasks";
    }

    @GetMapping("addTask/{user}/{projectId}")
    private String addTask(Model model, @PathVariable Integer projectId, @PathVariable String user) {
        model.addAttribute("task", new Task());
        model.addAttribute("projectId", projectId);
        model.addAttribute("user", user);
        //System.out.println("Test pre projectId: " + projectId);
        return "addTask";
    }

    @PostMapping("taskAdded/{user}")
    private String taskAdded(@ModelAttribute("task") Task task, @ModelAttribute("projectId") int projectId, @PathVariable String user) {
        task.setProjectId(projectId);
        System.out.println("Test post projectId: " + task.getProjectId());
        database.addTask(task);
        return "redirect:/showTask/"+ user + "/" + projectId;
    }

    @GetMapping("removeTask/{user}/{projectId}/{taskId}")
    private String removeTask(@PathVariable int projectId,@PathVariable int taskId, @PathVariable String user) {
        database.removeTask(taskId);
        return "redirect:/showTask/"+ user + "/" + projectId;
    }

    @GetMapping("updateState/{user}/{projectId}/{taskId}/{state}")
    private String updateTask(@PathVariable String user,@PathVariable int projectId,@PathVariable int taskId, @PathVariable int state) {
        database.updateState(taskId, state);
        return "redirect:/showTask/"+ user + "/" + projectId;
    }


    @PostMapping("editTask/{user}/{project}/{taskId}")
    private String editTask(@PathVariable String user,@PathVariable int projectId,@PathVariable int taskId, @ModelAttribute Task updatedTask) {
        database.editTask(taskId, updatedTask);
        return "redirect:/showTask/"+ user + "/" + projectId;

    }


    @GetMapping("/updateTask")
    public String updateTask(@ModelAttribute("task") Task task) {
        database.updateTask(task);
        return "redirect:/tasks";
    }

}




