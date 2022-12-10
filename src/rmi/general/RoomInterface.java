package rmi.general;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import model.Room;
import model.User;

public interface RoomInterface extends Remote{
	public boolean createSingleRoom(User userAccept, User threadUserAccept) throws RemoteException;

	public boolean leaveRoom(User me, Room myRoom) throws RemoteException;

	public ArrayList<Room> getRoom(User user) throws RemoteException;

	public ArrayList<User> getRoomMember(int id) throws RemoteException;

	public int getSingleRoom(User userSingleChat, User threadUser) throws RemoteException;

	public int createGroup(String name) throws RemoteException;

	public boolean addGroupMember(int group_id, ArrayList<User> groupList) throws RemoteException;

}
