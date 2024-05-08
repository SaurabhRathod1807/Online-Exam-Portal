package com.example.demo.User;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AdminUser {
	
	@Id
	public String username;
	public String password;
	public String email;
	public String mobno;
	
	
	public AdminUser() {
		super();
	}


	public AdminUser(String username, String password, String email, String mobno) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.mobno = mobno;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMobno() {
		return mobno;
	}


	public void setMobno(String mobno) {
		this.mobno = mobno;
	}


	@Override
	public int hashCode() {
		return Objects.hash(password, username);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdminUser other = (AdminUser) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}


	@Override
	public String toString() {
		return "AdminUser [username=" + username + ", password=" + password + ", email=" + email + ", mobno=" + mobno
				+ "]";
	}
	
	
}
