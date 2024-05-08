package com.example.demo.Controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.User.AdminUser;

@RestController
@CrossOrigin("http://localhost:4200")
public class AdminLoginController {

	@Autowired
	SessionFactory sf;
	
	@PostMapping("admincheck")
	public boolean admincheck(@RequestBody AdminUser adminfrombrower ) {
		System.out.println("Admin from Brower : " + adminfrombrower );	
		Session s = sf.openSession();
		AdminUser adminfromdatabase=s.get(AdminUser.class, adminfrombrower.getUsername());
		System.out.println("Admin from databases :" + adminfromdatabase);
		
		boolean answer = adminfromdatabase.equals(adminfrombrower);
		
		return answer;
	}
}
