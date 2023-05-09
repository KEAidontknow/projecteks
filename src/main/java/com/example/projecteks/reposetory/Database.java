package com.example.projecteks.reposetory;

import com.example.projecteks.models.Project;
import com.example.projecteks.models.Task;
import com.example.projecteks.utilities.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.example.projecteks.utilities.ConnectionManager.con;

@Repository
public class Database implements DatabaseInterface {


    public ArrayList<Task> getTasks() throws RuntimeException {  //UNITEST
        ArrayList<Task> list = new ArrayList<>();

        Connection con = ConnectionManager.getConnection();
        String SQLScript = "select * from Projectmanagement.task";

        try {
            ResultSet rs = con.createStatement().executeQuery(SQLScript);
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("taskId"));
                task.setName(rs.getString("taskName"));
                task.setState(rs.getInt("taskState"));
                task.setTimeEstimate(rs.getInt("timeEstimate"));
                task.setCreationDate(rs.getString("creationDate"));
                task.setDeadline(LocalDate.parse(rs.getString("deadline")));
                task.setStartDate(LocalDate.parse(rs.getString("startDate")));
                task.setHoursOfPeriod();
                list.add(task);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void addTask(Task task){  //UNITEST
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "insert into Projectmanagement.task (taskName,taskState,creationDate,startdate,deadline,timeEstimate) values(?,?,?,?,?,?)";

        try {


            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setString(1,task.getName());
            ps.setInt(2,task.getState());
            //Oprettelsesdatoer & deadlines
            LocalDateTime now = LocalDateTime.now(); // skaffer current date og time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // formattet
            String formattedDate = now.format(formatter); // Stringen
            task.setCreationDate(formattedDate); // sætter den som CreationDate
            ps.setString(3, task.getCreationDate());
            ps.setString(4, task.getStartDate().toString());
            ps.setString(5,task.getDeadline().toString() );
            ps.setInt(6,task.getTimeEstimate());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    // Et stykke HTML kode som du skal ignorrere   <input type="hidden" id="creationDate" name="creationDate" th:field="*{creationDate}" value="${#dates.format(#dates.createNow(), 'yyyy-MM-dd HH:mm:ss')}" />

    public void removeTask(int taskId){   //UNITEST
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "delete from Projectmanagement.task where taskId = ?";
        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setInt(1,taskId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateState(int taskId, int state){  //
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "update Projectmanagement.task set taskState=? where taskId=?";

        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setInt(2,taskId);
            switch (state) {
                case(1)->ps.setInt(1, 2);
                case(2)->ps.setInt(1, 3);
                case(3)->ps.setInt(1, 1);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
//Jeg har valgt at pille Status fra da den er til at ændre altid som vi snakkede om
    public void editTask(int taskId, Task updatedTask) {
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "update Projectmanagement.task set taskName=?, timeEstimate=?, startDate=?, deadline=? where taskId=?";

        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setString(1, updatedTask.getName());
            ps.setInt(2, updatedTask.getTimeEstimate());
            ps.setString(3, updatedTask.getStartDate().toString());
            ps.setString(4, updatedTask.getDeadline().toString());
            ps.setInt(5, taskId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTask(Task task){
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "update Projectmanagement.task set taskName=?, taskState=? where taskId=?";

        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setString(1,task.getName());
            ps.setInt(2,task.getState());
            ps.setInt(3,task.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //----------------------------------<[[[ PROJECT METHODS ]]]>-------------------------------------

    @Override
    public void addUserToProject(int userId, int projectId) {
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

    @Override
    public Project getCertainProject(int projectId) {
        try {
            String SQL = "SELECT projectName, projectId FROM project WHERE projectId = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, projectId);
            ResultSet rs = ps.executeQuery();
            Project project = null;
            while (rs.next()) {
                String projectName = rs.getString("projectName");
                projectId = rs.getInt("projectId");
                project = new Project(projectId, projectName);
            }
            return project;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
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
    }

    @Override
    public int getProjectId(String projectname) {
        try {
            int projectId = 0;
            String SQL = "SELECT projectId FROM project WHERE projectName = ?";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, projectId);
            ResultSet rs = ps.executeQuery();
            projectId = rs.getInt("projectId");
            return projectId;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}


