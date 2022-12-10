package tcp.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.ObjectWrapper;
import model.User;
import tcp.client.control.ClientCtr;

public class CreateGroupFrm extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ClientCtr mySocket;
	private User me;
	private ArrayList<User> listUser;
	private ArrayList<User> listAdd;
	
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
	
    public CreateGroupFrm(ClientCtr socket, User me){
        super("Create Group View");
        this.me = me;
        mySocket = socket;
        listUser = new ArrayList<User>();
        listAdd = new ArrayList<User>();

        initial();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public void initial() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("              Search an user to add to your new group");

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);
        
        jTable1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = jTable1.getColumnModel().getColumnIndexAtX(e.getX()); // get the column of the button
                int row = e.getY() / jTable1.getRowHeight(); // get the row of the button
                // *Checking the row or column is valid or not
                if (row < jTable1.getRowCount() && row >= 0 && column < jTable1.getColumnCount() && column >= 0) {
                	if(listUser.get(row).getId() != me.getId()) {
                    	listAdd.add(listUser.get(row));
                    	addToAddList(listAdd);
                	}
                }
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("                   Add member to your new group");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable2);
        
        jTable2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = jTable2.getColumnModel().getColumnIndexAtX(e.getX()); // get the column of the button
                int row = e.getY() / jTable2.getRowHeight(); // get the row of the button
                // *Checking the row or column is valid or not
                if (row < jTable2.getRowCount() && row >= 0 && column < jTable2.getColumnCount() && column >= 0) {
                	User userRemove = listAdd.get(row);
                	System.out.println(userRemove);
                	listAdd.remove(userRemove);
                	addToAddList(listAdd);
                }
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setText("Create Group");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("                            Named your group");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jTextField1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2)
                            .addComponent(jTextField2)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(25, 25, 25)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(262, 262, 262)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jButton2)
                .addContainerGap())
        );

        pack();
    }
    
	protected void jButton1ActionPerformed(ActionEvent evt) {
        if((jTextField1.getText() == null)||(jTextField1.getText().length() == 0))
            return;
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.SEARCH_USER_BY_NAME_2, jTextField1.getText()));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_USER_2, this));
        jTextField1.setText(null);
	}

	protected void jButton2ActionPerformed(ActionEvent evt) {
        if((jTextField2.getText() == null)||(jTextField2.getText().length() == 0))
            return;
        ArrayList<Object> listObject = new ArrayList<>();
        listObject.add(me);
        listObject.add(listAdd);
        listObject.add(jTextField2.getText());
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.CREATE_GROUP, listObject));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_GROUP, this));
	}
	
	@SuppressWarnings("unchecked")
	public void receivedSearchUserProcessing(ObjectWrapper data) {
		listUser = (ArrayList<User>) data.getData();

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
        jTable1.setModel(tableModel);
        
	}
	
	public void addToAddList(ArrayList<User> listAdd) {
        String[] columnNames = {"Id", "Name"};
        String[][] value = new String[listAdd.size()][columnNames.length];
        for(int i=0; i<listAdd.size(); i++){
            value[i][0] = listAdd.get(i).getId() +"";
            value[i][1] = listAdd.get(i).getName();
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
        jTable2.setModel(tableModel);
	}

	public void receivedData2Processing(ObjectWrapper data) {
		boolean i = (boolean) data.getData();
		if(i) {
			JOptionPane.showMessageDialog(this, "Tạo phòng thành công!");
		} else {
			JOptionPane.showMessageDialog(this, "Tạo phòng thất bại!");
		}
	}

}
