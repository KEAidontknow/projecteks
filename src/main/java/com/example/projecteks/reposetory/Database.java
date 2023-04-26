package com.example.projecteks.reposetory;

import com.example.projecteks.models.Task;
import com.example.projecteks.utilities.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class Database implements DatabaseInterface {


    public ArrayList<Task> getTasks()throws RuntimeException{
        ArrayList<Task> list = new ArrayList<>();
        try{
            Connection con = ConnectionManager.getConnection();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }



        return list;
    }


}
