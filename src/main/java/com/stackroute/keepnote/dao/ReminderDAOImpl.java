package com.stackroute.keepnote.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;

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
public class ReminderDAOImpl implements ReminderDAO {
	
	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	@Autowired
	private SessionFactory sessionFactory;
	public ReminderDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}

	/*
	 * Create a new reminder
	 */

	public boolean createReminder(Reminder reminder) {
		try {
			sessionFactory.getCurrentSession().save(reminder);

		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	/*
	 * Update an existing reminder
	 */

	public boolean updateReminder(Reminder reminder) {
		try {
			sessionFactory.getCurrentSession().update(reminder);
			return true;

		}catch(Exception e) {
			return false;
		}
	}

	/*
	 * Remove an existing reminder
	 */
	
	public boolean deleteReminder(int reminderId) {
		try {
			sessionFactory.getCurrentSession().delete(getReminderById(reminderId));
			return true;

		}catch(Exception e) {
			return false;
		}

	}

	/*
	 * Retrieve details of a specific reminder
	 */
	
	public Reminder getReminderById(int reminderId) throws ReminderNotFoundException {
		
		Reminder reminder=new Reminder();
		try {
			reminder = (Reminder) sessionFactory.getCurrentSession().createQuery("from Reminder r where r.reminderId=:reminderId")
					.setParameter("reminderId", reminderId).getSingleResult();
					if(reminder==null) {
						throw new ReminderNotFoundException("Reminder not found");
					}
					else {
						return reminder;
					}
		} catch (Exception e) {
			// TODO: handle exception
			throw new ReminderNotFoundException("Reminder Not Found");
		}
		
	}

	/*
	 * Retrieve details of all reminders by userId
	 */
	
	public List<Reminder> getAllReminderByUserId(String userId) {
		
		List<Reminder> reminderList=sessionFactory.getCurrentSession().createQuery("from Reminder r where r.reminderCreatedBy=:userId").setParameter("userId", userId).list();
		return reminderList;

	}

}
