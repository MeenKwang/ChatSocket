package tcp.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import model.ObjectWrapper;
import model.User;
import tcp.client.control.ClientCtr;

public class FriendRequestFrm extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private User user;
    private List<User> listFriendRequest;
    private ClientCtr mySocket;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblResult;
    
    public FriendRequestFrm(ClientCtr socket, User user) {
    	super();
        this.user = user;
        mySocket = socket;
        listFriendRequest = new ArrayList<User>();
        initial();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_GET_FRIEND_REQUEST, this));
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_FRIEND_REQUEST, user));
    }

	private void initial() {
		// TODO Auto-generated method stub
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResult = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Friend Request");

        tblResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblResult.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblResultMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblResult);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(163, 163, 163)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(179, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
        );

        pack();
	}
	
    private void tblResultMouseClicked(java.awt.event.MouseEvent evt) {                                     
    	int column = tblResult.getColumnModel().getColumnIndexAtX(evt.getX());
    	int row = evt.getY() / tblResult.getRowHeight();
        if (row < tblResult.getRowCount() && row >= 0 && column < tblResult.getColumnCount() && column >= 0) {
            (new FriendRequestDialog(mySocket, listFriendRequest.get(row))).setVisible(true);
            System.out.println("User truyen toi dialog: " + listFriendRequest.get(row));
        }
    }
	
    @SuppressWarnings("unchecked")
	public void receivedDataProcessing(ObjectWrapper data) {
        if(data.getData() instanceof ArrayList<?>) {
            listFriendRequest = (ArrayList<User>)data.getData();
 
            String[] columnNames = {"Id", "Name"};
            String[][] value = new String[listFriendRequest.size()][columnNames.length];
            for(int i=0; i<listFriendRequest.size(); i++){
                value[i][0] = listFriendRequest.get(i).getId() +"";
                value[i][1] = listFriendRequest.get(i).getName();
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
            tblResult.setModel(tableModel);
        }else {
            tblResult.setModel(null);
        }
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
