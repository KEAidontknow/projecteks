package com.example.projecteks.models;

public class User {
    private int user_id;
    private String username;
    private String password;
    private String role;
    private int enabled;


    public User(int user_id, String username){
        this.user_id = user_id;
        this.username = username;
    }
    public User(){

    }
    public int getEnabled(){
        return enabled;
    }
    public void setEnabled(){
        this.enabled = enabled;
    }
    public String getRole(){
        return role;
    }
    public void setRole(){
        this.role = role;
    }
    public int getUser_id(){
        return user_id;
    }
    public void setUser_id(int user_id){
        this.user_id = user_id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
