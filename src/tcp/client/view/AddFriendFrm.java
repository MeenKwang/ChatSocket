package tcp.client.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.ObjectWrapper;
import model.User;
import tcp.client.control.ClientCtr;

public class AddFriendFrm extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private ArrayList<User> listUser;
	private List<User> listFriend;
	private ClientCtr mySocket;
    private JTextField txtKey;
    private JButton btnSearch;
    private JTable tblResult;
    boolean checkFriendship = false;
    public AddFriendFrm(ClientCtr socket, User user, List<User> listFriend) {
    	super();
        this.user = user;
        mySocket = socket;
        listUser = new ArrayList<>();
        this.listFriend = listFriend;
        initial();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
    }
    private void initial() {
    	JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width-5, this.getSize().height-20);       
        pnMain.setLayout(new BoxLayout(pnMain,BoxLayout.Y_AXIS));
        pnMain.add(Box.createRigidArea(new Dimension(0,10)));
         
        JLabel lblHome = new JLabel("Search an user to add friend!");
        lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);  
        lblHome.setFont (lblHome.getFont ().deriveFont (20.0f));
        pnMain.add(lblHome);
        pnMain.add(Box.createRigidArea(new Dimension(0,20)));
         
        JPanel pn1 = new JPanel();
        pn1.setLayout(new BoxLayout(pn1,BoxLayout.X_AXIS));
        pn1.setSize(this.getSize().width-5, 20);
        pn1.add(new JLabel("Client name: "));
        txtKey = new JTextField();
        pn1.add(txtKey);
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(this);
        pn1.add(btnSearch);
        pnMain.add(pn1);
        pnMain.add(Box.createRigidArea(new Dimension(0,10)));
 
        JPanel pn2 = new JPanel();
        pn2.setLayout(new BoxLayout(pn2,BoxLayout.Y_AXIS));     
        tblResult = new JTable();
        JScrollPane scrollPane= new  JScrollPane(tblResult);
        tblResult.setFillsViewportHeight(false); 
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 250));
        tblResult.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = tblResult.getColumnModel().getColumnIndexAtX(e.getX()); // get the column of the button
                int row = e.getY() / tblResult.getRowHeight(); // get the row of the button
                // *Checking the row or column is valid or not
                if (row < tblResult.getRowCount() && row >= 0 && column < tblResult.getColumnCount() && column >= 0) {
                	User us = listUser.get(row);
                	for(User user: listFriend) {
                		if(us.getId() == user.getId()) {
                			checkFriendship = false;
                			break;
                		}
                	}
                	if(checkFriendship == false && (user.getId() != listUser.get(row).getId()) ) {
                		(new AddFriendDialog(mySocket, user, listUser.get(row))).setVisible(true);
                	}else if((user.getId() == listUser.get(row).getId())) {
                		JOptionPane.showMessageDialog(null, "Bạn không thể kết bạn với chính mình!", "Thông báo", row, null);	
                	}else {
                		JOptionPane.showMessageDialog(null, "Bạn đã gửi yêu cầu kết bạn tới người này hoặc người này đã là bạn bè của bạn", "Thông báo", row, null);
                	}
                }
            }
        });
 
        pn2.add(scrollPane);
        pnMain.add(pn2);    
        this.add(pnMain);
        this.setSize(600,300);              
        this.setLocation(200,10);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
	@Override
    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton)e.getSource();
        if(btnClicked.equals(btnSearch)){
            if((txtKey.getText() == null)||(txtKey.getText().length() == 0))
                return;
         
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.SEARCH_USER_BY_NAME, txtKey.getText()));
            mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_USER, this));
        }else {
            tblResult.setModel(null);
        }
    }
	@SuppressWarnings("unchecked")
	public void receivedSearchUserProcessing(ObjectWrapper data) {
		listUser = (ArrayList<User>) data.getData();
        if(listUser == null && data.getPerformative() == ObjectWrapper.REPLY_SEARCH_USER) {
            JOptionPane.showMessageDialog(this, "Error connecting to RMI server!");
            dispose();
        }

        String[] columnNames = {"Id", "Name"};
        String[][] value = new String[listUser.size()][columnNames.length];
        for(int i=0; i<listUser.size(); i++){
            value[i][0] = listUser.get(i).getId() +"";
            value[i][1] = listUser.get(i).getName();
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
        
	}
	public boolean receivedCheckFriendProcessing(ObjectWrapper data) {
		if(data.getPerformative() == ObjectWrapper.REPLY_CHECK_FRIENDSHIP) {
			checkFriendship = (boolean) data.getData();
		}
		return checkFriendship;
	}
}
