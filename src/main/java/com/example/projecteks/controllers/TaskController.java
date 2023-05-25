package com.example.projecteks.controllers;

import com.example.projecteks.models.Project;
import com.example.projecteks.models.Task;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;

@Controller
@RequestMapping
public class TaskController {
    DatabaseInterface database = new Database();

    @GetMapping("showTask/{projectId}")
    private String showTasks(Model model,@PathVariable int projectId) throws RuntimeException{
        ArrayList<Task> taskList = database.getTasks(projectId);
        model.addAttribute("list", taskList);
        model.addAttribute("projectId",projectId);
        System.out.println("test "+taskList);
        ArrayList<ArrayList<String>> dto = new ArrayList<>(); //TODO gør til en metode
        for(Task t : taskList){
            dto.add(database.getUserNameByTaskId(t.getId()));
        }
        //Test start
        for(ArrayList<String> l : dto){  //TODO GØR TIL EN LOG
            System.out.println("-------------");
            for(String n : l){
                System.out.println("UserName: "+n);
            }
        }
        //Test end
        model.addAttribute("nameDTO",dto);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("userName",userName);
        return "Task/showTasks";
    }

    @GetMapping("addTask/{projectId}")
    private String addTask(Model model, @PathVariable Integer projectId) throws RuntimeException{
        model.addAttribute("task", new Task());
        model.addAttribute("projectId", projectId);
        Project p = database.getCertainProject(projectId);
        model.addAttribute("projectStart", p.getStartDate());
        model.addAttribute("projectDeadline", p.getDeadline());

        return "Task/addTask";
    }

    @PostMapping("taskAdded")
    private String taskAdded(@ModelAttribute("task") Task task, @ModelAttribute("projectId") int projectId) throws RuntimeException{
        task.setProjectId(projectId);
        database.addTask(task);
        //database.addAssignment(taskId,userId);
        return "redirect:/showTask/"+ projectId;
    }

    @GetMapping("removeTask/{projectId}/{taskId}")
    private String removeTask(@PathVariable String projectId,@PathVariable int taskId) throws RuntimeException{
        database.removeAllAssignmentsFromTask(taskId);
        database.removeTask(taskId);
        return "redirect:/showTask/"+ projectId;
    }

    @GetMapping("updateState/{projectId}/{taskId}/{state}")
    private String updateTask(@PathVariable int projectId,@PathVariable int taskId, @PathVariable int state) throws RuntimeException {
        database.updateState(taskId, state);
        return "redirect:/showTask/"+ projectId;
    }

    @GetMapping("updateStar/{projectId}/{taskId}/{star}")
    private String updateStar(@PathVariable int projectId,@PathVariable int taskId, @PathVariable int star) throws RuntimeException {
        database.updateStar(taskId, star);
        return "redirect:/showTask/"+ projectId;
    }


    @GetMapping("editTask/{taskId}/{projectId}")
    private String editTask(@PathVariable int taskId, Model model, @PathVariable String projectId) throws RuntimeException{
        model.addAttribute("task",database.getTaskById(taskId));
        model.addAttribute("newTask", new Task());
        model.addAttribute("projectId", projectId);

        return "Task/editTask";
    }

    @PostMapping("editTask/")
    private String editTask(@ModelAttribute("newTask") Task newTask,@ModelAttribute("projectId") int projectId,@ModelAttribute("id") int taskId) throws RuntimeException {
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
    @ExceptionHandler(SQLException.class)
    public String handleError(Model model, Exception exception, HttpServletRequest request) {
        model.addAttribute("message",exception.getMessage());
        model.addAttribute("urlBack",request.getHeader("Referer"));
        return "exceptions/SQLExceptionPage";
    }



}




