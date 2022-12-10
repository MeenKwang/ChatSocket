package jdbc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import model.Room;
import model.User;

public class RoomDAO extends DAO{
	
	private static RoomDAO roomDAO;
    public RoomDAO() {
        super();
    }
    
    public static RoomDAO getInstance() {
        if (roomDAO == null) {
            roomDAO = new RoomDAO();
        }
        return roomDAO;
    }
	
	public boolean createSingleRoom(User userAccept, User threadUserAccept) {
		boolean checkCreateRoom = true;
		int room_id = 0;
		String sql = "INSERT INTO tblroom (name) VALUES (?)";
		String sql2 = "UPDATE tblfriendship SET room_id = ? WHERE user_id = ? AND friend_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "SingleChat_" + userAccept.getId() + "_" + threadUserAccept.getId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
            	room_id = rs.getInt(1);
            }
        }catch(Exception e) {
            e.printStackTrace();
            checkCreateRoom = false;
        }
        if(checkCreateRoom) {
        	try {
        		PreparedStatement ps = con.prepareStatement(sql2);
        		ps.setInt(1, room_id);
        		ps.setInt(2, threadUserAccept.getId());
        		ps.setInt(3, userAccept.getId());
        		ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
        	
        }
        System.out.println("Room id la: " + room_id);
        return true;
	}
	
    public ArrayList<Room> getRoom(User user) {
    	String sql = "SELECT * FROM tblroom INNER JOIN tbluser_room ON tblroom.id = tbluser_room.room_id WHERE tbluser_room.user_id = ?";
    	ArrayList<Room> rooms = new ArrayList<Room>();
    	try {
    		PreparedStatement ps = con.prepareStatement(sql);
    		ps.setInt(1, user.getId());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Room room = new Room();
				room.setId(rs.getInt("id"));
				room.setName(rs.getString("name"));
				rooms.add(room);
			}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
		return rooms;
    	
    }

	public ArrayList<User> getRoomMember(int id) {
		String sql = "SELECT * FROM tbluser INNER JOIN tbluser_room ON tbluser.id = tbluser_room.user_id WHERE tbluser_room.room_id = ? ";
		ArrayList<User> roomFriends = new ArrayList<>();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				roomFriends.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roomFriends;
	}
	
	public boolean leaveRoom(User me, Room myRoom) {
		String sql = "DELETE FROM tbluser_room WHERE user_id = ? AND room_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, me.getId());
            ps.setInt(2, myRoom.getId());
            
            ps.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
		return true;
	}

	public int getSingleRoom(User userSingleChat, User threadUser) {
		String sql = "SELECT room_id FROM tblfriendship WHERE user_id = ? AND friend_id = ? OR user_id = ? AND friend_id = ?";
		int i = 0;
        try {
    		PreparedStatement ps = con.prepareStatement(sql);
    		ps.setInt(1, threadUser.getId());
    		ps.setInt(2, userSingleChat.getId());
    		ps.setInt(3, userSingleChat.getId());
    		ps.setInt(4, threadUser.getId());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				i = rs.getInt("room_id");
			}
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
		return i;
	}

	public int createGroup(String name) {
		String sql = "INSERT INTO tblroom (name) VALUES (?)";
		int room_id = 0;
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
            	room_id = rs.getInt(1);
            }
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
        return room_id;
	}

	public boolean addGroupMember(int group_id, ArrayList<User> groupList) {
		String sql = "INSERT INTO tbluser_room (user_id, room_id) VALUES (?, ?)";
		for(User member: groupList) {
	        try {
	            PreparedStatement ps = con.prepareStatement(sql);
	            ps.setInt(1, member.getId());
	            ps.setInt(2, group_id);
	            ps.executeUpdate();
	        }catch(Exception e) {
	            e.printStackTrace();
	            return false;
	        }
		}
		return true;
	}

}
