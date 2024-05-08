package com.example.demo.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PasswordReset {
	
	@Id
	public String username;
	public String passwordquestion;
	public String passwordanswer;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswordquestion() {
		return passwordquestion;
	}
	public void setPasswordquestion(String passwordquestion) {
		this.passwordquestion = passwordquestion;
	}
	public String getPasswordanswer() {
		return passwordanswer;
	}
	public void setPasswordanswer(String passwordanswer) {
		this.passwordanswer = passwordanswer;
	}
	@Override
	public String toString() {
		return "PasswordReset [username=" + username + ", passwordquestion=" + passwordquestion + ", passwordanswer="
				+ passwordanswer + "]";
	}
	
	
}
