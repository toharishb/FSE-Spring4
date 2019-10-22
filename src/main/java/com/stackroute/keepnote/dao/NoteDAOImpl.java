package com.stackroute.keepnote.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.model.Note;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */

@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	private SessionFactory sessionFactory;
	@Autowired
	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}

	/*
	 * Create a new note
	 */
	
	public boolean createNote(Note note) {
		
		try {
			sessionFactory.getCurrentSession().save(note);

		}
		catch(Exception e) {
			return false;
		}
		return true;

	}

	/*
	 * Remove an existing note
	 */
	
	public boolean deleteNote(int noteId) {
		
		try {
			//Note note = getNoteById(noteId);
			sessionFactory.getCurrentSession().delete(getNoteById(noteId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			try {
				throw new NoteNotFoundException("Note Not found");
			} catch (NoteNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		return true;
	}

	/*
	 * Retrieve details of all notes by userId
	 */
	
	public List<Note> getAllNotesByUserId(String userId) {
		
		List<Note> noteList= sessionFactory.getCurrentSession().createQuery("from Note n where n.createdBy=:userID").setParameter("userID", userId).list();
		
		return noteList;

	}

	/*
	 * Retrieve details of a specific note
	 */
	
	public Note getNoteById(int noteId) throws NoteNotFoundException {
		Note note=new Note();
		try {
			note = (Note) sessionFactory.getCurrentSession().createQuery("from Note n where n.noteId=:noteId")
					.setParameter("noteId", noteId).getSingleResult();
					if(note==null)
						throw new NoteNotFoundException("Note not found");
					else
						return note;
		} catch (Exception e) {
			// TODO: handle exception
			throw new NoteNotFoundException("Note Not found");
}
}/*
	 * Update an existing note
	 */

	public boolean UpdateNote(Note note) {
		if(note!=null) {
			sessionFactory.getCurrentSession().update(note);
			return true;
		}else		
		return false;

	}

}
