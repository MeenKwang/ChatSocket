package tcp.client.view;
 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import model.ObjectWrapper;
import model.Room;
import model.User;
import tcp.client.control.ClientCtr;

public class HomeFrm extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
    private ArrayList<User> listFriend;
    private ArrayList<Room> listRoom;
    private ClientCtr mySocket;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenuGroup;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItemCreate;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblResult2;
    private javax.swing.JTable tblResult1;
    private int singleRoom_id;
    private Room singleRoom;
     
    public HomeFrm(ClientCtr socket, User user){
        super("Home View");
        this.user = user;
        mySocket = socket;
        listFriend = new ArrayList<User>();
        singleRoom = new Room(); 

        initial();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_GET_FRIEND_AND_ROOM, this));
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_FRIEND_AND_ROOM, user));
    }

    public void initial() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblResult1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResult2 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuGroup = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItemCreate = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	announceDisconnect();
                System.exit(0);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Home View");

        jLabel2.setText("Friends");

        tblResult1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblResult1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblResultMouseClicked1(evt);
            }
        });
        jScrollPane4.setViewportView(tblResult1);

        jLabel3.setText("Room");

        tblResult2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblResult2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblResultMouseClicked2(evt);
            }
        });
        jScrollPane1.setViewportView(tblResult2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(196, 196, 196)
                .addComponent(jLabel3)
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(46, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(291, 291, 291)))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 510, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(589, 589, 589)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );

        jMenu1.setText("User");

        jMenuItem1.setText("Friend request");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Add Friend");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);
        
        jMenuItem3.setText("Cancel Friendship");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);
        
        jMenuGroup.setText("Group");

        jMenuItemCreate.setText("Create group");
        jMenuItemCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	jMenuItemCreateActionPerformed(evt);
            }
        });
        jMenuGroup.add(jMenuItemCreate);
        
        jMenuBar1.add(jMenuGroup);
        
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 934, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }

	protected void jMenuItemCreateActionPerformed(ActionEvent evt) {
		CreateGroupFrm cgv = new CreateGroupFrm(mySocket, user);
		cgv.setVisible(true);
	}

	protected void tblResultMouseClicked1(java.awt.event.MouseEvent evt) {
		int column = tblResult1.getColumnModel().getColumnIndexAtX(evt.getX());
		int row = evt.getY() / tblResult2.getRowHeight();
        if (row < tblResult1.getRowCount() && row >= 0 && column < tblResult1.getColumnCount() && column >= 0) {
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_SINGLE_ROOM, listFriend.get(row)));
            mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_GET_SINGLE_ROOM, this));
            (new SingleChatFrm(mySocket, user, listFriend.get(row), singleRoom)).setVisible(true);
        }
	}

	private void tblResultMouseClicked2(java.awt.event.MouseEvent evt) {                                     
    	int column = tblResult2.getColumnModel().getColumnIndexAtX(evt.getX());
    	int row = evt.getY() / tblResult2.getRowHeight();
        if (row < tblResult2.getRowCount() && row >= 0 && column < tblResult2.getColumnCount() && column >= 0) {
            (new ChatBoxFrm(mySocket, user, listRoom.get(row))).setVisible(true);
        }
    }

	private void jMenuItem1ActionPerformed(ActionEvent evt) {                                           
		FriendRequestFrm frf = new FriendRequestFrm(mySocket, user);
		frf.setVisible(true);
    }  
	
	private void jMenuItem2ActionPerformed(ActionEvent evt) {                                           
		AddFriendFrm afv = new AddFriendFrm(mySocket, user, listFriend);
		afv.setVisible(true);
    }
    
	private void jMenuItem3ActionPerformed(ActionEvent evt) {
		DeleteFriendFrm dfv = new DeleteFriendFrm(mySocket, user, listFriend);
		dfv.setVisible(true);
	}
    /**
     * Treatment of search result received from the server
     * @param data
     */
    public void announceDisconnect() {
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.SEND_ANNOUNCE_OFFLINE, ""));
    }

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@SuppressWarnings("unchecked")
	public void receivedFriendAndRoomDataProcessing(ObjectWrapper data) {
        if(data.getData() instanceof ArrayList<?>) {
        	ArrayList<Object> listObject = (ArrayList<Object>) data.getData();
            if(listObject.get(0) instanceof ArrayList<?>) {
            	listFriend = (ArrayList<User>) listObject.get(0);
                listRoom = (ArrayList<Room>) listObject.get(1);

                String[] columnNames = {"Name", "Status"};
                String[][] value = new String[listFriend.size()][columnNames.length];
                for(int i=0; i<listFriend.size(); i++){
                    value[i][0] = listFriend.get(i).getName();
                    value[i][1] = listFriend.get(i).getStatus();
                }
                DefaultTableModel tableModel = new DefaultTableModel(value, columnNames) {
                    /**
    				 * 
    				 */
    				private static final long serialVersionUID = 1L;

    				@Override
                    public boolean isCellEditable(int row, int column) {
                       //unable to edit cells
                       return false;
                    }

                };
                tblResult1.setModel(tableModel);
                //ListRoom
                
                String[] columnNames2 = {"Id", "Name"};
                String[][] value2 = new String[listRoom.size()][columnNames2.length];
                for(int i=0; i<listRoom.size(); i++){
                    value2[i][0] = listRoom.get(i).getId() + "";
                    value2[i][1] = listRoom.get(i).getName();
                }
                DefaultTableModel tableModel2 = new DefaultTableModel(value2, columnNames2) {
                    /**
    				 * 
    				 */
    				private static final long serialVersionUID = 1L;

    				@Override
                    public boolean isCellEditable(int row, int column) {
                       //unable to edit cells
                       return false;
                    }

                };
                tblResult2.setModel(tableModel2);
                this.setVisible(true);	
            }

        }else {
            tblResult2.setModel(null);
        }
	}

	public void receivedSingleRoomDataProcessing(ObjectWrapper data) {
		singleRoom_id = (int) data.getData();
		System.out.println("Id phong don la: " + singleRoom_id);
		singleRoom.setId(singleRoom_id);
	}
}