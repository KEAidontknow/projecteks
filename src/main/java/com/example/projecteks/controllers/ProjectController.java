package com.example.projecteks.controllers;

import com.example.projecteks.models.Project;
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
    @GetMapping("showProject/{userId}")
    private String showProjects(Model model, @PathVariable int userId){
        model.addAttribute("pList", database.showUserProjects(userId));
        return "Project/showProjects";
    }

    @GetMapping("addProject")
    public String addProject(Model model){
        model.addAttribute("project", new Project());
        return "Project/addProject";
    }
    @PostMapping("projectAdded")
    public String projectAdded(@ModelAttribute("project") Project project){
        database.addProject(project);
        return "redirect:/showProject";
    }

    @PostMapping("/deleteProject/{projectID}")
    public String deleteProject (@PathVariable int projectID){
        database.deleteAllTasksInProject(projectID);
        database.deleteById(projectID);
        return "redirect:/showProject";
    }

    @GetMapping("/updateProjectName/update/{projectId}")
    public String showUpdateProjectNameForm(@PathVariable("projectId") int projectId, Model model) {
        Project project = database.getCertainProject(projectId);
        model.addAttribute("project", project);


        return "Project/updateProjectName";
    }

    @PostMapping("/update/{projectId}")
    public String updateProjectName(@ModelAttribute("project") Project project, @PathVariable("projectId") int projectId, Model model) {
        database.updateProjectName(projectId, project.getProjectName(), project.getStartDate(), project.getDeadline());
        List<Project> pList = database.showProjects(); // Fetch the updated project list
        model.addAttribute("pList", pList); // Add the updated list to the model

        return "redirect:/showProject";
    }


}
