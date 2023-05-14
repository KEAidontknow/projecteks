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

    @GetMapping("showTask/{projectId}")
    private String showTasks(Model model,@PathVariable int projectId) {
        model.addAttribute("list", database.getTasks(projectId));
        model.addAttribute("projectId",projectId);
        return "showTasks";
    }

    @GetMapping("addTask/{projectId}")
    private String addTask(Model model, @PathVariable Integer projectId) {
        model.addAttribute("task", new Task());
        model.addAttribute("projectId", projectId);
        //System.out.println("Test pre projectId: " + projectId);
        return "addTask";
    }

    @PostMapping("taskAdded")
    private String taskAdded(@ModelAttribute("task") Task task, @ModelAttribute("projectId") int projectId) {
        task.setProjectId(projectId);
        System.out.println("Test post projectId: " + task.getProjectId());
        database.addTask(task);
        return "redirect:/showTask/"+ projectId;
    }

    @GetMapping("removeTask/{taskId}")
    private String removeTask(@PathVariable String projectId,@PathVariable int taskId) {
        database.removeTask(taskId);
        return "redirect:/showTask/"+ projectId;
    }

    @GetMapping("updateState/{projectId}/{taskId}/{state}")
    private String updateTask(@PathVariable int projectId,@PathVariable int taskId, @PathVariable int state) {
        database.updateState(taskId, state);
        return "redirect:/showTask/"+ projectId;
    }


    @GetMapping("editTask/{taskId}")
    private String editTask(@PathVariable int taskId, Model model) {
        model.addAttribute("task",database.getTaskById(taskId));
        model.addAttribute("newTask", new Task());
        return "editTask";
    }

    @PostMapping("editTask/")
    private String editTask(@ModelAttribute("newTask") Task newTask,@ModelAttribute("projectId") int projectId,@ModelAttribute("id") int taskId) {
        newTask.setId(taskId);
        database.editTask(newTask);
        System.out.println("tId "+ taskId+" pId "+ projectId);
        return "redirect:/showTask/" + projectId;
    }


    /*@GetMapping("/updateTask")
    public String updateTask(@ModelAttribute("task") Task task) {
        database.updateTask(task);
        return "redirect:/tasks";
    }*/

}



