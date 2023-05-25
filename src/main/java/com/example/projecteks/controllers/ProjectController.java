package com.example.projecteks.controllers;

import com.example.projecteks.models.Project;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class ProjectController {
    DatabaseInterface database = new Database();
    @GetMapping("showProject")
    private String showProjects(Model model) throws RuntimeException{
        model.addAttribute("pList", database.showUserProjects());
        return "Project/showProjects";
    }

    @GetMapping("addProject")
    public String addProject( Model model){
        model.addAttribute("project", new Project());
        return "Project/addProject";
    }
    @PostMapping("projectAdded")
    public String projectAdded(@ModelAttribute("project") Project project) throws RuntimeException{
        database.addProject(project);
        return "redirect:/showProject";
    }

    @PostMapping("/deleteProject/{projectID}")
    public String deleteProject (@PathVariable int projectID) throws RuntimeException{
        database.deleteAllTasksInProject(projectID);
        database.deleteById(projectID);
        return "redirect:/showProject";
    }

    @GetMapping("/updateProjectName/update/{projectId}")
    public String showUpdateProjectNameForm(@PathVariable("projectId") int projectId, Model model) throws RuntimeException {
        Project project = database.getCertainProject(projectId);
        model.addAttribute("project", project);


        return "Project/updateProjectName";
    }

    @PostMapping("/update/{projectId}")
    public String updateProjectName(@ModelAttribute("project") Project project, @PathVariable("projectId") int projectId, Model model) throws RuntimeException {
        database.updateProjectName(projectId, project.getProjectName(), project.getStartDate(), project.getDeadline());
        List<Project> pList = database.showProjects(); // Fetch the updated project list
        model.addAttribute("pList", pList); // Add the updated list to the model

        return "redirect:/showProject";
    }
    @ExceptionHandler(SQLException.class)
    public String handleError(Model model, Exception exception, HttpServletRequest request) {
        model.addAttribute("message",exception.getMessage());
        model.addAttribute("urlBack",request.getHeader("Referer"));
        return "exceptions/SQLExceptionPage";
    }

}
