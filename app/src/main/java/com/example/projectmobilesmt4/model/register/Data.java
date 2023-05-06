package com.example.projectmobilesmt4.model.register;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

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