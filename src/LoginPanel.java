import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginPanel extends JPanel{

	public LoginPanel(JPanel contentPane){
		setLayout(new GridBagLayout());
	    
	    GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 10, 10);
	    
	    JLabel emailLabel = new JLabel("Email: ");
        JTextField email = new JTextField(40);    
        JLabel passLabel = new JLabel("Password: ");
        JTextField password = new JPasswordField(40);
        JButton submit = new JButton("Submit");
        
        gc.gridx = 0;
        gc.gridy = 0;
        add(emailLabel, gc);
        
        gc.gridx = 1;
        add(email, gc);
        
        gc.gridy = 1;
        add(password, gc);
        
        gc.gridx = 0;
        add(passLabel, gc);

        gc.gridx = 1;
        gc.gridy = 2;
        add(submit, gc);
        
        
        submit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            if(Utils.loginAuth(email.getText(), password.getText())){
	            	login(email.getText(), contentPane);
	            }
	            else{
	            	JOptionPane.showMessageDialog(contentPane,
	        		    "Email or Password is Incorrect.",
	        		    "Incorrect Email or Password",
	        		    JOptionPane.ERROR_MESSAGE);
	            }
	         }
        });
	}
	
	public void login(String email, JPanel contentPane){
		User user = new User(email);
		JPanel dev = new DevicesPanel(user, contentPane);
		contentPane.add(dev, "Devices");
		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
        cardLayout.show(contentPane, "Devices");
	}

}
