import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Nav extends JPanel{
	public Nav(User currentUser, JPanel contentPane){
		setLayout(new GridBagLayout());
	    
	    GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 10, 10);
        gc.gridy = 0;
        gc.gridx = 0;
        
        JButton devices = new JButton("Devices");
        JButton requests = new JButton("Requests");
        JButton tickets = new JButton("Tickets");
        JButton reqDevice = new JButton("Request Device");
        JButton submitTicket = new JButton("Submit Ticket");
        JButton addUser = new JButton("Add User");
        JButton addDevice = new JButton("Add Device");
        JButton editDevice = new JButton("Edit Device");
        JButton logout = new JButton("Logout");
        
        JButton[] adminBar = new JButton[9];
        adminBar[0] = devices;
        adminBar[1] = requests;
        adminBar[2] = tickets;
        adminBar[3] = reqDevice;
        adminBar[4] = submitTicket;
        adminBar[5] = addUser;
        adminBar[6] = addDevice;
        adminBar[7] = editDevice;
        adminBar[8] = logout;
        
        JButton[] standardBar = new JButton[6];
        standardBar[0] = devices;
        standardBar[1] = requests;
        standardBar[2] = tickets;
        standardBar[3] = reqDevice;
        standardBar[4] = submitTicket;
        standardBar[5] = logout;
        
		if (currentUser.permissionLevel.equals("admin")){
			for (JButton button: adminBar){
				add(button,gc);
				gc.gridx++;
			}
		}
		else{
			for (JButton button: standardBar){
				add(button,gc);
				gc.gridx++;
			}
		}
		
		devices.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	User user = new User(currentUser.email);
	    		JPanel dev = new DevicesPanel(user, contentPane);
	    		contentPane.add(dev, "Devices");
	    		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
	            cardLayout.show(contentPane, "Devices");
	         }
        });

		reqDevice.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	User user = new User(currentUser.email);
	    		JPanel devReq = new DeviceRequest(user, contentPane);
	    		contentPane.add(devReq, "Device Request");
	    		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
	            cardLayout.show(contentPane, "Device Request");
	         }
        });

		submitTicket.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	User user = new User(currentUser.email);
	    		JPanel subTicket = new SubmitTicket(user, contentPane);
	    		contentPane.add(subTicket, "Submit Ticket");
	    		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
	            cardLayout.show(contentPane, "Submit Ticket");
	         }
        });
		
		addUser.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	User user = new User(currentUser.email);
	    		JPanel addUserPanel = new AddUser(user, contentPane);
	    		contentPane.add(addUserPanel, "Add User");
	    		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
	            cardLayout.show(contentPane, "Add User");
	         }
        });
		
		logout.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	    		JPanel login = new LoginPanel(contentPane);
	    		contentPane.add(login, "Login");
	    		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
	            cardLayout.show(contentPane, "Login");
	         }
        });
	}
}
