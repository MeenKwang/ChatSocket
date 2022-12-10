package rmi.server.control;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import jdbc.dao.UserDAO;
import jdbc.dao.MessageDAO;
import jdbc.dao.RoomDAO;
import model.IPAddress;
import model.Message;
import model.Room;
import model.User;
import rmi.general.MessageInterface;
import rmi.general.RoomInterface;
import rmi.general.UserInterface;
import rmi.server.view.ServerMainFrm;
 
 
public class ServerCtr extends UnicastRemoteObject implements UserInterface, MessageInterface, RoomInterface{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IPAddress myAddress = new IPAddress("localhost", 9999);     // default server host/port
    private Registry registry;
    private ServerMainFrm view;
    private String rmiService = "rmiServer";    // default rmi service key
     
    public ServerCtr(ServerMainFrm view) throws RemoteException{
        this.view = view;   
    }
     
    public ServerCtr(ServerMainFrm view, int port, String service) throws RemoteException{
        this.view = view;   
        myAddress.setPort(port);
        this.rmiService = service;
    }
     
    public void start() throws RemoteException{
        // registry this to the localhost
        try{
            try {
                //create new one
                registry = LocateRegistry.createRegistry(myAddress.getPort());
            }catch(ExportException e) {//the Registry exists, get it
                registry = LocateRegistry.getRegistry(myAddress.getPort());
            }
            registry.rebind(rmiService, this);
            myAddress.setHost("10.144.197.8");
            view.showServerInfo(myAddress, rmiService);
            view.showMessage("The RIM has registered the service key: " + rmiService + ", at the port: " + myAddress.getPort());
        }catch(RemoteException e){
            throw e;
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
     
    public void stop() throws RemoteException{
        // unbind the service
        try{
            if(registry != null) {
                registry.unbind(rmiService);
                UnicastRemoteObject.unexportObject(this,true);
            }
            view.showMessage("The RIM has unbinded the service key: " + rmiService + ", at the port: " + myAddress.getPort());
        }catch(RemoteException e){
            throw e;
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public User checkLogin(User user) {
		UserDAO userDAO = new UserDAO();
		return userDAO.checkLogin(user);
	}

	@Override
	public boolean register(User user) {
		return new UserDAO().register(user);
	}

	@Override
	public ArrayList<User> getFriends(User user) {
		return new UserDAO().getFriends(user);
	}

	@Override
	public ArrayList<User> getFriendRequest(User userGetRequest) {
		return new UserDAO().getFriendRequest(userGetRequest);
	}

	@Override
	public boolean acceptRequest(User userAccept, User threadUserAccept) {
		UserDAO userDAO = new UserDAO();
		userDAO.acceptRequest(userAccept, threadUserAccept);
		return true;
	}

	@Override
	public boolean deleteRequest(User userDelete, User threadUserDelete) {
		return new UserDAO().deleteRequest(userDelete, threadUserDelete);
	}

	@Override
	public ArrayList<User> searchUser(String name) {
		return new UserDAO().searchUser(name);
	}

	@Override
	public boolean addRequest(User userAdd, User user) throws RemoteException {
		return new UserDAO().addRequest(userAdd, user);
	}

	@Override
	public boolean checkFriendShip(User user, User userAdd) throws RemoteException {
		return new UserDAO().checkFriendShip(user, userAdd);
	}

	@Override
	public ArrayList<Object> getUserAndRoom(User user) throws RemoteException {
		UserDAO userDAO = new UserDAO();
		RoomDAO roomDAO = new RoomDAO();
		ArrayList<Object> userAndRoomList = new ArrayList<>();
		ArrayList<User> friendList = userDAO.getFriends(user);
		ArrayList<Room> roomList = roomDAO.getRoom(user);
		userAndRoomList.add(friendList);
		userAndRoomList.add(roomList);
		return userAndRoomList;
	}

	@Override
	public boolean leaveRoom(User me, Room myRoom) throws RemoteException {
		return new RoomDAO().leaveRoom(me, myRoom);
	}

	@Override
	public ArrayList<Room> getRoom(User user) {
		return new RoomDAO().getRoom(user);
	}

	@Override
	public ArrayList<User> getRoomMember(int id) throws RemoteException {
		return new RoomDAO().getRoomMember(id);
	}

	@Override
	public boolean addMessage(Message mess) throws RemoteException {
		return new MessageDAO().addMessage(mess);
	}

	@Override
	public boolean createSingleRoom(User userAccept, User threadUserAccept) throws RemoteException{
		return new RoomDAO().createSingleRoom(userAccept, threadUserAccept);
	}

	@Override
	public boolean deleteFriend(User userCancel, User threadUser) {
		return new UserDAO().deleteFriend(userCancel, threadUser);
	}

	@Override
	public int getSingleRoom(User userSingleChat, User threadUser) throws RemoteException {
		return new RoomDAO().getSingleRoom(userSingleChat, threadUser);
	}

	@Override
	public List<Message> getSingleRoomMessage(int id) throws RemoteException {
		return new MessageDAO().getSingleRoomMessage(id);
	}

	@Override
	public ArrayList<Message> getRoomMessage(Room room) throws RemoteException {
		return new MessageDAO().getRoomMessage(room);
	}

	@Override
	public int createGroup(String name) throws RemoteException {
		return new RoomDAO().createGroup(name);
	}

	@Override
	public boolean addGroupMember(int group_id, ArrayList<User> groupList) throws RemoteException {
		return new RoomDAO().addGroupMember(group_id, groupList);
	}
    
}