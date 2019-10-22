package com.stackroute.keepnote.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.UserAlreadyExistException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserService;

/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class UserController {

	/*
	 * Autowiring should be implemented for the UserService. (Use Constructor-based
	 * autowiring) Please note that we should not create an object using the new
	 * keyword
	 */
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService=userService;
	}

	/*
	 * Define a handler method which will create a specific user by reading the
	 * Serialized object from request body and save the user details in a User table
	 * in the database. This handler method should return any one of the status
	 * messages basis on different situations: 1. 201(CREATED) - If the user created
	 * successfully. 2. 409(CONFLICT) - If the userId conflicts with any existing
	 * user
	 * 
	 * Note: ------ This method can be called without being logged in as well as
	 * when a new user will use the app, he will register himself first before
	 * login.
	 * 
	 * This handler method should map to the URL "/user/register" using HTTP POST
	 * method
	 */
	@PostMapping(value = "/user/register", consumes="application/JSON",produces="application/JSON")
	public ResponseEntity addUser(@RequestBody User user,HttpSession session) throws UserAlreadyExistException{
	
		try {
			
			this.userService.registerUser(user);
			
			return new ResponseEntity(user, HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(user,HttpStatus.CONFLICT);
		}
	}
	
	/*
	 * Define a handler method which will update a specific user by reading the
	 * Serialized object from request body and save the updated user details in a
	 * user table in database handle exception as well. This handler method should
	 * return any one of the status messages basis on different situations: 1.
	 * 200(OK) - If the user updated successfully. 2. 404(NOT FOUND) - If the user
	 * with specified userId is not found. 3. 401(UNAUTHORIZED) - If the user trying
	 * to perform the action has not logged in.
	 * 
	 * This handler method should map to the URL "/user/{id}" using HTTP PUT method.
	 */
	@PutMapping(value="/user/{id}",consumes="application/JSON",produces="application/JSON")
	public ResponseEntity update(@PathVariable("id") String userID,@RequestBody User user,HttpSession session) throws Exception {
		try {
			String sessionUserID=(String) session.getAttribute("loggedInUserId");
					if(sessionUserID==null||!sessionUserID.equalsIgnoreCase(userID)) {
						return new ResponseEntity<> (user,HttpStatus.UNAUTHORIZED);
					}
					User usr=this.userService.updateUser(user, userID);
					if(usr!=null) {
						return new ResponseEntity<>(user,HttpStatus.OK);
					}else {
						return new ResponseEntity<>(user,HttpStatus.NOT_FOUND);

					}
			
		}catch(Exception e) {
			return new ResponseEntity<>(user,HttpStatus.NOT_FOUND);

		}

	}
	/*
	 * Define a handler method which will delete a user from a database.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the user deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the user with specified userId is not found.
	 * 3. 401(UNAUTHORIZED) - If the user trying to perform the action has not
	 * logged in.
	 * 
	 * This handler method should map to the URL "/user/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid userId without {}
	 */
	
	@DeleteMapping(value="/user/{id}",produces="application/JSON")
	public ResponseEntity<?> deleteUser(@PathVariable("id") String userID,HttpSession session) {
		
		try {
			String sessionUserID=(String) session.getAttribute("loggedInUserId");
			if(sessionUserID==null||!sessionUserID.equalsIgnoreCase(userID)) {
				return new ResponseEntity<>("{\"success\":false}",HttpStatus.UNAUTHORIZED);
			}
			if(this.userService.deleteUser(userID)) {
				return new ResponseEntity<>("{\"success\":true}",HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>("{\"success\":false}",HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("{\"success\":false}",HttpStatus.NOT_FOUND);

		}
		
		
	}
	
	/*
	 * Define a handler method which will show details of a specific user handle
	 * UserNotFoundException as well. This handler method should return any one of
	 * the status messages basis on different situations: 1. 200(OK) - If the user
	 * found successfully. 2. 401(UNAUTHORIZED) - If the user trying to perform the
	 * action has not logged in. 3. 404(NOT FOUND) - If the user with specified
	 * userId is not found. This handler method should map to the URL "/user/{id}"
	 * using HTTP GET method where "id" should be replaced by a valid userId without
	 * {}
	 */

	@GetMapping(value="/user/{id}",produces="application/JSON")
	public ResponseEntity<?> getUser(@PathVariable("id") String userid, HttpSession ses) {
		try {

			String sessionUserid = (String) ses.getAttribute("loggedInUserId");
			if (sessionUserid == null || !sessionUserid.equalsIgnoreCase(userid)) {
				return new ResponseEntity<>("{\"success\":false}", HttpStatus.UNAUTHORIZED);
			}
			User u = this.userService.getUserById(userid);
			if (u != null) {

				return new ResponseEntity<>(u, HttpStatus.OK);
			} else {

				return new ResponseEntity<>("{\"success\":false}", HttpStatus.NOT_FOUND);
			}

		} catch (Exception ex) {

			return new ResponseEntity<>("{\"success\":false}", HttpStatus.NOT_FOUND);
		}

	}
	
	

}