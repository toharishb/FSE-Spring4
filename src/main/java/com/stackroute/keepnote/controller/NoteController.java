package com.stackroute.keepnote.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.NoteService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class NoteController {

	/*
	 * Autowiring should be implemented for the NoteService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	@Autowired
	private NoteService noteservice;

	public NoteController(NoteService noteService) {
		
		this.noteservice=noteService;
	}

	/*
	 * Define a handler method which will create a specific note by reading the
	 * Serialized object from request body and save the note details in a Note table
	 * in the database.Handle ReminderNotFoundException and
	 * CategoryNotFoundException as well. please note that the loggedIn userID
	 * should be taken as the createdBy for the note.This handler method should
	 * return any one of the status messages basis on different situations: 1.
	 * 201(CREATED) - If the note created successfully. 2. 409(CONFLICT) - If the
	 * noteId conflicts with any existing user3. 401(UNAUTHORIZED) - If th	e user
	 * trying to perform the action has not logged in.
	 * 
	 * This handler method should map to the URL "/note" using HTTP POST method
	 */
	
	@PostMapping(value="/note" ,consumes="application/JSON")
	public ResponseEntity addNote(Note note,HttpSession session) {
		
		try {
			String sessionUserID=(String) session.getAttribute("loggedInUserId");
			if(sessionUserID==null) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}/*else if(note.getReminder()==null) {
				throw new ReminderNotFoundException("Remainder not found");
			}else if(note.getCategory()==null) {
				throw new CategoryNotFoundException("category not found");
			}*/
			Note nte=this.noteservice.getNoteById(note.getNoteId());
			if(nte==null) {
				note.setCreatedBy(sessionUserID);
				boolean flag=this.noteservice.createNote(note);
				if(flag)
					return new ResponseEntity<>(HttpStatus.CREATED);
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
			else {
				return new ResponseEntity<>(HttpStatus.CONFLICT);

			}
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.CONFLICT);

		}		
	}

	/*
	 * Define a handler method which will delete a note from a database.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * 3. 401(UNAUTHORIZED) - If the user trying to perform the action has not
	 * logged in.
	 * 
	 * This handler method should map to the URL "/note/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid noteId without {}
	 */
	
	@DeleteMapping(value="/note/{id}",produces="application/JSON")
	public ResponseEntity<?> deleteNote(@PathVariable("id") int noteID,HttpSession session) {
		
		try {
			String sessionUserID=(String) session.getAttribute("loggedInUserId");
			if(sessionUserID==null) {
				return new ResponseEntity<>("{\"success\":false}",HttpStatus.UNAUTHORIZED);
			}
			boolean result=this.noteservice.deleteNote(noteID);
			if(result) {
				return new ResponseEntity<>("{\"success\":true}",HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>("{\"success\":false}",HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<>("{\"success\":false}",HttpStatus.NOT_FOUND);

		}		
	}
	
	/*@DeleteMapping(value="/user/{id}",produces="application/JSON")
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
		
		
	}*/

	/*
	 * Define a handler method which will update a specific note by reading the
	 * Serialized object from request body and save the updated note details in a
	 * note table in database handle ReminderNotFoundException,
	 * NoteNotFoundException, CategoryNotFoundException as well. please note that
	 * the loggedIn userID should be taken as the createdBy for the note. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note updated successfully. 2.
	 * 404(NOT FOUND) - If the note with specified noteId is not found. 3.
	 * 401(UNAUTHORIZED) - If the user trying to perform the action has not logged
	 * in.
	 * 
	 * This handler method should map to(String) session.getAttribute("loggedINUserId") the URL "/note/{id}" using HTTP PUT method.
	 */
	
	@PutMapping(value="/note/{id}",consumes="application/JSON",produces="application/JSON")
	public ResponseEntity update(@PathVariable("id") int noteID,@RequestBody Note note,HttpSession session) throws Exception {
		try {
			String sessionUserID=(String) session.getAttribute("loggedInUserId");
					if(sessionUserID==null) {
						return new ResponseEntity<> (HttpStatus.UNAUTHORIZED);
					}
					Note nte=this.noteservice.updateNote(note, noteID);
					if(nte!=null) {
						return new ResponseEntity<>(HttpStatus.OK);
					}else {
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);

					}
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}

	}

	/*
	 * Define a handler method which will get us the notes by a userId.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note found successfully. 2.
	 * 401(UNAUTHORIZED) -If the user trying to perform the action has not logged
	 * in.
	 * 
	 * 
	 * This handler method should map to the URL "/note" using HTTP GET method
	 */
	@GetMapping(value="/note",produces="application/JSON")
	public ResponseEntity getNote(HttpSession session) {
		try {
			String sessionuserID=(String) session.getAttribute("loggedInUserId");
			if(sessionuserID==null) {
				return new ResponseEntity<>("{\"success\":false}",HttpStatus.UNAUTHORIZED);
			}
			List<Note> note=this.noteservice.getAllNotesByUserId(sessionuserID);
			if(note!=null)
				return new ResponseEntity<>(note,HttpStatus.OK);
			else
				return new ResponseEntity<>("{\"success\":false}",HttpStatus.NOT_FOUND);

			
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("{\"success\":false}",HttpStatus.NOT_FOUND);

		}
	}	
	

}
