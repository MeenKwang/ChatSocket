package jdbc.dao;
 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.User;
 
public class UserDAO extends DAO{
	private static UserDAO userDAO;
    public UserDAO() {
        super();
    }
    
    public static UserDAO getInstance() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }
     
    public User checkLogin(User user) {
        String sql = "SELECT id, name, position FROM tbluser WHERE username = ? AND password = ?";
        User userLogin = new User();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
             
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                userLogin.setUsername(user.getUsername());
                userLogin.setPassword(user.getPassword());
                userLogin.setId(rs.getInt("id"));
                userLogin.setName(rs.getString("name"));
                userLogin.setPosition(rs.getString("position"));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return userLogin;
    }
    
    public boolean register(User user) {
        String sql = "INSERT INTO tbluser(name, username, password, position) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPosition());
            
            ps.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public ArrayList<User> getFriends(User user){
        ArrayList<User> result = new ArrayList<User>();
        String sql = "SELECT u.id, u.name, u.position FROM tbluser AS u INNER JOIN tblfriendship AS f ON u.id = f.friend_id WHERE f.user_id = ? AND status = 1";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
 
            while(rs.next()){
                User friend = new User();
                friend.setId(rs.getInt("id"));
                friend.setName(rs.getString("name"));
                friend.setPosition(rs.getString("position"));
                result.add(friend);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        String sql2 = "SELECT u.id, u.name, u.position FROM tbluser AS u INNER JOIN tblfriendship AS f ON u.id = f.user_id WHERE f.friend_id = ? AND status = 1";
        try{
            PreparedStatement ps = con.prepareStatement(sql2);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
 
            while(rs.next()){
                User friend = new User();
                friend.setId(rs.getInt("id"));
                friend.setName(rs.getString("name"));
                friend.setPosition(rs.getString("position"));
                result.add(friend);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

	public ArrayList<User> getFriendRequest(User userGetRequest) {
		ArrayList<User> friendRequest = new ArrayList<User>();
		String sql = "SELECT u.id, u.name FROM tbluser AS u INNER JOIN tblfriendship AS tfr ON u.id = tfr.friend_id WHERE tfr.user_id = ? AND tfr.status = 0";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userGetRequest.getId());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				friendRequest.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return friendRequest;
	}

	public boolean acceptRequest(User friend, User user) {
		String sql = "UPDATE tblfriendship SET status = 1 WHERE friend_id = ? AND user_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, friend.getId());
            ps.setInt(2, user.getId());
            
            ps.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
	}

	public boolean deleteRequest(User friend, User user) {
		String sql = "DELETE FROM tblfriendship WHERE user_id = ? AND friend_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.setInt(2, friend.getId());
            
            ps.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
		return true;
	}
	
	public ArrayList<User> searchUser(String name) {
		String sql = "SELECT * FROM tbluser WHERE name = ? ";
		ArrayList<User> userList = new ArrayList<>();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				userList.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	public boolean addRequest(User userAdd, User user) {
		String sql = "INSERT INTO tblfriendship(user_id, friend_id, status) VALUES(?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userAdd.getId());
            ps.setInt(2, user.getId());
            ps.setInt(3, 0);
            
            ps.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
	}

	public boolean checkFriendShip(User user, User userCheck) {
		String sql = "SELECT * FROM tblfriendship WHERE user_id = ? AND friend_id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userCheck.getId());
			ps.setInt(2, user.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteFriend(User userCancel, User threadUser) {
		String sql = "UPDATE tblfriendship SET status = 2 WHERE user_id = ? AND friend_id = ? OR user_id = ? AND friend_id = ?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userCancel.getId());
            ps.setInt(2, threadUser.getId());
            ps.setInt(3, threadUser.getId());
            ps.setInt(4, userCancel.getId());
			
			ps.executeUpdate();
		} catch (Exception e) {
            e.printStackTrace();
            return false;
		}
		return true;
	}
	
}