package com.chan.securelogin.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserRegistration {
	
	
	private String User_name;
	
	@Id
	private String User_email; //Primary key 
	private String User_password;
	private String saltValue;
	
	public UserRegistration() {
		
	}
	
	public UserRegistration(String name, String eid, String password,String salt) 
	{
		
		this.User_name = name;
		this.User_email = eid;
		this.User_password = password;
		this.saltValue=salt;
	}

	public String getEid() 
	{
		return User_email;
	}

	public void setEid(String eid)
	 {
		this.User_email = eid;
	}

	public String getPassword() 
	{
		return User_password;
	}

	public void setPassword(String password) {
		this.User_password = password;
	}

	public String getName() {
		return User_name;
	}

	public void setName(String name) {
		this.User_name = name;
	}

	public String getSalt() {
		return saltValue;
	}

	public void setSalt(String salt) {
		this.saltValue = salt;
	}

	@Override
	public String toString() {
		return "userdata [name=" + User_name + ", eid=" + User_email + ", password=" + User_password + ", salt=" + saltValue + "]";
	} 
	
	
	
	
	
	
}
