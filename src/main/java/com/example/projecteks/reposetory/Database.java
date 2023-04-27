package com.example.projecteks.reposetory;

import com.example.projecteks.models.Task;
import com.example.projecteks.utilities.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class Database implements DatabaseInterface {


    public ArrayList<Task> getTasks()throws RuntimeException{
        ArrayList<Task> list = new ArrayList<>();

        Connection con = ConnectionManager.getConnection();
        String SQLCommand = "select * from Projectmanagement.task";

        try {
            ResultSet rs = con.createStatement().executeQuery(SQLCommand);
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
        String SQLCommand = "insert into Projectmanagement.task (taskName,taskState) values(?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(SQLCommand);
            ps.setString(1,task.getName());
            ps.setInt(2,task.getState());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
