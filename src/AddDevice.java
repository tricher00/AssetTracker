import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class AddDevice extends JPanel{
	public AddDevice(User currentUser, JPanel contentPane){
		setLayout(new BorderLayout());
	    
		add(new Nav(currentUser, contentPane), BorderLayout.PAGE_START);
		
		JPanel form = new JPanel();
		form.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 1;
		gc.weighty = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 10, 10);
        
        JLabel idLabel = new JLabel("Id: ");
        JLabel typeLabel = new JLabel("Type: ");
        JLabel descLabel = new JLabel("Description: ");
        JLabel assignLabel = new JLabel("AssignedTo: ");
        
        JTextField id = new JTextField(40);
        JTextField type = new JTextField(40);
        JTextField desc = new JTextField(40);
        String[] users = Utils.getAllUsersNames();
        users[0] = "Inventory";
        JComboBox<String> assignedTo = new JComboBox<String>(users);
        JButton submit = new JButton("Submit");
        
        gc.gridx = 0;
        gc.gridy = 0;
        form.add(idLabel, gc);
        
        gc.gridx++;
        form.add(id,gc);
        
        gc.gridx++;
        form.add(typeLabel, gc);
        
        gc.gridx++;
        form.add(type, gc);
        
        gc.gridx = 0;
        gc.gridy = 1;
        form.add(descLabel, gc);
        
        gc.gridx++;
        form.add(desc,gc);
        
        gc.gridx++;
        form.add(assignLabel, gc);
        
        gc.gridx++;
        form.add(assignedTo, gc);
        
        form.setBorder(new EmptyBorder(10, 100, 10, 100));
        
        add(form, BorderLayout.CENTER);
        
        add(submit, BorderLayout.PAGE_END);
        
        submit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String idInput = id.getText();
	            String typeInput = type.getText();
	            String descInput = desc.getText();
	            String assignInput = (String) assignedTo.getSelectedItem();

	            String assign;
	            if (assignInput.equals("Inventory")){
	            	assign = "";
	            }
	            else{
	            	String[] arr = assignInput.split(": ");
	            	assign = arr[1];
	            }
	            Utils.insertDevice(idInput, typeInput, descInput, assign);
	            JPanel dev = new DevicesPanel(currentUser, contentPane);
	    		contentPane.add(dev, "Devices");
	    		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
	            cardLayout.show(contentPane, "Devices");
	         }
        });
	}
}