package com.stackroute.keepnote.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*
 * The class "Reminder" will be acting as the data model for the Reminder Table in the database. 
 * Please note that this class is annotated with @Entity annotation. 
 * Hibernate will scan all package for any Java objects annotated with the @Entity annotation. 
 * If it finds any, then it will begin the process of looking through that particular 
 * Java object to recreate it as a table in your database.
 */
@Entity
@Table(name="Reminder")
public class Reminder {
	/*
	 * This class should have seven fields
	 * (reminderId,reminderName,reminderDescription,reminderType,
	 * reminderCreatedBy,reminderCreationDate,notes). Out of these seven fields, the
	 * field reminderId should be primary key and auto-generated. This class should
	 * also contain the getters and setters for the fields along with the no-arg ,
	 * parameterized constructor and toString method. The value of
	 * reminderCreationDate should not be accepted from the user but should be
	 * always initialized with the system date. annotate notes field with @OneToMany
	 * and @JsonIgnore
	 */
	@Id
	@Column(name="reminder_id")
	private int reminderId;
	
	@Column(name="reminder_name")
	private String reminderName;
	
	@Column(name="reminder_description")
	private String reminderDescription;
	
	@Column(name="reminder_type")
	private String reminderType;
	
	@Column(name="user_id")
	private String reminderCreatedBy;
	
	@OneToMany(mappedBy="reminder")
	private  List<Note> notes;
	
	@Column(name="reminder_creation_date")
	private Date reminderCreationDate;

	public Reminder() {

	}

	public int getReminderId() {
		return reminderId;
	}

	public void setReminderId(int reminderId) {
		this.reminderId = reminderId;
	}

	public String getReminderName() {
		return reminderName;
	}

	public void setReminderName(String reminderName) {
		this.reminderName = reminderName;
	}

	public String getReminderDescription() {
		return reminderDescription;
	}

	public void setReminderDescription(String reminderDescription) {
		this.reminderDescription = reminderDescription;
	}

	public String getReminderType() {
		return reminderType;
	}

	public void setReminderType(String reminderType) {
		this.reminderType = reminderType;
	}

	public String getReminderCreatedBy() {
		return reminderCreatedBy;
	}

	public void setReminderCreatedBy(String reminderCreatedBy) {
		this.reminderCreatedBy = reminderCreatedBy;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public Date getReminderCreationDate() {
		return reminderCreationDate;
	}

	public void setReminderCreationDate(Date reminderCreationDate) {
		this.reminderCreationDate = reminderCreationDate;
	}

	public Reminder(int reminderId, String reminderName, String reminderDescription, String reminderType,
			String reminderCreatedBy, List<Note> notes, Date reminderCreationDate) {
		super();
		this.reminderId = reminderId;
		this.reminderName = reminderName;
		this.reminderDescription = reminderDescription;
		this.reminderType = reminderType;
		this.reminderCreatedBy = reminderCreatedBy;
		this.notes = notes;
		this.reminderCreationDate = reminderCreationDate;
	}

	

}