package com.stackroute.keepnote.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;

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
public class CategoryDAOImpl implements CategoryDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	
	private SessionFactory sessionFactory;
	@Autowired
	public CategoryDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;

	}

	/*
	 * Create a new category
	 */
	public boolean createCategory(Category category) {
		boolean result=false;
		if(category!=null) {
			
			this.sessionFactory.getCurrentSession().save(category);
			result= true;
		}else {
			try {
				throw new CategoryNotFoundException("Category not added");
			} catch (CategoryNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;

	}

	/*
	 * Remove an existing category
	 */
	public boolean deleteCategory(int categoryId) {
		try {
			sessionFactory.getCurrentSession().delete(getCategoryById(categoryId));
			return true;
		}catch(Exception e) {
			
			return false;
		}

	}
	/*
	 * Update an existing category
	 */

	public boolean updateCategory(Category category) {
		boolean result=false; 
		if (category != null) {
			try {
				sessionFactory.getCurrentSession().update(category);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
				
			}
		
		return result;
	}
	/*
	 * Retrieve details of a specific category
	 */

	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
		Category category=new Category();
		
		 category=(Category) sessionFactory.getCurrentSession().createQuery("from Category c where c.categoryId=:categoryid").setParameter("categoryid", categoryId).uniqueResult();
		 if(category==null)
			 throw new CategoryNotFoundException("Category not found");
		 else
			return category;
	}

	/*
	 * Retrieve details of all categories by userId
	 */
	public List<Category> getAllCategoryByUserId(String userId) {
		
		List<Category> categoryList=sessionFactory.getCurrentSession().createQuery("from Category where categoryCreatedBy=:userId").setParameter("userId", userId).list();

		return categoryList;

	}

}
