package com.example.projecteks.reposetory;

import com.example.projecteks.models.Task;
import com.example.projecteks.utilities.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
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


}
