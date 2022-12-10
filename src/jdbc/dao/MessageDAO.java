package jdbc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.*;

public class MessageDAO extends DAO {
	private static MessageDAO messageDAO;
    public MessageDAO() {
        super();
    }
    
    public static MessageDAO getInstance() {
        if (messageDAO == null) {
        	messageDAO = new MessageDAO();
        }
        return messageDAO;
    }

	public ArrayList<Message> getRoomMessage(Room room) {
		ArrayList<Message> messageList = new ArrayList<>();
		String sql= "SELECT * FROM message WHERE id_room = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, room.getId());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Message message = new Message();
				User user  = new User();
				message.setId(rs.getInt("id"));
				message.setContent(rs.getString("content"));
				user.setId((rs.getInt("id_user")));
				message.setUser(user);
				messageList.add(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageList;
	}

	public boolean addMessage(Message mess) {
		String sql = "INSERT INTO message (content, id_room, id_user) VALUES (?, ?, ?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, mess.getContent());
			ps.setInt(2, mess.getId_room());
			ps.setInt(3, mess.getUser().getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ArrayList<Message> getSingleRoomMessage(int id) {
		ArrayList<Message> messageList = new ArrayList<>();
		String sql= "SELECT * FROM message WHERE id_room = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Message message = new Message();
				User user = new User();
				message.setId(rs.getInt("id"));
				message.setContent(rs.getString("content"));
				user.setId((rs.getInt("id_user")));
				message.setUser(user);
				messageList.add(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageList;
	}
    
}
