package tcp.client.view;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import model.Message;
import model.ObjectWrapper;
import model.Room;
import model.User;
import tcp.client.control.ClientCtr;

public class SingleChatFrm extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JButton btnSendMess;
    private javax.swing.JTextArea displayMess;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtInputMess;
    private ClientCtr mySocket;
    private User userSend;
    private User userRecieve;
    private Room room;
    private ArrayList<Message> roomMessage;
    
    public SingleChatFrm(ClientCtr socket, User userSend, User userRecieve, Room room){
    	super("Single Chat");
    	this.mySocket = socket;
    	this.userSend = userSend;
    	this.userRecieve = userRecieve;
    	this.room = room;
        initial();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_SINGLE_ROOM_USER_AND_MESSAGE, room));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_GET_SINGLE_ROOM_USER_AND_MESSAGE, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_SEND_SINGLE_MESSAGE, this));
    }
    
    public void initial() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        displayMess = new javax.swing.JTextArea();
        btnSendMess = new javax.swing.JButton();
        txtInputMess = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Chat with user: " + userRecieve.getName());

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

        displayMess.setColumns(20);
        displayMess.setRows(5);
        jScrollPane2.setViewportView(displayMess);

        btnSendMess.setText("SEND");
        btnSendMess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendMessActionPerformed(evt);
            }

        });

        txtInputMess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInputMessActionPerformed(evt);
            }

			private void txtInputMessActionPerformed(ActionEvent evt) {
				
			}
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtInputMess)
                                .addGap(18, 18, 18)
                                .addComponent(btnSendMess, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 876, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(589, 589, 589)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtInputMess)
                            .addComponent(btnSendMess, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );

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
    
	private void btnSendMessActionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton) e.getSource();
        if (btnClicked.equals(btnSendMess)) {
            //pack the entity
            Message mess = new Message();
            String text = txtInputMess.getText().trim();
            if(!text.equals("")) {
                mess.setContent(text);
                mess.setUser(userSend);
                mess.setId_room(room.getId());
                Iterator<ObjectWrapper> iter = mySocket.getActiveFunction().iterator();
                while (iter.hasNext()) {
                    ObjectWrapper objectWrapper = iter.next();
                    if (objectWrapper.getData().equals(this)) {
                        iter.remove();
                    }
                }
                ArrayList<Object> objectList = new ArrayList<>();
                objectList.add(mess);
                objectList.add(userRecieve);
                mySocket.sendData(new ObjectWrapper(ObjectWrapper.SEND_SINGLE_MESSAGE, objectList));
                mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_SEND_SINGLE_MESSAGE, this));
            }
            txtInputMess.setText(null);
        }
	}

	@SuppressWarnings("unchecked")
	public void receivedDataProcessing(ObjectWrapper data) {
		roomMessage = (ArrayList<Message>)data.getData();
		displayMess.setText(null);
		for(Message s: roomMessage) {
			displayMess.append("\n" + getUserMessage(s).getName() + ": " + s.getContent());
			displayMess.setCaretPosition(displayMess.getDocument().getLength());
		}
	}
	
	public User getUserMessage(Message mess) {
		if(userSend.getId() == mess.getUser().getId()) {
			return userSend;
		} else if(userRecieve.getId() == mess.getUser().getId()) {
			return userRecieve;
		}
		return null;
	}

	public void receivedData2Processing(ObjectWrapper data) {
		String message = (String)data.getData();
		System.out.println("Tin nhan gui den la: " + message);
		displayMess.append("\n" + message);
		displayMess.setCaretPosition(displayMess.getDocument().getLength());
	}
}
