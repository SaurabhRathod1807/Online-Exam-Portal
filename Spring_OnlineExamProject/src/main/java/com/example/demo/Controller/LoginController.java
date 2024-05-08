package com.example.demo.Controller;

import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.User.Answer;
import com.example.demo.User.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin("http://localhost:4200")
public class LoginController {
	@Autowired
	SessionFactory sf;
	static HttpSession httpSession;

	@PostMapping("check")
	public boolean check(@RequestBody User userformbrower, HttpServletRequest request) {
		System.out.println("User from the brower is : " + userformbrower);
		Session s = sf.openSession();
		User userfromdatabase = s.get(User.class, userformbrower.getUsername());
		System.out.println("User from database is :" + userfromdatabase);

		boolean answer = userfromdatabase.equals(userformbrower);

		System.out.println("answer from equal() from object class is :" + answer);
		if (answer) {
			httpSession = request.getSession();
			httpSession.setAttribute("score", 0);
			httpSession.setAttribute("questionIndex", 0);

			HashMap<Integer, Answer> hashmap = new HashMap<>();

			httpSession.setAttribute("submitedDetails", hashmap);
		}

		return answer;

	}
}
