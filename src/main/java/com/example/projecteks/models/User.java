package com.example.projecteks.models;

public class User {
    private String userName;
    private String password;
    String name;
    int id;

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
    public User() {
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
