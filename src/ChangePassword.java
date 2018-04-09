import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.swing.*;

public class ChangePassword extends JPanel{
	public ChangePassword(User currentUser, JPanel contentPane){
		setLayout(new GridBagLayout());
	    		
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 1;
		gc.weighty = 1;
        gc.insets = new Insets(10, 10, 10, 10);
        
        JLabel currPassLabel = new JLabel("Current Password: ");
        JTextField currPass = new JPasswordField(40);    
        JLabel newPassLabel = new JLabel("New Password: ");
        JTextField newPassword = new JPasswordField(40);
        JLabel reenterLabel = new JLabel("Re-Enter Password: ");
        JTextField reEnter = new JPasswordField(40);
        JButton submit = new JButton("Submit");
        
        gc.gridx = 0;
        gc.gridy = 0;
        add(currPassLabel, gc);
        
        gc.gridx = 1;
        add(currPass, gc);
        
        gc.gridy = 1;
        add(newPassword, gc);
        
        gc.gridx = 0;
        add(newPassLabel, gc);
        
        gc.gridy = 2;
        add(reenterLabel, gc);
        
        gc.gridx = 1;
        add(reEnter, gc);

        gc.gridx = 1;
        gc.gridy = 3;
        add(submit, gc);
        
        submit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	String hash = Utils.hashPass(currentUser.salt, currPass.getText());
	        	if (hash.equals(currentUser.password)){
	        		if(newPassword.getText().equals(reEnter.getText())){
	        			Utils.updatePassword(currentUser, newPassword.getText());
	        			User updatedUser = new User(currentUser.email);
	        			JPanel dev = new DevicesPanel(updatedUser, contentPane);
	        			contentPane.add(dev, "Devices");
	        			CardLayout cardLayout = (CardLayout) contentPane.getLayout();
	        	        cardLayout.show(contentPane, "Devices");
	        		}
	        		else{
	        			JOptionPane.showMessageDialog(contentPane,
    	        		    "Passwords don't match.",
    	        		    "Passwords don't match",
    	        		    JOptionPane.ERROR_MESSAGE);
	        		}
	        	}
	        	else{
	        		JOptionPane.showMessageDialog(contentPane,
	        		    "Password is Incorrect.",
	        		    "Incorrect Password",
	        		    JOptionPane.ERROR_MESSAGE);
	        	}
	         }
        });
        
	}
}
