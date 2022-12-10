package tcp.server.control;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jdbc.dao.MessageDAO;
import jdbc.dao.UserDAO;
import jdbc.dao.RoomDAO;
import model.IPAddress;
import model.Message;
import model.ObjectWrapper;
import model.Room;
import model.User;
import rmi.general.MessageInterface;
import rmi.general.RoomInterface;
import rmi.general.UserInterface;
import tcp.server.control.ServerCtr.ServerProcessing;
import tcp.server.view.ServerMainFrm;

@SuppressWarnings("unused")
public class ServerCtr {
	private ServerMainFrm view;
	private ServerSocket myServer;
	private ServerListening myListening;
	private HashMap<Integer, ServerProcessing> userProcessList;
	private ArrayList<ServerProcessing> myProcess;
	private IPAddress myAddress = new IPAddress("localhost", 8888); // default server host and port
	//RMI
    private UserInterface userRO;
    private MessageInterface messageRO;
    private RoomInterface roomRO;
    private IPAddress serverRMIAddress = new IPAddress("localhost", 9999); //default server address
    private String rmiService = "rmiServer"; //default server service key

	public ServerCtr(ServerMainFrm view) {
		myProcess = new ArrayList<ServerProcessing>();
		userProcessList = new HashMap<>();
		this.view = view;
		openServer();
		initRMI();
	}

	public ServerCtr(ServerMainFrm view, int serverPort) {
		myProcess = new ArrayList<ServerProcessing>();
		userProcessList = new HashMap<>();
		this.view = view;
		myAddress.setPort(serverPort);
		openServer();
		initRMI();
	}

	private void openServer() {
		try {
			myServer = new ServerSocket(myAddress.getPort());
			myListening = new ServerListening();
			myListening.start();
			myAddress.setHost("10.144.197.8");
			view.showServerInfor(myAddress);
			// System.out.println("server started!");
			view.showMessage("TCP server is running at the port " + myAddress.getPort() + "...");
		} catch (Exception e) {
			e.printStackTrace();
			;
		}
	}

	@SuppressWarnings("deprecation")
	public void stopServer() {
		try {
			for (ServerProcessing sp : myProcess)
				sp.stop();
			myListening.stop();
			myServer.close();
			view.showMessage("TCP server is stopped!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public boolean initRMI(){
        try{
            // get the registry
            Registry registry = LocateRegistry.getRegistry(serverRMIAddress.getHost(), serverRMIAddress.getPort());
            // lookup the remote objects
            userRO = (UserInterface)(registry.lookup(rmiService));
            messageRO = (MessageInterface)(registry.lookup(rmiService));
            roomRO = (RoomInterface)(registry.lookup(rmiService));
            view.showMessage("Found the remote objects at the host: " + serverRMIAddress.getHost() + ", port: " + serverRMIAddress.getPort());
        }catch(Exception e){
            e.printStackTrace();
            view.showMessage("Error to lookup the remote objects!");
            return false;
        }
        return true;
    }

	public void publicClientNumber() {
		ObjectWrapper data = new ObjectWrapper(ObjectWrapper.SERVER_INFORM_CLIENT_NUMBER, myProcess.size());
		for (ServerProcessing sp : myProcess) {
			sp.sendData(data);
		}
	}

	/**
	 * The class to listen the connections from client, avoiding the blocking of
	 * accept connection
	 *
	 */
	class ServerListening extends Thread {

		public ServerListening() {
			super();
		}

		public void run() {
			view.showMessage("server is listening... ");
			try {
				while (true) {
					Socket clientSocket = myServer.accept();
					ServerProcessing sp = new ServerProcessing(clientSocket);
					sp.start();
					myProcess.add(sp);
					view.showMessage("Number of client connecting to the server: " + myProcess.size());
					publicClientNumber();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The class to treat the requirement from client
	 *
	 */
	class ServerProcessing extends Thread {
		private Socket mySocket;
		private User threadUser;
		
		// private ObjectInputStream ois;
		// private ObjectOutputStream oos;

		private User user = null;

		public ServerProcessing(Socket s) {
			super();
			mySocket = s;
		}

		public void sendData(Object obj) {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
				oos.writeObject(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void getThread(Object object, User user) {
	        for (ServerProcessing serverProcessing : myProcess) {
	            if (serverProcessing.threadUser.getId() == user.getId()) {
	                sendDataBack(object, serverProcessing);
	            }
	        }
		}
		
	    public void sendDataBack(Object object, ServerProcessing serverProcessing) {
	        try {
	        	ObjectOutputStream objectOutputStream = new ObjectOutputStream(serverProcessing.mySocket.getOutputStream());
	        	objectOutputStream.writeObject(object);
	        } catch (IOException e) {
	            System.out.println("I/O Error in UserThread: sendDataBack: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	    
	    //TCP function
		public void doLoginProcessing(ObjectOutputStream oos, ObjectWrapper data) throws IOException {
			User user = (User) data.getData();
			user = remoteCheckLogin(user);
			oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, user));

			for (ServerProcessing serverProcessing : myProcess) {
				if (serverProcessing.mySocket.equals(mySocket)) {
					serverProcessing.threadUser = user;
					userProcessList.put(user.getId(), serverProcessing);
				}
			}
		}
		
		public void announceStatusToFriend(int status) throws IOException {
			
			List<User> friendList = remoteGetFriends(threadUser);
			if (status == ObjectWrapper.ONLINE) {
				friendList.add(threadUser);
			}
			for (User friend : friendList) {
				if (userProcessList.containsKey(friend.getId())) {
					ObjectOutputStream obos = new ObjectOutputStream(
							userProcessList.get(friend.getId()).mySocket.getOutputStream());
					doReturnFriendList(obos, friend);
				}
			}
		}

		public void doReturnFriendList(ObjectOutputStream oos, User user) throws IOException {
			List<User> friendList = remoteGetFriends(user);
			for (User friend : friendList) {
				if (userProcessList.containsKey(friend.getId())) {
					friend.setStatus("Online");
				} else {
					friend.setStatus("Offline");
				}
			}
			List<Room> roomList = remoteGetRoom(threadUser);
			List<Object> objectList = new ArrayList<>();
			objectList.add(friendList);
			objectList.add(roomList);
			oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_FRIEND_AND_ROOM, objectList));
		}
		
		public void publicMessage(int id, String mess) throws IOException{
			ArrayList<User> roomMemberList = remoteGetRoomMember(id);
			for (ServerProcessing sp : myProcess) {
				for(User roomMember: roomMemberList) {
					if (sp.threadUser.getId() == roomMember.getId() && threadUser.getId() != roomMember.getId()) {
						sp.sendData(new ObjectWrapper(ObjectWrapper.REPLY_SEND_MESSAGE, mess));
					}
				}
			}
		}
		
		public void publicSingleMessage(int id, String mess, User userRecieved) throws IOException{
			for (ServerProcessing sp : myProcess) {
				if (sp.threadUser.getId() == userRecieved.getId()) {
					sp.sendData(new ObjectWrapper(ObjectWrapper.REPLY_SEND_SINGLE_MESSAGE, mess));
				}
			}
		}

		//RMI function
	    public User remoteCheckLogin(User user) {
	    	User userNull = new User();
	    	try {
	    		return userRO.checkLogin(user);
			} catch (Exception e) {
				e.printStackTrace();
				return userNull;
			}
	    }
	    
	    public boolean remoteRegisterUser(User user) {
	    	try {
				return userRO.register(user);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	    }
	    
	    public ArrayList<User> remoteGetFriendRequest(User user){
	    	try {
				return userRO.getFriendRequest(user);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	    }
	    
	    public boolean remoteAcceptRequest(User userAccept, User threadUserAccept) {
	    	try {
				return userRO.acceptRequest(userAccept, threadUserAccept);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	    }
	    
	    public boolean remoteCreateSingleRoom(User userAccept, User threadUserAccept) {
	    	try {
				return roomRO.createSingleRoom(userAccept, threadUserAccept);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	    }
	    
	    public boolean remoteDeleteRequest(User userAccept, User threadUserAccept) {
	    	try {
				return userRO.deleteRequest(userAccept, threadUserAccept);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	    }

		public ArrayList<User> remoteSearchUser(String text) {
			try {
				return userRO.searchUser(text);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		public boolean remoteAddRequest(User userAdd, User user) {
			try {
				return userRO.addRequest(userAdd, user);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		public boolean checkFriendShip(User user, User userAdd) {
			try {
				return userRO.checkFriendShip(user, userAdd);
			} catch (RemoteException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		public ArrayList<Object> remoteGetUserAndRoomList(User user){
			try {
				return userRO.getUserAndRoom(user);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		public boolean remoteLeaveRoom(User me, Room myRoom) {
			try {
				return roomRO.leaveRoom(me, myRoom);
			} catch (Exception e) {
				return false;
			}
		}
		
		public ArrayList<User> remoteGetFriends(User user) {
			try {
				return userRO.getFriends(user);
			} catch (Exception e) {
				return null;
			}
		}
		
		public ArrayList<Room> remoteGetRoom(User user){
			try {
				return roomRO.getRoom(user);
			} catch (Exception e) {
				return null;
			}
		}
		
		private ArrayList<User> remoteGetRoomMember(int id) {
			try {
				return roomRO.getRoomMember(id);
			} catch (Exception e) {
				return null;
			}
		}
		
		private ArrayList<Message> remoteGetRoomMessage(Room room) {
			try {
				return messageRO.getRoomMessage(room);
			} catch (Exception e) {
				return null;
			}
		}
		
		private boolean remoteAddMessage(Message mess) {
			try {
				return messageRO.addMessage(mess);
			} catch (Exception e) {
				return false;
			}
		}
		
		private boolean remoteDeleteFriend(User userCancel, User threadUser) {
			try {
				return userRO.deleteFriend(userCancel, threadUser);
			} catch (Exception e) {
				return false;
			}
		}
		
		private int remoteGetSingleRoom(User userSingleChat, User threadUser) {
			try {
				return roomRO.getSingleRoom(userSingleChat, threadUser);
			} catch (Exception e) {
				return 0;
			}
		}
		
		private List<Message> remoteGetSingleRoomMessage(int id) {
			try {
				return messageRO.getSingleRoomMessage(id);
			} catch (Exception e) {
				return null;
			}
		}
		
		private int remoteCreateGroup(String name) {
			try {
				return roomRO.createGroup(name);
			} catch (Exception e) {
				return 0;
			}
		}
		
		private boolean remoteAddGroupMember(int group_id, ArrayList<User> groupList) {
			try {
				return roomRO.addGroupMember(group_id, groupList);
			} catch (Exception e) {
				return false;
			}
		}
		
		@SuppressWarnings("deprecation")
		public void run() {
			try {
				while (true) {
					ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
					ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
					Object o = ois.readObject();
					if (o instanceof ObjectWrapper) {
						ObjectWrapper data = (ObjectWrapper) o;

						switch (data.getPerformative()) {
						case ObjectWrapper.LOGIN_USER:
							doLoginProcessing(oos, data);
							announceStatusToFriend(ObjectWrapper.ONLINE);
							break;
						case ObjectWrapper.REGISTER:
							User userRegister = (User) data.getData();
							boolean checkRegister = remoteRegisterUser(userRegister);
							if(checkRegister) oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REGISTER, checkRegister));
							break;
						case ObjectWrapper.GET_FRIEND_AND_ROOM:
							doReturnFriendList(oos, threadUser);
							break;
						case ObjectWrapper.SEND_ANNOUNCE_OFFLINE:
							System.out.println("user" + threadUser.getName() +  " offline");
							userProcessList.remove(threadUser.getId());
							announceStatusToFriend(ObjectWrapper.OFFLINE);
							break;
						case ObjectWrapper.GET_FRIEND_REQUEST:
							User userGetRequest = (User) data.getData();
							List<User> userRequestList = remoteGetFriendRequest(userGetRequest);
							oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_FRIEND_REQUEST, userRequestList));
							break;
						case ObjectWrapper.ACCEPT_FRIEND_REQUEST:
							User userAccept = (User) data.getData();
							boolean i = remoteAcceptRequest(userAccept, threadUser);
							boolean i1 = remoteCreateSingleRoom(userAccept, threadUser);
							List<User> userRequestList1 = remoteGetFriendRequest(threadUser);
							oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_FRIEND_REQUEST, userRequestList1));
							announceStatusToFriend(ObjectWrapper.ONLINE);
							break;
						case ObjectWrapper.DELETE_FRIEND_REQUEST:
							User userDelete = (User) data.getData();
							boolean j = remoteDeleteRequest(userDelete, threadUser);
							List<User> userRequestList2 = remoteGetFriendRequest(threadUser);
							oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_FRIEND_REQUEST, userRequestList2));
							break;
						case ObjectWrapper.SEARCH_USER_BY_NAME:
							String name = (String) data.getData();
							List<User> listSearch = remoteSearchUser(name);
							oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_USER, listSearch));
							break;
						case ObjectWrapper.SEARCH_USER_BY_NAME_2:
							String name2 = (String) data.getData();
							List<User> listSearch2 = remoteSearchUser(name2);
							oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_USER_2, listSearch2));
							break;
						case ObjectWrapper.SEND_REQUEST:
							User userSend = (User) data.getData();
							boolean checkSend = remoteAddRequest(userSend, threadUser);
							oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEND_REQUEST, checkSend));
							System.out.println(checkSend);
							break;
						case ObjectWrapper.GET_ROOM_USER_AND_MESSAGE:
							Room room = (Room) data.getData();
							List<User> roomUserList = remoteGetRoomMember(room.getId());
							List<Message> roomMessageList = remoteGetRoomMessage(room);
							List<Object> userAndMess = new ArrayList<>();
							userAndMess.add(roomUserList);
							userAndMess.add(roomMessageList);
							oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_ROOM_USER_AND_MESSAGE, userAndMess));
							break;
						case ObjectWrapper.SEND_MESSAGE:
							Message mess = (Message) data.getData();
							boolean check = remoteAddMessage(mess);
							String newMessage = threadUser.getName() + ": " + mess.getContent();
							oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEND_MESSAGE, newMessage));
							publicMessage(mess.getId_room(), newMessage);
							break;
						case ObjectWrapper.DELETE_FRIEND:
							User userCancel = (User) data.getData();
							boolean checkDeleteFriend = remoteDeleteFriend(userCancel, threadUser);
							oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_FRIEND, checkDeleteFriend));
							break;
						case ObjectWrapper.GET_SINGLE_ROOM:
							User userSingleChat = (User) data.getData();
							int roomId = remoteGetSingleRoom(userSingleChat, threadUser);
							oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_SINGLE_ROOM, roomId));
							break;
						case ObjectWrapper.GET_SINGLE_ROOM_USER_AND_MESSAGE:
							Room singleRoom = (Room) data.getData();
							List<Message> singleRoomMessageList = remoteGetSingleRoomMessage(singleRoom.getId());
							oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_SINGLE_ROOM_USER_AND_MESSAGE, singleRoomMessageList));
							break;
						case ObjectWrapper.SEND_SINGLE_MESSAGE:
							@SuppressWarnings("unchecked") 
							ArrayList<Object> objectList = (ArrayList<Object>) data.getData();
							Message singleMess = (Message) objectList.get(0);
							User userRecieved = (User) objectList.get(1);
							boolean checkSingle = remoteAddMessage(singleMess);
							String newSingleMessage = threadUser.getName() + ": " + singleMess.getContent();
							oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_SEND_SINGLE_MESSAGE, newSingleMessage));
							publicSingleMessage(singleMess.getId_room(), newSingleMessage, userRecieved);
							break;
						case ObjectWrapper.CREATE_GROUP:
							@SuppressWarnings("unchecked")
							ArrayList<Object> objectGroupList = (ArrayList<Object>) data.getData();
							User me = (User) objectGroupList.get(0);
							@SuppressWarnings("unchecked") 
							ArrayList<User> groupList = (ArrayList<User>) objectGroupList.get(1);
							groupList.add(me);
							String groupName = (String) objectGroupList.get(2);
							int cg1 = remoteCreateGroup(groupName);
							boolean cg2 = remoteAddGroupMember(cg1, groupList);
							oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_GROUP, cg2));
							break;
						}
					}
					// ois.reset();
					// oos.reset();
				}
			} catch (EOFException | SocketException e) {
				try {
					userProcessList.remove(threadUser.getId());
					announceStatusToFriend(ObjectWrapper.OFFLINE);
				} catch (IOException ex) {
					e.printStackTrace();
				}
				myProcess.remove(this);
				view.showMessage("Number of client connecting to the server: " + myProcess.size());
				publicClientNumber();
				try {
					mySocket.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				this.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}