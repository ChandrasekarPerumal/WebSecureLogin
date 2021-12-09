package com.chan.securelogin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chan.securelogin.service.HashingPassword;
import com.chan.securelogin.service.SaveNewUserData;



@Controller
public class Securelogincontroller {
	
	
	@RequestMapping("userSignup")
	public String home()
	{
		return "userSignup";
	}
	
	@RequestMapping("userLogin")
	public String loginPage() 
	{
		return "userLogin";
	}
	
	@RequestMapping("userRegistration")
	public String newUserRegistration(@RequestParam("userName") String Name,@RequestParam("userEmailId") String EmailId,@RequestParam("userPassWord") String Password ) 
	{
			
		// Encrypt the password and store the data in DB;
		
		HashingPassword hash=new HashingPassword();
		String saltvalue=hash.getSaltvalue(30); // create salt value
		
		// Get Encrypted Password
		String securedpassword=hash.generateSecurePassword(Password, saltvalue);
		SaveNewUserData object2=new SaveNewUserData(Name, EmailId, securedpassword,saltvalue);
		object2.connectDB();
		
	return "registrationSuccess";
	}
	
	@RequestMapping("loginValidation")
	public String userValidate(@RequestParam("eid") String email,@RequestParam("pwd") String pwd)
	{
		
		boolean flag=false;
		SaveNewUserData sd= new SaveNewUserData();
		flag=sd.ValidateUser(email,pwd);
		
		if(flag)
			return "loginValidationSuccess";
		

	return "loginValidationUnsuccess";
	}
}
