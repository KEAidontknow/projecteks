package com.example.projecteks.reposetory;

import com.example.projecteks.models.Project;
import com.example.projecteks.models.Task;

import java.util.ArrayList;

public interface DatabaseInterface {
    ArrayList<Task> getTasks();


    void addTask(Task task);

    void removeTask(int taskId);

    void updateState(int taskId, int state);

    void editTask(int taskId, Task updatedTask);

    void addUserToProject(int userId, int projectId);

    Project getCertainProject(int projectId);

    void addProject(int userId, String projectName);

    int getProjectId(String projectname);
}
/*
    void updateTask(Task task);

    //void addProject(Project project);

    //void deleteById(int projectID);
}

 */
