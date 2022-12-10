package model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String content;
	private Date time;
	private int id_room;
	private User user;
	
	public Message() {
		
	}
	public Message(int id, String content, Date time, int id_room, User user) {
		super();
		this.id = id;
		this.content = content;
		this.time = time;
		this.id_room = id_room;
		this.user = user;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getId_room() {
		return id_room;
	}
	public void setId_room(int id_room) {
		this.id_room = id_room;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
