package rmi.general;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import model.Message;
import model.Room;

public interface MessageInterface extends Remote{
	public boolean addMessage(Message mess) throws RemoteException;

	List<Message> getSingleRoomMessage(int id) throws RemoteException;

	public ArrayList<Message> getRoomMessage(Room room) throws RemoteException;
}
