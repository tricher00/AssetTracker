import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SubmitTicket extends JPanel{
	public SubmitTicket(User currentUser, JPanel contentPane){
		setLayout(new BorderLayout());
	    
		add(new Nav(currentUser, contentPane), BorderLayout.PAGE_START);
		
		JPanel form = new JPanel();
		form.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 1;
		gc.weighty = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(0, 0, 0, 0);
        
        Device[] devices = currentUser.getDevices();
        int n = devices.length;
        
        String[] devDescriptions = new String[n + 1];
        for (int i = 0; i < n; i++){
        	Device dev = devices[i];
        	devDescriptions[i] = dev.id + ": " + dev.type + " - " + dev.description;
        }
        devDescriptions[n] = "Other";
        
        JLabel devChoiceLabel = new JLabel("Select Device: ");
        JComboBox<String> devChoices = new JComboBox<String>(devDescriptions);
        JLabel commentsLabel = new JLabel("Additional Comments: ");
        JTextArea comments = new JTextArea(5,500);
        JButton submit = new JButton("Submit");
        
        gc.gridx = 0;
        gc.gridy = 0;
        form.add(devChoiceLabel, gc);
        
        gc.gridy++;
        form.add(commentsLabel, gc);
        
        gc.gridx++;
        form.add(comments, gc);
        
        gc.gridy--;
        form.add(devChoices, gc);
        
        add(form, BorderLayout.LINE_START);
        
        add(submit, BorderLayout.PAGE_END);
        
        submit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String choice = (String) devChoices.getSelectedItem();
	            String commInput = comments.getText();
	            String id;
	            if (choice.equals("Other")){
	            	id = "";
	            }
	            else{
		            String[] dev = choice.split(":");
		            id = dev[0];
	            }
	            Utils.insertTicket(currentUser.email, id, commInput);
	            JPanel dev = new DevicesPanel(currentUser, contentPane);
	    		contentPane.add(dev, "Devices");
	    		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
	            cardLayout.show(contentPane, "Devices");
	         }
        });
	}
}
