package rmi.general;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.User;

public interface UserInterface extends Remote{
	public User checkLogin(User user) throws RemoteException;
	public boolean register(User user) throws RemoteException;
	public ArrayList<User> getFriends(User user) throws RemoteException;
	public ArrayList<User> getFriendRequest(User userGetRequest) throws RemoteException;
	public boolean acceptRequest(User userAccept, User threadUserAccept) throws RemoteException;
	public boolean deleteRequest(User userDelete, User threadUserDelete) throws RemoteException;
	public ArrayList<User> searchUser(String name) throws RemoteException;
	public boolean addRequest(User userAdd, User user) throws RemoteException;
	public boolean checkFriendShip(User user, User userAdd) throws RemoteException;
	public ArrayList<Object> getUserAndRoom(User user) throws RemoteException;
	public boolean deleteFriend(User userCancel, User threadUser) throws RemoteException;
	
}
