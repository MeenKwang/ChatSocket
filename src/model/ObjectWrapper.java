package model;
 
import java.io.Serializable;
 
public class ObjectWrapper  implements Serializable{
    private static final long serialVersionUID = 20210811011L;
    public static final int LOGIN_USER = 1;
    public static final int REPLY_LOGIN_USER = 2;
    public static final int SERVER_INFORM_CLIENT_NUMBER = 7;
    public static final int GET_FRIEND = 8;
    public static final int REPLY_GET_FRIEND = 9;
    public static final int SEND_ANNOUNCE_OFFLINE = 10;
    public static final int RECEIVE_ANNOUNCE_OFFLINE = 11;
    public static final int ONLINE = 100;
    public static final int OFFLINE = 200;
    public static final int GET_FRIEND_REQUEST = 14;
	public static final int REPLY_GET_FRIEND_REQUEST = 15;
	public static final int DELETE_FRIEND_REQUEST = 16;
	public static final int ACCEPT_FRIEND_REQUEST = 17;
	public static final int GET_FRIEND_AND_ROOM = 18;
	public static final int REPLY_GET_FRIEND_AND_ROOM = 19;
	public static final int GET_ROOM_MESSAGE = 20;
	public static final int REPLY_GET_ROOM_MESSAGE = 21;
	public static final int GET_ROOM_USER_AND_MESSAGE = 22;
	public static final int REPLY_GET_ROOM_USER_AND_MESSAGE = 23;
	public static final int SEND_MESSAGE = 24;
	public static final int REPLY_SEND_MESSAGE = 25;
	public static final int SEARCH_USER_BY_NAME = 26;
	public static final int REPLY_SEARCH_USER = 27;
	public static final int SEND_REQUEST = 28;
	public static final int REPLY_SEND_REQUEST = 29;
	public static final int CHECK_FRIENDSHIP = 30;
	public static final int REPLY_CHECK_FRIENDSHIP = 31;
	public static final int REGISTER = 32;
	public static final int REPLY_REGISTER = 33;
	public static final int DELETE_FRIEND = 34;
	public static final int REPLY_DELETE_FRIEND = 35;
	public static final int GET_SINGLE_ROOM = 36;
	public static final int REPLY_GET_SINGLE_ROOM = 37;
	public static final int GET_SINGLE_ROOM_USER_AND_MESSAGE = 38;
	public static final int REPLY_GET_SINGLE_ROOM_USER_AND_MESSAGE = 39;
	public static final int SEND_SINGLE_MESSAGE = 40;
	public static final int REPLY_SEND_SINGLE_MESSAGE = 41;
	public static final int SEARCH_USER_BY_NAME_2 = 42;
	public static final int REPLY_SEARCH_USER_2 = 43;
	public static final int CREATE_GROUP = 44;
	public static final int REPLY_CREATE_GROUP = 45;
	
     
    private int performative;
    private Object data;
    public ObjectWrapper() {
        super();
    }
    public ObjectWrapper(int performative, Object data) {
        super();
        this.performative = performative;
        this.data = data;
    }
    public int getPerformative() {
        return performative;
    }
    public void setPerformative(int performative) {
        this.performative = performative;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }   
}