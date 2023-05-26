package com.example.projecteks.controllers;

import com.example.projecteks.models.Assign;
import com.example.projecteks.models.Task;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;
import com.example.projecteks.utilities.DateGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;



@Controller
public class AssignmentController {
    DatabaseInterface database = new Database();
    private  int daysFromNow =0;

    @GetMapping("/addAssignment/{projectId}/{taskId}")
    public String addAssignment(@PathVariable int projectId, @PathVariable int taskId, Model model) throws RuntimeException{
        Assign assignment = new Assign();
        model.addAttribute("taskId",taskId);
        model.addAttribute("projectId",projectId);
        model.addAttribute("assignment",assignment);
        model.addAttribute("userList", database.getUser());
        Task t = database.getTaskById(taskId);
        model.addAttribute("taskStart",t.getStartDate());
        model.addAttribute("taskDeadline",t.getDeadline());
        model.addAttribute("objectList", database.getAssignmentsByTaskId(taskId));
        model.addAttribute("dateDTOList", DateGenerator.getDateDTOList(daysFromNow));

        return "Assignment/addAssignment";
    }
    @PostMapping ("/assignmentAdded")
    public String addAssignment(@ModelAttribute("projectId") int projectId,@ModelAttribute("taskId") int taskId, @ModelAttribute("assignment") Assign assignment) throws RuntimeException{
        System.out.println("PostMapping: TaskId: "+taskId+", UserName: "+assignment.getUserName());
        database.addAssignment(taskId,assignment.getUserName(),assignment.getStartDate(),assignment.getEndDate());

        return "redirect:/addAssignment/"+projectId+"/"+taskId;
    }
    @GetMapping("/myAssignment")
    public String getAssignmentByUserId(Model model) throws RuntimeException{
        model.addAttribute("list", database.getAssignedTasks());
        return "Assignment/myAssignment";
    }
    /*@GetMapping("/getAssigmentByTaskId/{projectId}")
    public String getAssignmentByTaskId(@PathVariable int projectId){
        database.getAssignmentsByUserName();
        return "redirect:/showTask/"+projectId;
    }*/
    @GetMapping("updateStateAssignment/{taskId}/{state}")
    private String updateTask(@PathVariable int taskId, @PathVariable int state) throws RuntimeException {
        database.updateState(taskId, state);
        return "redirect:/myAssignment";
    }
    @GetMapping("/assignDate/increment/{projectId}/{taskId}")
    public String increment(@PathVariable int projectId, @PathVariable int taskId){
        daysFromNow += 30;
        return "redirect:/addAssignment/"+projectId+"/"+taskId;
    }
    @GetMapping("/assignDate/now/{projectId}/{taskId}")
    public String now(@PathVariable int projectId, @PathVariable int taskId){
        daysFromNow = 0;
        return "redirect:/addAssignment/"+projectId+"/"+taskId;
    }


    @GetMapping("/assignDate/decrement/{projectId}/{taskId}")
    public String decrement(@PathVariable int projectId, @PathVariable int taskId){
        daysFromNow -= 30;
        return "redirect:/addAssignment/"+projectId+"/"+taskId;
    }


    @ExceptionHandler(SQLException.class)
    public String handleError(Model model, Exception exception, HttpServletRequest request) {
        model.addAttribute("message",exception.getMessage());
        model.addAttribute("urlBack",request.getHeader("Referer"));
        return "exceptions/SQLExceptionPage";
    }


}
