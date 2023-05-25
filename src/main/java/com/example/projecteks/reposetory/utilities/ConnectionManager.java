package com.example.projecteks.reposetory.utilities;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static Connection connection = null;
    public static String url = "jdbc:mysql://eksamen.mysql.database.azure.com/Projectmanagement";
    public static String username = "user";
    public static String password = "!test1234";

/*
TODO:injecting datasource into static variable result in null

    @Value("${spring.datasource.url}")
    public void setUrl(String url) {
        ConnectionManager.url = url;
    }

    @Value("${spring.datasource.username}")
    public void setUsername(String username) {
        ConnectionManager.username = username;
    }

    @Value("${spring.datasource.password}")
    public void setPassword(String password) {
        ConnectionManager.password = password;
    }
*/

    public static Connection getConnection() {
        if (connection != null) return connection;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return connection;
    }
}