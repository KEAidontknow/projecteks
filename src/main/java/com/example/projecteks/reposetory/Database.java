package com.example.projecteks.reposetory;

import com.example.projecteks.models.Assign;
import com.example.projecteks.models.Project;
import com.example.projecteks.models.Task;
import com.example.projecteks.models.User;
import com.example.projecteks.reposetory.utilities.ConnectionManager;
import com.example.projecteks.utilities.TimeCalc;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

@Repository
public class Database implements DatabaseInterface {

    public void updateStar(int taskId, int star) {
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "UPDATE Projectmanagement.task SET taskStar=? WHERE taskId=?";

        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setInt(1, star == 1 ? 2 : 1); // skifter mellem 1 og 2
            ps.setInt(2, taskId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public ArrayList<Task> getTasks(int projectId) throws RuntimeException {  //UNITEST
        ArrayList<Task> list = new ArrayList<>();

        Connection con = ConnectionManager.getConnection();
        String SQLScript = "select * from Projectmanagement.task where projectId = ? ORDER BY taskStar DESC";

        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setInt(1,projectId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("taskId"));
                task.setName(rs.getString("taskName"));
                task.setState(rs.getInt("taskState"));
                task.setTimeEstimate(rs.getInt("timeEstimate"));
                task.setCreationDate(rs.getString("creationDate"));
                task.setDeadline(LocalDate.parse(rs.getString("deadline")));
                task.setStartDate(LocalDate.parse(rs.getString("startDate")));
                task.setTimeEstimate(rs.getInt("timeEstimate"));
                task.setHoursOfPeriod();
                task.setEmpNeeded();
                task.setStar(rs.getInt("taskStar"));

                list.add(task);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        Collections.sort(list, (t1, t2) -> {
            if (t1.getStar() == 2 && t2.getStar() != 2) {
                return -1;
            } else if (t1.getStar() != 2 && t2.getStar() == 2) {
                return 1;
            } else {
                return 0;
            }
        });

        return list;
    }

    public Task getTaskById(int taskId) throws RuntimeException {


        Connection con = ConnectionManager.getConnection();
        String SQLScript = "select * from Projectmanagement.task where taskId = ?";
        Task task = new Task();
        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setInt(1,taskId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                task.setId(rs.getInt("taskId"));
                task.setName(rs.getString("taskName"));
                task.setState(rs.getInt("taskState"));
                task.setTimeEstimate(rs.getInt("timeEstimate"));
                task.setCreationDate(rs.getString("creationDate"));
                task.setDeadline(LocalDate.parse(rs.getString("deadline")));
                task.setStartDate(LocalDate.parse(rs.getString("startDate")));
                //task.setHoursOfPeriod();
                task.setTimeEstimate(rs.getInt("timeEstimate"));
                task.setStar(rs.getInt("taskStar"));
                task.setProjectId(rs.getInt("projectId"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return task;
    }

    public void addTask(Task task) {  //UNITEST
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "insert into Projectmanagement.task (taskName,taskState,creationDate,startdate,deadline,timeEstimate,taskStar,projectId) values(?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setString(1, task.getName());
            ps.setInt(2, task.getState());
            //Oprettelsesdatoer & deadlines
            LocalDateTime now = LocalDateTime.now(); // skaffer current date og time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // formattet
            String formattedDate = now.format(formatter); // Stringen
            task.setCreationDate(formattedDate); // sætter den som CreationDate
            ps.setString(3, task.getCreationDate());
            ps.setString(4, task.getStartDate().toString());
            ps.setString(5, task.getDeadline().toString());
            ps.setInt(6, task.getTimeEstimate());
            ps.setInt(7, task.getStar());
            ps.setInt(8, task.getProjectId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    // Et stykke HTML kode som du skal ignorrere   <input type="hidden" id="creationDate" name="creationDate" th:field="*{creationDate}" value="${#dates.format(#dates.createNow(), 'yyyy-MM-dd HH:mm:ss')}" />

    public void removeTask(int taskId) {   //UNITEST
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "delete from Projectmanagement.task where taskId = ?";
        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setInt(1, taskId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteAllTasksInProject(int projectId) {
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "delete from Projectmanagement.task where projectId = ?";
        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setInt(1, projectId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateState(int taskId, int state) {  //
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "update Projectmanagement.task set taskState=? where taskId=?";

        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setInt(2, taskId);
            switch (state) {
                case (1) -> ps.setInt(1, 2);
                case (2) -> ps.setInt(1, 3);
                case (3) -> ps.setInt(1, 1);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //Jeg har valgt at pille Status fra da den er til at ændre altid som vi snakkede om
    public void editTask(Task updatedTask) {
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "update Projectmanagement.task set taskName=?, timeEstimate=?, startDate=?, deadline=? where taskId=?";

        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setString(1, updatedTask.getName());
            ps.setInt(2, updatedTask.getTimeEstimate());
            ps.setString(3, updatedTask.getStartDate().toString());
            ps.setString(4, updatedTask.getDeadline().toString());
            ps.setInt(5,updatedTask.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTask(Task task) {
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "update Projectmanagement.task set taskName=?, taskState=? where taskId=?";

        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setString(1, task.getName());
            ps.setInt(2, task.getState());
            ps.setInt(3, task.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //----------------------------------<[[[ PROJECT METHODS ]]]>-------------------------------------


    public void addUserToProject(int userId, int projectId) { // todo: Denne metode skal ikke bruges, med mindre man gerne vil overdrage de administrative rettigheder til en anden bruger
        Connection con = ConnectionManager.getConnection();

        try {
            String SQLScript = "INSERT INTO userproject (userId, projectId) VALUES (?,?)";
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setInt(1, userId);
            ps.setInt(2, projectId);
            ps.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public Project getCertainProject(int projectId) { //UNITEST
        try {
            Connection con = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM Projectmanagement.project WHERE projectId = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, projectId);
            ResultSet rs = ps.executeQuery();
            Project project = new Project();
            while (rs.next()) {
                project.setProjectId(rs.getInt("projectId"));
                project.setProjectName(rs.getString("projectName"));
                project.setDeadline(rs.getString("deadline"));
                project.setStartDate(rs.getString("startDate"));
            }

            return project;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


   /* @Override
    public void addProject(int userId, String projectName) {
        try {
            String SQL = "INSERT INTO project (projectName) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, projectName);
            ps.executeQuery();
            int projectId = getProjectId(projectName);
            addUserToProject(userId, projectId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }*/


    public int getProjectId(String projectName) { // UNITEST
        Connection con = ConnectionManager.getConnection();
        try {
            Project project = new Project();
            String SQL = "SELECT projectId FROM Projectmanagement.project WHERE projectName = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, projectName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                project.setProjectId(rs.getInt("projectId"));
            }
            return project.getProjectId();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void updateProjectName(int projectId, String projectName, String startDate, String deadline) {
        Connection con = ConnectionManager.getConnection();
        try {
            String SQL = "UPDATE Projectmanagement.project SET projectName = ?, startDate = ?, deadline = ? WHERE projectId = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, projectName);
            ps.setString(2, startDate);
            ps.setString(3, deadline);
            ps.setInt(4, projectId); // Corrected index to 4
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public ArrayList<Project> showProjects() throws RuntimeException {
        ArrayList<Project> pList = new ArrayList<>();

        Connection con = ConnectionManager.getConnection();
        String SQLScript = "select * from Projectmanagement.project";

        try {
            ResultSet rs = con.createStatement().executeQuery(SQLScript);
            while (rs.next()) {
                Project project = new Project();
                int projectId = rs.getInt("projectId");
                project.setProjectId(projectId);
                project.setProjectName(rs.getString("projectName"));
                project.setStartDate(rs.getString("startDate"));
                project.setDeadline(rs.getString("deadline"));
                project.setHoursOfTasks(TimeCalc.hoursOfTaskInProject(getTasks(projectId))); //TODO Måske spørge directe med SQL om summen af timer
                pList.add(project);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pList;
    }
public ArrayList<Project> showUserProjects() throws RuntimeException {
        ArrayList<Project> pList = new ArrayList<>();

        Connection con = ConnectionManager.getConnection();
        String SQLScript = "select * from Projectmanagement.project where username = ?";

        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setString(1, SecurityContextHolder.getContext().getAuthentication().getName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Project project = new Project();
                int projectId = rs.getInt("projectId");
                project.setProjectId(projectId);
                project.setProjectName(rs.getString("projectName"));
                project.setStartDate(rs.getString("startDate"));
                project.setDeadline(rs.getString("deadline"));
                project.setHoursOfTasks(TimeCalc.hoursOfTaskInProject(getTasks(projectId))); //TODO Måske spørge directe med SQL om summen af timer
                pList.add(project);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pList;
    }

    public void addProject(Project project) { //UNITEST
        Connection con = ConnectionManager.getConnection();
        // String SQLScript="insert into project_DB.project(name, id) valued=(?,?) ";
        String SQL = "INSERT INTO Projectmanagement.project (projectName,startDate,deadline,username) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(SQL);

            ps.setString(1, project.getProjectName());
            ps.setString(2, project.getStartDate());
            ps.setString(3, project.getDeadline());
            ps.setString(4, SecurityContextHolder.getContext().getAuthentication().getName());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    public void deleteById(int projectId) {
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "delete from Projectmanagement.project where projectId=?";
        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setInt(1, projectId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }




//---------------------------------------<[[[ USER METHODS ]]]>-------------------------------------

    public ArrayList<User> getUser() throws RuntimeException {
        ArrayList<User> userList = new ArrayList<>();

        Connection con = ConnectionManager.getConnection();
        String SQLScript = "select * from Projectmanagement.users";

        try {
            ResultSet rs = con.createStatement().executeQuery(SQLScript);
            while (rs.next()) {
                User user = new User();

                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public User getUserByUserName(String userName) {
        User user = new User();

        Connection con = ConnectionManager.getConnection();
        String SQLScript = "select * from Projectmanagement.users where username = ?";

        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setString(1,userName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user.setUsername(rs.getString("username"));
                //user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }



    public void addUser(User user) {
        Connection con = ConnectionManager.getConnection();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String SQLScript = "insert into Projectmanagement.users (userName, password, role, enabled) values(?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setString(1, user.getUsername());
            ps.setString(2, passwordEncoder.encode(user.getPassword()));
            ps.setString(3, "USER");
            ps.setInt(4, 1);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //___________________ASSIGNMENTS_______________________________________________________

    public ArrayList<Assign> getAssignmentsByTaskId(int taskId) throws RuntimeException {


        Connection con = ConnectionManager.getConnection();
        String SQLScript = "select * from Projectmanagement.assign where taskId = ?";
        ArrayList<Assign> assignmentList = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setInt(1,taskId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Assign assignment = new Assign();
                assignment.setAssignId(rs.getInt("assignId"));
                assignment.setTaskId(rs.getInt("taskId"));
                assignment.setUserName(rs.getString("username"));
                assignment.setStartDate(LocalDate.parse(rs.getString("startDate")));
                assignment.setEndDate(LocalDate.parse(rs.getString("endDate")));
                assignmentList.add(assignment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return assignmentList;
    }

    public ArrayList<Assign> getAssignmentsByUserName(String userName) throws RuntimeException {


        Connection con = ConnectionManager.getConnection();
        String SQLScript = "select * from Projectmanagement.assign where username = ?";
        ArrayList<Assign> assignmentList = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setString(1,userName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Assign assignment = new Assign();
                assignment.setAssignId(rs.getInt("assignId"));
                assignment.setTaskId(rs.getInt("taskId"));
                assignment.setUserName(rs.getString("username"));
                assignment.setStartDate(LocalDate.parse(rs.getString("startDate")));
                assignment.setEndDate(LocalDate.parse(rs.getString("endDate")));
                assignmentList.add(assignment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return assignmentList;
    }

    public void addAssignment(int taskId, String userName, LocalDate startDate, LocalDate endDate) throws RuntimeException {  //UNITEST
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "insert into Projectmanagement.assign (taskId,username,startDate,endDate) values(?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setInt(1,taskId);
            ps.setString(2,userName);
            ps.setString(3,startDate.toString());
            ps.setString(4,endDate.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public ArrayList<String> getUserNameByTaskId(int taskId){
        ArrayList<String> nameList = new ArrayList<>();
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "select username from Projectmanagement.assign where taskId = ?";
        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setInt(1,taskId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name;
                name = rs.getString("username");
                nameList.add(name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       return  nameList;
    }


    public ArrayList<Task> getAssignedTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "select * from Projectmanagement.task where taskId in (select taskId from Projectmanagement.assign where username = ?)";
        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setString(1, SecurityContextHolder.getContext().getAuthentication().getName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("taskId"));
                task.setProjectId(rs.getInt("projectId"));
                task.setState(rs.getInt("taskState"));
                task.setName(rs.getString("taskName"));
                task.setDeadline(LocalDate.parse(rs.getString("deadline")));
                task.setStartDate(LocalDate.parse(rs.getString("startDate")));
                taskList.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return taskList;
    }

    public void removeAllAssignmentsFromTask(int taskId){

        Connection con = ConnectionManager.getConnection();
        String SQLScript = "delete from Projectmanagement.assign where taskId = ?";
        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setInt(1, taskId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
