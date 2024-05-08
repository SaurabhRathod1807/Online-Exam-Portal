package com.example.demo.Controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.User.Answer;
import com.example.demo.User.Question;
import jakarta.persistence.criteria.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin("http://localhost:4200")
public class QuestionController {

	@Autowired
	SessionFactory sf;
	
	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping("getFirstQuestion/{subjectfromangular}")
	public Question getFirstQuestion(@PathVariable String subjectfromangular, HttpServletRequest request) {
		HttpSession httpSession = LoginController.httpSession;

		Session s = sf.openSession();

		Query query = s.createQuery("from Question where subject=:subjectName");
		query.setParameter("subjectName", subjectfromangular);

		List<Question> list = query.list();

		httpSession.setAttribute("allquestion", list);
		Question question = list.get(0);

		return question;
	}
	@RequestMapping("getallAnswer")
	public Collection<Answer> getallAnswer(){
		HttpSession httpSession = LoginController.httpSession;

		HashMap<Integer, Answer> hashmap = (HashMap<Integer, Answer>) httpSession.getAttribute("submitedDetails");

		Collection<Answer> collection = hashmap.values();
		
		return  collection;
	}
	
//check()	{"username":"xz", "password":"xz123"}

	@RequestMapping("nextquestion")
	public Question nextquestion() {

		HttpSession httpSession = LoginController.httpSession;

		List<Question> listofquestion = (List<Question>) httpSession.getAttribute("allquestion");
		Question question = null;
		if ((int) httpSession.getAttribute("questionIndex") < listofquestion.size() - 1) {

			httpSession.setAttribute("questionIndex", (int) httpSession.getAttribute("questionIndex") + 1);

			question = listofquestion.get((int) httpSession.getAttribute("questionIndex"));
		} else {
//			return listofquestion.get(listofquestion.size()-1);

			httpSession.setAttribute("questionIndex", 0);
			question = listofquestion.get((int) httpSession.getAttribute("questionIndex"));

		}

		return question;
	}

	@RequestMapping("previousquestion")
	public Question previousquestion() {

		HttpSession httpSession = LoginController.httpSession;

		List<Question> listofquestion = (List<Question>) httpSession.getAttribute("allquestion");
		Question question = null;
		if ((int) httpSession.getAttribute("questionIndex") > 0) {

			httpSession.setAttribute("questionIndex", (int) httpSession.getAttribute("questionIndex") - 1);

			question = listofquestion.get((int) httpSession.getAttribute("questionIndex"));
		} else {
//			return listofquestion.get(listofquestion.size()-1);

			httpSession.setAttribute("questionIndex", listofquestion.size() - 1);
			question = listofquestion.get((int) httpSession.getAttribute("questionIndex"));

		}

		return question;
	}

	@PostMapping("qsaveDB")
	public boolean qsaveDB(@RequestBody Question question) {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		s.persist(question);
		t.commit();
		
		return true;	
		
	}
//	{"qno":1,"qtext":"what","submittedAnswer":"A","correctAnswer":"B"}

	@PostMapping("saveAnswer")
	public void saveAnswer(@RequestBody Answer answer) {

		HttpSession httpSession = LoginController.httpSession;

		HashMap<Integer, Answer> hashmap = (HashMap<Integer, Answer>) httpSession.getAttribute("submitedDetails");

		hashmap.put(answer.getQno(), answer);

		System.out.println(hashmap);
	}

	@RequestMapping("calculateScore")
	public Integer calculateScore() {

		HttpSession httpSession = LoginController.httpSession;

		HashMap<Integer, Answer> hashmap = (HashMap<Integer, Answer>) httpSession.getAttribute("submitedDetails");

		Collection<Answer> collection = hashmap.values();

		for (Answer answer : collection) {
			if (answer.getSubmittedAnswer().equals(answer.getCorrectAnswer())) {
				httpSession.setAttribute("score", (int) httpSession.getAttribute("score") + 1);
			}
		}
		int score=(int) httpSession.getAttribute("score");
		
		httpSession.invalidate();
		
		return score;
	}
	
//	@Transactional
//	@DeleteMapping("deletequestion/{qno}/{subject}")
//	public String deletequestion(@PathVariable int qno, @PathVariable String subject) {
//		String hql="delete from Question where qno=:qno and subject=:subject";
//		
//	Query	query= (Query) entityManager.createQuery(hql);
//	 query.setParameter("qno", qno);
//	 query.setParameter("subject", subject);
//	 int result = query.executeUpdate();
//	 System.out.println("Question delete successfully" + result);
//	 
//	 return "";
//	 
//	}
	
	@GetMapping("getsubject")
	public List<String> getsubject() {
		Session s = sf.openSession();
		Query query = s.createQuery("select distinct  subject from Question");
		List<String> list=query.list();
		
		return list;
	}
	
	@GetMapping("getallquestion/{subject}")
	public List<Integer> getallquestion(@PathVariable String subject) {
		Session s = sf.openSession();
		Query query = s.createQuery("select distinct qno from Question where subject=:subject");
		query.setParameter("subject",subject);
		List<Integer> list=query.list();
		
		return list;
	}
	
	@GetMapping("getquestion/{questionNumber}")
	public Question getquestion(@PathVariable int questionNumber ) {
		
		HttpSession httpSession = LoginController.httpSession;
		
		List<Question> listofquestion=(List<Question>) httpSession.getAttribute("allquestion");
		
		Question expectedQuestion=null;
		
		
		for (Question question : listofquestion) {
			if(question.qno==questionNumber) {
				expectedQuestion=question;
			}
		}
		return expectedQuestion;
	}
	
	@DeleteMapping("deletequestion/{qno}/{subject}")
	public boolean deletequestion(@PathVariable Integer qno, @PathVariable String subject) {
		Session s = sf.openSession();
		 Transaction t = s.beginTransaction();
		Query query = s.createQuery("delete from Question where qno=:qno and subject=:subject");
		query.setParameter("qno", qno);
		query.setParameter("subject", subject);
		query.executeUpdate();
		
		t.commit();
		
		return true;
		
	}
	
}
