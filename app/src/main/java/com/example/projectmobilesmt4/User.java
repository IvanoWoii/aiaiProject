package com.example.projectmobilesmt4;

public class User {
    private int id;
    private String username,email,password;

    public User(int id, String username, String email){
        this.id=id;
        this.username=username;
        this.email=email;
        this.password=password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
