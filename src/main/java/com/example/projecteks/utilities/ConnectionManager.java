package com.example.projecteks.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    public static Connection con = null;

    public static Connection getConnection(){

        /*String url = "jdbc:mysql://eksamen.mysql.database.azure.com";
        String username = "user";
        String password = "!test1234";*/

        String url = "jdbc:mysql://localhost:3306/Projectmanagement";
        String username = "testuser";
        String password = "Sivertsen13";

        if(con == null) {
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return con;

    }
}
