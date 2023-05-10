package com.example.projecteks.reposetory;

import com.example.projecteks.models.Project;
import com.example.projecteks.models.Task;
import com.example.projecteks.models.User;

import java.util.ArrayList;

public interface DatabaseInterface {
    ArrayList<Task> getTasks(int projectId);


    void addTask(Task task);

    void removeTask(int taskId);

    void updateState(int taskId, int state);

    void editTask(int taskId, Task updatedTask);

    void addUserToProject(int userId, int projectId);

    Project getCertainProject(int projectId);

   /* void addProject(int userId, String projectName);*/

    int getProjectId(String projectName);

    void updateProjectName(int projectId, String projectName);
    void addUser(User user);
    ArrayList<User> getUser();
    User logIn(String userName, String password);

   void updateTask(Task task);

   ArrayList<Project> showProjects();

   void addProject(Project project);

   void deleteById(int projectID);
}

