package com.stackroute.keepnote.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation.
 * Annotate class with @SessionAttributes this  annotation is used to store the model attribute in the session.
 */
@RestController
public class UserAuthenticationController {

	/*
	 * Autowiring should be implemented for the UserService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */

	@Autowired
	private UserService userservice;
	
	public UserAuthenticationController(UserService userService) {

		this.userservice =userService;
	}

	/*
	 * Define a handler method which will authenticate a user by reading the
	 * Serialized user object from request body containing the userId and password
	 * and validating the same. Post login, the userId will have to be stored into
	 * session object, so that we can check whether the user is logged in for all
	 * other services handle UserNotFoundException as well. This handler method
	 * should return any one of the status messages basis on different situations:
	 * 1. 200(OK) - If login is successful. 2. 401(UNAUTHORIZED) - If login is not
	 * successful
	 * 
	 * This handler method should map to the URL "/login" using HTTP POST method
	 */
	
	@RequestMapping(value="/login",method=RequestMethod.POST,consumes="application/JSON",produces="application/JSON")
	public ResponseEntity<?> loginUSer(@RequestBody User user,HttpSession ses){
		try {
			String username = user.getUserId();
			String password = user.getUserPassword();
		
		boolean flag = this.userservice.validateUser(username, password);
		if(flag) {
			
			ses.setAttribute("loggedInUserId", username);
			return new ResponseEntity<>("{\"success\":true}",HttpStatus.OK);
		}else {
			return new ResponseEntity<>("{\"success\":true}",HttpStatus.UNAUTHORIZED);
		}
		
		}catch(Exception ex) {
			
			return new ResponseEntity<>("{\"success\":false}",HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET,produces="application/JSON")
	public ResponseEntity<?> logoutUser(HttpSession ses){
		try {
			String userid = (String) ses.getAttribute("loggedInUserId");
			User u = this.userservice.getUserById(userid);
			if(u != null) {
				ses.removeAttribute("loggedInUserId");
				return new ResponseEntity<>("{\"success\":true}",HttpStatus.OK);
			}else {
			
			return new ResponseEntity<>("{\"success\":false}",HttpStatus.BAD_REQUEST);
			}
		
		
		}catch(Exception ex) {
			
			return new ResponseEntity<>("{\"success\":false}",HttpStatus.UNAUTHORIZED);
		}
		
	}

	/*
	 * Define a handler method which will perform logout. Post logout, the user
	 * session is to be destroyed. This handler method should return any one of the
	 * status messages basis on different situations: 1. 200(OK) - If logout is
	 * successful 2. 400(BAD REQUEST) - If logout has failed
	 * 
	 * This handler method should map to the URL "/logout" using HTTP GET method
	 */

}