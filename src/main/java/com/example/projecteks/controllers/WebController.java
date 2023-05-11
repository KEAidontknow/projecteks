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

    @GetMapping("showTask/{projectId}")
    private String showTasks(Model model, @PathVariable int projectId) {
        model.addAttribute("list", database.getTasks(projectId));
        model.addAttribute("projectId", projectId);

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
        return "redirect:/showTask/" + projectId;
    }

    @GetMapping("removeTask/{projectId}/{taskId}")
    private String removeTask(@PathVariable int projectId, @PathVariable int taskId) {
        database.removeTask(taskId);
        return "redirect:/showTask/" + projectId;
    }

    @GetMapping("updateState/{projectId}/{taskId}/{state}")
    private String updateTask(@PathVariable int projectId, @PathVariable int taskId, @PathVariable int state) {
        database.updateState(taskId, state);
        return "redirect:/showTask/" + projectId;
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
    private String showProjects(Model model) {
        model.addAttribute("pList", database.showProjects());
        return "showProjects";
    }

    @GetMapping("addProject")
    public String addProject(Model model) {
        model.addAttribute("project", new Project());
        return "addProject";
    }

    @PostMapping("projectAdded")
    public String projectAdded(@ModelAttribute("project") Project project) {
        database.addProject(project);
        return "redirect:/showProject";
    }

    @DeleteMapping("deleteProject{projectID}")
    public String deleteProject(@PathVariable int projectID) {
        database.deleteById(projectID);
        return "redirect:/showProjects";
    }


    //Opdatere project navn

    @GetMapping("/updateProjectName/update/{projectId}")
    public String updateProjectNameForm(@PathVariable("projectId") int projectId, Model model) {
        Project project = database.getCertainProject(projectId);
        model.addAttribute("project", project);
        return "updateProjectName";
    }

    @PostMapping("/updatedProjectName/update/{projectId}")
    public String updatedProjectName(@ModelAttribute("project") Project project, @PathVariable("projectId") int projectId, Model model) {
        database.updateProjectName(projectId, project.getProjectName());
        return "redirect:/showProjects";
    }
}





