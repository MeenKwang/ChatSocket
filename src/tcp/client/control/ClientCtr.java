package tcp.client.control;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import model.IPAddress;
import model.ObjectWrapper;
import tcp.client.view.LoginFrm;
import tcp.client.view.RegisterFrm;
import tcp.client.view.SingleChatFrm;
import tcp.client.view.AddFriendDialog;
import tcp.client.view.AddFriendFrm;
import tcp.client.view.ChatBoxFrm;
import tcp.client.view.ClientMainFrm;
import tcp.client.view.CreateGroupFrm;
import tcp.client.view.DeleteFriendDialog;
import tcp.client.view.FriendRequestFrm;
import tcp.client.view.HomeFrm;

public class ClientCtr {
	private Socket mySocket;
	private ClientMainFrm view;
	private ClientListening myListening; // thread to listen the data from the server
	private ArrayList<ObjectWrapper> myFunction; // list of active client functions
	private IPAddress serverAddress = new IPAddress("localhost", 8888); // default server host and port

	public ClientCtr(ClientMainFrm view) {
		super();
		this.view = view;
		myFunction = new ArrayList<ObjectWrapper>();
	}

	public ClientCtr(ClientMainFrm view, IPAddress serverAddr) {
		super();
		this.view = view;
		this.serverAddress = serverAddr;
		myFunction = new ArrayList<ObjectWrapper>();
	}

	public boolean openConnection() {
		try {
			mySocket = new Socket(serverAddress.getHost(), serverAddress.getPort());
			myListening = new ClientListening();
			myListening.start();
			view.showMessage("Connected to the server at host: " + serverAddress.getHost() + ", port: "
					+ serverAddress.getPort());
		} catch (Exception e) {
			// e.printStackTrace();
			view.showMessage("Error when connecting to the server!");
			return false;
		}
		return true;
	}

	public boolean sendData(Object obj) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
			oos.writeObject(obj);

		} catch (Exception e) {
			// e.printStackTrace();
			view.showMessage("Error when sending data to the server!");
			return false;
		}
		return true;
	}

	/*
	 * public Object receiveData(){ Object result = null; try { ObjectInputStream
	 * ois = new ObjectInputStream(mySocket.getInputStream()); result =
	 * ois.readObject(); } catch (Exception e) { //e.printStackTrace();
	 * view.showMessage("Error when receiving data from the server!"); return null;
	 * } return result; }
	 */

	@SuppressWarnings("deprecation")
	public boolean closeConnection() {
		try {
			if (myListening != null)
				myListening.stop();
			if (mySocket != null) {
				mySocket.close();
				view.showMessage("Disconnected from the server!");
			}
			myFunction.clear();
		} catch (Exception e) {
			// e.printStackTrace();
			view.showMessage("Error when disconnecting from the server!");
			return false;
		}
		return true;
	}

	public ArrayList<ObjectWrapper> getActiveFunction() {
		return myFunction;
	}

	class ClientListening extends Thread {

		public ClientListening() {
			super();
		}

		public void run() {
			try {
				while (true) {
					ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
					Object obj = ois.readObject();
					if (obj instanceof ObjectWrapper) {
						ObjectWrapper data = (ObjectWrapper) obj;
						if (data.getPerformative() == ObjectWrapper.SERVER_INFORM_CLIENT_NUMBER)
							view.showMessage("Number of client connecting to the server: " + data.getData());
						else {
							Iterator<ObjectWrapper> iterator = myFunction.iterator();
							while (iterator.hasNext()) {
								ObjectWrapper ow = iterator.next();
								if (ow.getPerformative() == data.getPerformative()) {
									switch (data.getPerformative()) {
									case ObjectWrapper.REPLY_LOGIN_USER:
										LoginFrm loginView = (LoginFrm) ow.getData();
										loginView.receivedDataProcessing(data);
										break;
									// Write new
									case ObjectWrapper.REPLY_REGISTER:
										RegisterFrm rv = (RegisterFrm) ow.getData();
										rv.recievedDataProcessing(data);
										break;
									case ObjectWrapper.REPLY_GET_FRIEND_REQUEST:
										FriendRequestFrm frf = (FriendRequestFrm) ow.getData();
										frf.receivedDataProcessing(data);
										break;
									case ObjectWrapper.REPLY_GET_FRIEND_AND_ROOM:
										HomeFrm homeFrm = (HomeFrm) ow.getData();
										homeFrm.receivedFriendAndRoomDataProcessing(data);
										break;
									case ObjectWrapper.REPLY_SEARCH_USER:
										AddFriendFrm afv = (AddFriendFrm) ow.getData();
										afv.receivedSearchUserProcessing(data);
										break;
									case ObjectWrapper.REPLY_SEARCH_USER_2:
										CreateGroupFrm cgv = (CreateGroupFrm) ow.getData();
										cgv.receivedSearchUserProcessing(data);
										break;
									case ObjectWrapper.REPLY_SEND_REQUEST:
										AddFriendDialog afd = (AddFriendDialog) ow.getData();
										afd.recievedDataProcessing(data);
										break;
									case ObjectWrapper.REPLY_GET_ROOM_USER_AND_MESSAGE:
										ChatBoxFrm chatBoxFrm1 = (ChatBoxFrm) ow.getData();
										chatBoxFrm1.receivedDataProcessing(data);
										break;
									case ObjectWrapper.REPLY_SEND_MESSAGE:
										ChatBoxFrm chatBoxFrm2 = (ChatBoxFrm) ow.getData();
										chatBoxFrm2.receivedData2Processing(data);
										break;
									case ObjectWrapper.REPLY_DELETE_FRIEND:
										DeleteFriendDialog dfd = (DeleteFriendDialog) ow.getData();
										dfd.recievedDataProcessing(data);
										break;
									case ObjectWrapper.REPLY_GET_SINGLE_ROOM:
										HomeFrm homeFrm2 = (HomeFrm) ow.getData();
										homeFrm2.receivedSingleRoomDataProcessing(data);
										break;
									case ObjectWrapper.REPLY_GET_SINGLE_ROOM_USER_AND_MESSAGE:
										SingleChatFrm scv = (SingleChatFrm) ow.getData();
										scv.receivedDataProcessing(data);
										break;
									case ObjectWrapper.REPLY_SEND_SINGLE_MESSAGE:
										SingleChatFrm scv2 = (SingleChatFrm) ow.getData();
										scv2.receivedData2Processing(data);
										break;
									case ObjectWrapper.REPLY_CREATE_GROUP:
										CreateGroupFrm cgv2 = (CreateGroupFrm) ow.getData();
										cgv2.receivedData2Processing(data);
										break;
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				view.showMessage("Error when receiving data from the server!");
				view.resetClient();
			}
		}
	}
}