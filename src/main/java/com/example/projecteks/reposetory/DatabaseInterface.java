package com.example.projecteks.reposetory;

import com.example.projecteks.models.Project;
import com.example.projecteks.models.Task;
import com.example.projecteks.models.User;

import java.util.ArrayList;

public interface DatabaseInterface {
    ArrayList<Task> getTasks();


    void addTask(Task task);

    void removeTask(int taskId);

    void updateState(int taskId, int state);

    void editTask(int taskId, Task updatedTask);
    void addUser(User user);
}
/*
    void updateTask(Task task);

    //void addProject(Project project);

    void deleteProject(int id);

    //void deleteById(int projectID);
}

 */
