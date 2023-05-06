package com.example.projectmobilesmt4.model.login;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("user_id")
	private int userId;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}