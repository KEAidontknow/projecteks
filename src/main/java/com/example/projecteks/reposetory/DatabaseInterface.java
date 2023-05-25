package com.example.projecteks.reposetory;

import com.example.projecteks.models.Assign;
import com.example.projecteks.models.Project;
import com.example.projecteks.models.Task;
import com.example.projecteks.models.User;

import java.util.ArrayList;

public interface DatabaseInterface {
    ArrayList<Task> getTasks(int projectId);
    Task getTaskById(int taskId);
    void addTask(Task task);

    void removeTask(int taskId);

    void updateState(int taskId, int state);

    void editTask(Task updatedTask);

    void addUserToProject(int userId, int projectId);

    Project getCertainProject(int projectId);

   /* void addProject(int userId, String projectName);*/

    int getProjectId(String projectName);

    void updateProjectName(int projectId, String projectName, String startDate, String deadline);
    void addUser(User user);
    ArrayList<User> getUser();
    User getUserByUserName(String userName);

   void updateTask(Task task);

   ArrayList<Project> showProjects();
   ArrayList<Project> showUserProjects();

   void addProject(Project project);

   void deleteById(int projectID);

    void deleteAllTasksInProject(int projectID);

    //_________ASSIGNMENT:____________
    ArrayList<Assign> getAssignmentsByTaskId(int taskId);
    ArrayList<Assign> getAssignmentsByUserName(String userName);
    void addAssignment(int taskId, String userName);


    ArrayList<String> getUserNameByTaskId(int taskId);

    void updateStar(int taskId, int star);
    ArrayList<Task> getAssignedTasks();

    void removeAllAssignmentsFromTask(int taskId);


}

