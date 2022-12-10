package tcp.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ObjectWrapper;
import model.User;
import tcp.client.control.ClientCtr;

public class FriendRequestDialog extends javax.swing.JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User friendRequest;
    private ClientCtr mySocket;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    public FriendRequestDialog(ClientCtr socket, User friendRequest) {
    	super();
        this.friendRequest = friendRequest;
        mySocket = socket;
        initial();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

	private void initial() {
		// TODO Auto-generated method stub
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Are you sure you want to accept this request ?");

        jButton1.setText("Yes");

        jButton2.setText("No");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jButton1)
                .addGap(87, 87, 87)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(44, 44, 44))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );
        
        jButton1.addActionListener(this);
        jButton2.addActionListener(this);

        pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		javax.swing.JButton btnClicked = (javax.swing.JButton)e.getSource();
        if(btnClicked.equals(jButton1)){
        	mySocket.sendData(new ObjectWrapper(ObjectWrapper.ACCEPT_FRIEND_REQUEST, friendRequest));
        	this.dispose();
        	
        }
        if(btnClicked.equals(jButton2)){
             
            //send data to the server
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.DELETE_FRIEND_REQUEST, friendRequest));
            this.dispose();
            
        }
	}
}
