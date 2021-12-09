package com.chan.securelogin.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.chan.securelogin.model.UserRegistration;

public class SaveNewUserData {

	//Store Data to DB
	//Connection to DB
	private String userName;
	private String userEmailId;
	private String userPassword;
	private String saltValue;
	
	public SaveNewUserData() {
		
	}
	
	public SaveNewUserData(String name,String emailId,String password,String saltValue){
	
		this.userName=name;
		this.userEmailId=emailId;
		this.userPassword=password;
		this.saltValue=saltValue;
	}
	
	
	// Connect Database and Store User Data
	public void connectDB() 
	{
	
		UserRegistration obj1=new UserRegistration(userName,userEmailId,userPassword,saltValue);
		Configuration config;
		SessionFactory sessfac;
		Transaction transact;
		Session sess;
		
		try {
			config=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(UserRegistration.class);
			sessfac=config.buildSessionFactory();
			sess=sessfac.openSession();
			transact=sess.beginTransaction();
			sess.save(obj1); // Stores data to Database.
			transact.commit();
			sess.close();
		}
		catch(Exception exception) {
			System.err.print(exception);
		}
	}
	
	
	public boolean ValidateUser(String EmailId ,String OldPassword )
	{
		boolean flag=false;
		
		UserRegistration user=new UserRegistration();	
		HashingPassword hash=new HashingPassword();
		Session sess;
		Transaction transact;
		
		String newEncryptedPassword=null;
		String oldEncryptedPassword=null;
		
		try {
			Configuration config=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(UserRegistration.class);
			SessionFactory sessfac=config.buildSessionFactory();
			sess=sessfac.openSession();
			transact=sess.beginTransaction();
			
			//Retrieve the data from DB based on EmailId 
			user=sess.get(UserRegistration.class,EmailId);
			//Password Stored during registration
			oldEncryptedPassword=user.getPassword();
			//Salt value created during registration 
			String storedSaltValue=user.getSalt();
			
			/*
			 * Generate Encrypted value by using salt value of EmailId already stored in DB with that current password and  
			Check whether the password matches or not.
			*/ 
			newEncryptedPassword=hash.generateSecurePassword(OldPassword,storedSaltValue);
			flag=newEncryptedPassword.equalsIgnoreCase(oldEncryptedPassword);
			
			//Commit and destroy the Session
			transact.commit();
			sess.close();	
		}
		catch(Exception exceptions) {
			System.err.println(exceptions);
		}
		if(flag)
			return flag;
		
		
		return flag;
	}
	
}
