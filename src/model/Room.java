package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private ArrayList<User> roomUser;
	
	public Room() {
		super();
		
	}
	public Room(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<User> getRoomUser() {
		return roomUser;
	}
	public void setRoomUser(ArrayList<User> roomUser) {
		this.roomUser = roomUser;
	}
	
	
}
