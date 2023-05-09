package com.example.projectmobilesmt4.model;

public class DataJson {

     private String username,email,password;

     public void ProfileData(String username, String email, String password){
         this.username = username;
         this.email = email;
         this.password= password;
     }

     public String getUsername(){
         return username;
     }

     public String getEmail(){
         return email;
     }

     public String getPassword(){
         return password;
     }
}
