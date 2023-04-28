package com.example.projecteks.reposetory;

import com.example.projecteks.models.Task;
import com.example.projecteks.utilities.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class Database implements DatabaseInterface {


    public ArrayList<Task> getTasks()throws RuntimeException{
        ArrayList<Task> list = new ArrayList<>();

        Connection con = ConnectionManager.getConnection();
        String SQLScript = "select * from Projectmanagement.task";

        try {
            ResultSet rs = con.createStatement().executeQuery(SQLScript);
            while (rs.next()){
                Task task = new Task();
                task.setId(rs.getInt("taskId"));
                task.setName(rs.getString("taskName"));
                task.setState(rs.getInt("taskState"));
                list.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void addTask(Task task){
        Connection con = ConnectionManager.getConnection();
        String SQLScript = "insert into Projectmanagement.task (taskName,taskState,creationDate,deadline) values(?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(SQLScript);
            ps.setString(1,task.getName());
            ps.setInt(2,task.getState());
            //Oprettelsesdatoer & deadlines
            ps.setDate(3, new java.sql.Date(new java.util.Date().getTime()));

            LocalDate deadline = LocalDate.parse(task.getDeadline());
            ps.setDate(4, java.sql.Date.valueOf(deadline));            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeTask(int taskId){
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


}
