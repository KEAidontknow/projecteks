package com.example.projecteks.controllers;

import com.example.projecteks.models.Project;
import com.example.projecteks.models.User;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class ProjectController {
    DatabaseInterface database = new Database();
    @GetMapping("showProject/{user}")
    private String showProjects(Model model, @PathVariable String user){
        model.addAttribute("pList", database.showProjects());
        model.addAttribute("user", user);
        return "showProjects";
    }

    @GetMapping("addProject/{user}")
    public String addProject(Model model, @PathVariable String user){
        model.addAttribute("project", new Project());
        model.addAttribute("user", user);
        return "addProject";
    }
    @PostMapping("projectAdded/{user}")
    public String projectAdded(@ModelAttribute("project") Project project, @PathVariable String user){
        database.addProject(project);
        return "redirect:/showProject/" + user;
    }

    @GetMapping("deleteProject/{user}/{projectID}")
    public String deleteProject (Model model, @PathVariable int projectID, @PathVariable String user){
        database.deleteAllTasksInProject(projectID);
        database.deleteById(projectID);
        model.addAttribute("user", user);
        return "redirect:/showProject/" + user;
    }


}
