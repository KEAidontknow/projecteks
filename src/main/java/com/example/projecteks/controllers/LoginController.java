package com.example.projecteks.controllers;

import com.example.projecteks.models.User;
import com.example.projecteks.reposetory.Database;
import com.example.projecteks.reposetory.DatabaseInterface;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    DatabaseInterface database = new Database();
    @GetMapping("showUser")
    private String showTasks(Model model){
        model.addAttribute("userList", database.getUser());

        return "/users/showUser";
    }
    @GetMapping("/createUser")
    private String createUser(Model model){
        model.addAttribute("user", new User());
        return "/users/createUser";
    }
    @PostMapping("/createUser")
    private String userCreated(@ModelAttribute User user){
        database.addUser(user);
        return "redirect:/showUser";
    }
    @GetMapping("/login")
    public String isConnected(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("user", new User());
            return "users/login";
        } else {
            return "redirect:showProject/" + user;
        }
    }
    @PostMapping("/login")
    public String signIn(HttpSession session, @ModelAttribute("user") User user) {
        try {
            User login = database.logIn(user.getUserName(), user.getPassword());
            if (login != null) {
                session.setAttribute("user", login);
                session.setMaxInactiveInterval(5);

                return "redirect:showProject/" + login;
            } else {
                return "users/login";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
