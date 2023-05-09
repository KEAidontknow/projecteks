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
public class WebController {
    DatabaseInterface database = new Database();

    @GetMapping("showTask")
    private String showTasks(Model model) {
        model.addAttribute("list", database.getTasks());

        return "showTasks";
    }

    @GetMapping("addTask")
    private String addTask(Model model) {
        model.addAttribute("task", new Task());
        return "addTask";
    }

    @PostMapping("taskAdded")
    private String taskAdded(@ModelAttribute Task task) {
        database.addTask(task);
        return "redirect:/showTask";
    }

    @GetMapping("removeTask/{taskId}")
    private String removeTask(@PathVariable int taskId) {
        database.removeTask(taskId);
        return "redirect:/showTask";
    }

    @GetMapping("updateState/{taskId}/{state}")
    private String updateTask(@PathVariable int taskId, @PathVariable int state) {
        database.updateState(taskId, state);
        return "redirect:/showTask";
    }


    @PostMapping("editTask/{taskId}")
    private String editTask(@PathVariable int taskId, @ModelAttribute Task updatedTask) {
        database.editTask(taskId, updatedTask);
        return "redirect:/showTask";

    }


    @GetMapping("/updateTask")
    public String updateTask(@ModelAttribute("task") Task task) {
        database.updateTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("showProject")
    private String showProjects(Model model){
    model.addAttribute("pList", database.showProjects());
    return "showProjects";
}

    @GetMapping("addProject")
    public String addProject(Model model){
        model.addAttribute("project", new Project());
        return "addProject";
    }
    @PostMapping("projectAdded")
    public String projectAdded(@ModelAttribute("project") Project project){
      database.addProject(project);
        return "redirect:/showProject";
    }

    @DeleteMapping ("deleteProject{projectID}")
    public String deleteProject (@PathVariable int projectID){
        database.deleteById(projectID);
        return "redirect:/showProject";
    }

}




