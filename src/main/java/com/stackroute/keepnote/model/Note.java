package com.stackroute.keepnote.model;

import java.util.Date;

/*
 * The class "Note" will be acting as the data model for the Note Table in the database. 
 * Please note that this class is annotated with @Entity annotation. 
 * Hibernate will scan all package for any Java objects annotated with the @Entity annotation. 
 * If it finds any, then it will begin the process of looking through that particular 
 * Java object to recreate it as a table in your database.
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
 * The class "Note" will be acting as the data model for the note Table in the database. Please
 * note that this class is annotated with @Entity annotation. Hibernate will scan all package for 
 * any Java objects annotated with the @Entity annotation. If it finds any, then it will begin the 
 * process of looking through that particular Java object to recreate it as a table in your database.
 */

@Entity
@Table(name = "Note")
public class Note {

	@Id
	@Column(name = "note_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int noteId;

	@Column(name = "note_title")
	private String noteTitle;

	@Column(name = "note_content")
	private String noteContent;

	@Column(name = "note_status")
	private String noteStatus;

	@Column(name = "note_creation_date")
	private Date noteCreatedAt;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = true)
	private Category category;

	@ManyToOne
	@JoinColumn(name = "reminder_id", nullable = true)
	private Reminder reminder;

	@Column(name = "user_id")
	private String createdBy;

	public Note() {

	}

	public Note(int i, String string, String string2, String string3, Date date, Category category, Reminder reminder,
			String userid) {

		super();
		this.noteId = i;
		this.noteTitle = string;
		this.noteContent = string2;
		this.noteStatus = string3;
		this.noteCreatedAt = date;
		this.category = category;
		this.reminder = reminder;
		this.createdBy = userid;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Reminder getReminder() {
		return reminder;
	}

	public void setReminder(Reminder reminder) {
		this.reminder = reminder;
	}

	public int getNoteId() {

		return this.noteId;
	}

	public String getNoteTitle() {

		return this.noteTitle;
	}

	public String getNoteContent() {

		return this.noteContent;
	}

	public String getNoteStatus() {

		return this.noteStatus;
	}

	public Date getNoteCreatedAt() {
		return noteCreatedAt;
	}

	public void setNoteId(int parseInt) {
		this.noteId = parseInt;
	}

	public void setNoteTitle(String parameter) {
		this.noteTitle = parameter;
	}

	public void setNoteContent(String parameter) {
		this.noteContent = parameter;
	}

	public void setNoteStatus(String parameter) {
		this.noteStatus = parameter;
	}

	public void setNoteCreatedAt(Date now) {
		this.noteCreatedAt = now;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}