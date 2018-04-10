import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DeviceRequest extends JPanel{
	public DeviceRequest(User currentUser, JPanel contentPane){
		setLayout(new BorderLayout());
	    
		add(new Nav(currentUser, contentPane), BorderLayout.PAGE_START);
		
		JPanel form = new JPanel();
		form.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 1;
		gc.weighty = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 10, 10);
        
        JLabel typeLabel = new JLabel("Type: ");
        JTextField type = new JTextField(40);    
        JLabel descLabel = new JLabel("Description: ");
        JTextField description = new JTextField(40);
        JLabel dateLabel = new JLabel("Date Needed By(YYYY-MM-DD): ");
        JTextField neededBy = new JTextField(10);
        JLabel commentsLabel = new JLabel("Additional Comments");
        JTextArea comments = new JTextArea(5,500);        
        JButton submit = new JButton("Submit");
        
        gc.gridx = 0;
        gc.gridy = 0;
        form.add(typeLabel, gc);
        gc.gridy++;
        form.add(descLabel, gc);
        gc.gridy++;
        form.add(dateLabel, gc);
        gc.gridy++;
        form.add(commentsLabel, gc);
        
        gc.gridx = 1;
        gc.gridy = 0;
        form.add(type, gc);
        gc.gridy++;
        form.add(description, gc);
        gc.gridy++;
        form.add(neededBy, gc);
        gc.gridy++;
        form.add(comments, gc);
        
        form.setBorder(new EmptyBorder(10, 100, 10, 100));
        add(form, BorderLayout.CENTER);
        
        add(submit, BorderLayout.PAGE_END);
        
        submit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String typeInput = type.getText();
	            String descInput = description.getText();
	            String dateInput = neededBy.getText();
	            String commInput = comments.getText();
	            if (!(dateInput.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]"))){
	            	JOptionPane.showMessageDialog(contentPane,
        		    "Please enter a valid date.",
        		    "Invalid date",
        		    JOptionPane.WARNING_MESSAGE);
        			return;
	            }
	            Utils.insertRequest(currentUser.email, typeInput, descInput, commInput, dateInput);
	            JPanel dev = new DevicesPanel(currentUser, contentPane);
	    		contentPane.add(dev, "Devices");
	    		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
	            cardLayout.show(contentPane, "Devices");
	         }
        });
	}
}
