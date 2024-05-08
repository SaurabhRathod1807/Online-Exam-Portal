package com.example.demo.Controller;

import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.User.PasswordReset;
import com.example.demo.User.User;

@RestController
@CrossOrigin("http://localhost:4200")
public class RegistrationController {
	@Autowired
	SessionFactory sf;

	@PostMapping("saveDB")
	public void saveDB(@RequestBody User user) {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		s.persist(user);
		t.commit();
	}
	
	@GetMapping("getalluser")
	public List<User> getalluser(){
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
				return	s.createQuery("from User").list();
	}
	
	@PostMapping("saveDB2")
	public void saveDB2(@RequestBody PasswordReset user) {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		s.persist(user);
		t.commit();
	}
	
	@GetMapping("updatepassword/{username}/{password}")
	public boolean updatepassword(@PathVariable String username, @PathVariable String password) {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		Query query = s.createQuery("update User set password=:password where username=:username");
		query.setParameter("password", password);
		query.setParameter("username", username);
		query.executeUpdate();
		t.commit();
		
		return true;
	}
	
	@PostMapping("checking")
	public boolean checking(@RequestBody PasswordReset user) {
		
		Session s = sf.openSession();
		PasswordReset obj = s.get(PasswordReset.class, user.getUsername());
		
		if(user.getPasswordanswer().equals(obj.getPasswordanswer())) {
			return true;
		}
		else {
			return false;
		}
	}
}
