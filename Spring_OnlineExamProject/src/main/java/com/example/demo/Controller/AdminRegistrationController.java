package com.example.demo.Controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.User.AdminUser;



@RestController
@CrossOrigin("http://localhost:4200")
public class AdminRegistrationController {
	
	@Autowired
	SessionFactory sf;
	
	@PostMapping("adminsave")
	public void adminsave(@RequestBody AdminUser admin) {
		
		Session s = sf.openSession();
		 Transaction t = s.beginTransaction();
		 s.persist(admin);
		 t.commit();
	}
}
