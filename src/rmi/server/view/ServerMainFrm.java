package rmi.server.view;
 
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 
import model.IPAddress;
import rmi.server.control.ServerCtr;
 
public class ServerMainFrm extends JFrame implements ActionListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtServerHost, txtServerPort, txtServerService;
    private JButton btnStartServer, btnStopServer;  
    private JTextArea mainText;
    private ServerCtr myServer;
     
    public ServerMainFrm(){
        super("RMI server view");       
         
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
         
        JLabel lblTitle = new JLabel("Server RMI");
        lblTitle.setFont(new java.awt.Font("Dialog", 1, 20));
        lblTitle.setBounds(new Rectangle(150, 15, 200, 30));
        mainPanel.add(lblTitle, null);
         
        JLabel lblHost = new JLabel("Server host:");
        lblHost.setBounds(new Rectangle(10, 70, 150, 25));
        mainPanel.add(lblHost, null);
         
        txtServerHost = new JTextField(50);
        txtServerHost.setBounds(new Rectangle(170, 70, 150, 25));
        txtServerHost.setText("localhost");
        txtServerHost.setEditable(true);
        mainPanel.add(txtServerHost,null);
         
        JLabel lblPort = new JLabel("Server port:");
        lblPort.setBounds(new Rectangle(10, 100, 150, 25));
        mainPanel.add(lblPort, null);
         
        txtServerPort = new JTextField(50);
        txtServerPort.setBounds(new Rectangle(170, 100, 150, 25));
        mainPanel.add(txtServerPort,null);
         
        JLabel lblService = new JLabel("Service key:");
        lblService.setBounds(new Rectangle(10, 150, 150, 25));
        mainPanel.add(lblService, null);
         
        txtServerService = new JTextField(50);
        txtServerService.setBounds(new Rectangle(170, 150, 150, 25));
        mainPanel.add(txtServerService,null);
         
        btnStartServer = new JButton("Start server");
        btnStartServer.setBounds(new Rectangle(10, 200, 150, 25));
        btnStartServer.addActionListener(this);
        mainPanel.add(btnStartServer,null);
         
        btnStopServer = new JButton("Stop server");
        btnStopServer.setBounds(new Rectangle(170, 200, 150, 25));
        btnStopServer.addActionListener(this);
        btnStopServer.setEnabled(false);
        mainPanel.add(btnStopServer,null);
         
                 
        JScrollPane jScrollPane1 = new JScrollPane();
        mainText = new JTextArea("");
        jScrollPane1.setBounds(new Rectangle(10, 250, 610, 240));
        mainPanel.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(mainText, null);
         
        this.setContentPane(mainPanel);
        this.pack();    
        this.setSize(new Dimension(640, 480));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);      
        this.addWindowListener( new WindowAdapter(){
           public void windowClosing(WindowEvent e) {
               if(myServer != null)
                   try {
                       myServer.stop();
                   }catch(Exception ex) {
                       ex.printStackTrace();
                   }
             System.exit(0);
             }
          });
    }
 
    public void showServerInfo(IPAddress serverAddr, String service) {
        txtServerHost.setText(serverAddr.getHost());
        txtServerPort.setText(serverAddr.getPort()+"");
        txtServerService.setText(service);
    }
     
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() instanceof JButton) {
            JButton clicked = (JButton)ae.getSource();
            if(clicked.equals(btnStartServer)) {// start button
                try{
                    if(!txtServerPort.getText().isEmpty() && (txtServerPort.getText().trim().length() > 0) &&
                            !txtServerService.getText().isEmpty() && (txtServerService.getText().trim().length() > 0)) {//custom port
                        int port = Integer.parseInt(txtServerPort.getText().trim());
                        myServer = new ServerCtr(this, port, txtServerService.getText().trim());
                    }else {// default port
                        myServer = new ServerCtr(this);
                    }
                    myServer.start();
                    btnStopServer.setEnabled(true);
                    btnStartServer.setEnabled(false);
                }catch(Exception e) {
                    mainText.append("\n Error in starting the RIM server");
                    e.printStackTrace();
                }
            }else if(clicked.equals(btnStopServer)) {// stop button
                if(myServer != null) {
                    try {
                        myServer.stop();
                        myServer = null;
                    }catch(Exception e) {
                        mainText.append("\n Error in closing the RIM server");
                        e.printStackTrace();
                    }
                }               
                btnStopServer.setEnabled(false);
                btnStartServer.setEnabled(true);
                txtServerHost.setText("localhost");
            }
        }
    }
     
    public void showMessage(String s){
          mainText.append("\n"+s);
          mainText.setCaretPosition(mainText.getDocument().getLength());
    }
     
    public static void main(String[] args) {
        ServerMainFrm view = new ServerMainFrm();
        view.setVisible(true);
    }
}