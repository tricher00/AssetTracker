import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class AddUser extends JPanel{
	public AddUser(User currentUser, JPanel contentPane){
		setLayout(new BorderLayout());
	    
		add(new Nav(currentUser, contentPane), BorderLayout.PAGE_START);
		
		JPanel form = new JPanel();
		form.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 1;
		gc.weighty = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 10, 10);
        
        JLabel firstLabel = new JLabel("First Name: ");
        JLabel lastLabel = new JLabel("Last Name: ");
        JLabel emailLabel = new JLabel("Email: ");
        JLabel phoneLabel = new JLabel("Phone: ");
        JLabel passLabel = new JLabel("Password: ");
        JLabel reenterLabel = new JLabel("Re-Enter Password: ");
        JLabel isAdminLabel = new JLabel("Is this User an Administrator? ");
        JLabel reportsLabel = new JLabel("Who does this User report to? ");
        
        JTextField first = new JTextField(40);
        JTextField last = new JTextField(40);
        JTextField email = new JTextField(40);
        JTextField phone = new JTextField(40);
        JTextField password = new JPasswordField(40);
        JTextField reEnter = new JPasswordField(40);
        JCheckBox isAdmin = new JCheckBox();
        JComboBox<String> reportsTo = new JComboBox<String>(Utils.getAllUsersNames());
        JButton submit = new JButton("Submit");
        
        gc.gridx = 0;
        gc.gridy = 0;
        form.add(firstLabel, gc);
        
        gc.gridx++;
        form.add(first,gc);
        
        gc.gridx++;
        form.add(lastLabel, gc);
        
        gc.gridx++;
        form.add(last, gc);
        
        gc.gridx = 0;
        gc.gridy = 1;
        form.add(emailLabel, gc);
        
        gc.gridx++;
        form.add(email,gc);
        
        gc.gridx++;
        form.add(phoneLabel, gc);
        
        gc.gridx++;
        form.add(phone, gc);
        
        gc.gridx = 0;
        gc.gridy = 2;
        form.add(passLabel, gc);
        
        gc.gridx++;
        form.add(password,gc);
        
        gc.gridx++;
        form.add(reenterLabel, gc);
        
        gc.gridx++;
        form.add(reEnter, gc);
        
        gc.gridx = 0;
        gc.gridy = 3;
        form.add(isAdminLabel, gc);
        
        gc.gridx++;
        form.add(isAdmin, gc);
        
        gc.gridy = 4;
        gc.gridx = 0;
        form.add(reportsLabel, gc);
        
        gc.gridx++;
        gc.gridwidth = 3;
        form.add(reportsTo, gc);
        
        form.setBorder(new EmptyBorder(10, 100, 10, 100));
        
        add(form, BorderLayout.CENTER);
        
        add(submit, BorderLayout.PAGE_END);
        
        submit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String firstInput = first.getText();
	            String lastInput = last.getText();
	            String emailInput = email.getText();
	            String phoneInput = phone.getText();
	            String passInput = password.getText();
	            String reenterInput = reEnter.getText();
	            String reportsInput = (String) reportsTo.getSelectedItem();
	            boolean isAdminInput = isAdmin.isSelected();
	            if (!passInput.equals(reenterInput)){
	            	JOptionPane.showMessageDialog(contentPane,
        		    "Passwords don't match.",
        		    "Passwords don't match",
        		    JOptionPane.WARNING_MESSAGE);
        			return;
	            }
	            String permLevel;
	            if (isAdminInput){
	            	permLevel = "admin";
	            }
	            else{
	            	permLevel = "standard";
	            }
	            String reporter;
	            if (reportsInput.equals("None")){
	            	reporter = "";
	            }
	            else{
	            	String[] arr = reportsInput.split(": ");
	            	reporter = arr[1];
	            }
	            Utils.insertUser(firstInput, lastInput, emailInput, passInput, phoneInput, permLevel, reporter);
	            JPanel dev = new DevicesPanel(currentUser, contentPane);
	    		contentPane.add(dev, "Devices");
	    		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
	            cardLayout.show(contentPane, "Devices");
	         }
        });
	}
}
