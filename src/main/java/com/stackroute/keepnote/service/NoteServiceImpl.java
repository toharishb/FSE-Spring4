package com.stackroute.keepnote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.dao.CategoryDAO;
import com.stackroute.keepnote.dao.NoteDAO;
import com.stackroute.keepnote.dao.ReminderDAO;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Note;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn�t currently 
* provide any additional behavior over the @Component annotation, but it�s a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class NoteServiceImpl implements NoteService {

	/*
	 * Autowiring should be implemented for the NoteDAO,CategoryDAO,ReminderDAO.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	private NoteDAO noteDao;
	@Autowired
	private CategoryDAO categoryDao;
	@Autowired
	private ReminderDAO reminderDao;
	
	public NoteServiceImpl(NoteDAO noteDao,CategoryDAO categoryDao,ReminderDAO reminderDao) {
		
		this.noteDao=noteDao;
		this.categoryDao=categoryDao;
		this.reminderDao=reminderDao;
		
	}


	/*
	 * This method should be used to save a new note.
	 */
	
	public boolean createNote(Note note) throws ReminderNotFoundException, CategoryNotFoundException {
		/*if(note.getCategory()==null&&note.getReminder()==null) {
			return noteDao.createNote(note);
		}
		else if(note.getCategory()==null) {
			throw new ReminderNotFoundException("Category not found");
		}else {*/
		if(note.getCategory()!=null) {
			this.categoryDao.getCategoryById(note.getCategory().getCategoryId());
		}
		if(note.getReminder()!=null) {
			this.reminderDao.getReminderById(note.getReminder().getReminderId());
		}
			return noteDao.createNote(note);
		//}

	}

	/* This method should be used to delete an existing note. */

	public boolean deleteNote(int noteId) throws NoteNotFoundException {
		return this.noteDao.deleteNote(noteId);

	}
	/*
	 * This method should be used to get a note by userId.
	 */

	public List<Note> getAllNotesByUserId(String userId) {
		return this.noteDao.getAllNotesByUserId(userId);

	}

	/*
	 * This method should be used to get a note by noteId.
	 */
	public Note getNoteById(int noteId) throws NoteNotFoundException {
		return noteDao.getNoteById(noteId);

	}

	/*
	 * This method should be used to update a existing note.
	 */

	public Note updateNote(Note note, int id)
			throws ReminderNotFoundException, NoteNotFoundException, CategoryNotFoundException {
		Note nte=new Note();
		if(note!=null) {
			nte=getNoteById(id);
			if(nte==null) {
				throw new NoteNotFoundException("note not found");
			}
			if (null != note.getCategory()) {
				this.categoryDao.getCategoryById(note.getCategory().getCategoryId());
			}
			
			if (null != note.getReminder()) {
				this.reminderDao.getReminderById(note.getReminder().getReminderId());
			}
			
			noteDao.UpdateNote(note);
		}
		return nte;

	}

}
