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

import java.util.List;

@Controller
public class ProjectController {
    DatabaseInterface database = new Database();
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
        return "redirect:/showProject/";
    }

    @GetMapping("deleteProject/{projectID}")
    public String deleteProject (@PathVariable int projectID){
        database.deleteAllTasksInProject(projectID);
        database.deleteById(projectID);
        return "redirect:/showProject/";
    }

    @GetMapping("/updateProjectName/{user}/update/{projectId}")
    public String showUpdateProjectNameForm(@PathVariable("projectId") int projectId, Model model, @PathVariable String user) {
        Project project = database.getCertainProject(projectId);
        model.addAttribute("project", project);


        return "updateProjectName";
    }

    @PostMapping("/showProject/{user}/update/{projectId}")
    public String updateProjectName(@ModelAttribute("project") Project project, @PathVariable("projectId") int projectId, Model model, @PathVariable String user) {
        database.updateProjectName(projectId, project.getProjectName());
        List<Project> pList = database.showProjects(); //Fetcher den opdateret projektliste
        model.addAttribute("pList", pList); // tilføjer den opdaterede liste til modellen
        model.addAttribute("user", user);



        return "showProjects";

    }


}
