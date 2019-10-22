package com.stackroute.keepnote.dao;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;

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
public class UserDaoImpl implements UserDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	private SessionFactory sessionFactory;
	
	@Autowired
	public UserDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}

	/*
	 * Create a new user
	 */

	public boolean registerUser(User user) {
		
		Object obj = sessionFactory.getCurrentSession().save(user);
		if(null != obj) {
			return true;
		}
		return false;
	}

	/*
	 * Update an existing user
	 */

	public boolean updateUser(User user) {
		if(user != null) {
			this.sessionFactory.getCurrentSession().update(user);
			return true;
			}else {
				try {
					throw new Exception();
				} catch (Exception e) {
					return false;
				}
			}
	}

	/*
	 * Retrieve details of a specific user
	 */
	public User getUserById(String UserId) {
		User user;
		try {
			 user=(User) sessionFactory.getCurrentSession().createQuery("from User u where u.userId=:user")
					.setParameter("user", UserId).getSingleResult();
			if(user==null) {
				throw new UserNotFoundException("User not found");
			}
			else {
				return user;
			}
		}
		catch(Exception e){
			return null;
		}
		

	}

	/*
	 * validate an user
	 */

	public boolean validateUser(String userId, String password) throws UserNotFoundException {
		
		User user;
		try {
			user=(User) sessionFactory.getCurrentSession().createQuery("from User u where u.userId=:user and u.userPassword=:password")
			.setParameter("user", userId).setParameter("password", password)
			.getSingleResult();
			if(user==null) {
				throw new UserNotFoundException("User not found");
			}
			else {
				return true;
			}
		}
		catch(Exception e) {
			throw new UserNotFoundException("User not found");
			

		}
		
	}

	/*
	 * Remove an existing user
	 */
	public boolean deleteUser(String userId) {
			
		try {
				sessionFactory.getCurrentSession().delete(getUserById(userId));
		}
		catch (Exception e) {
			return false;
		}

		return true;

	}

}
