import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class TicketPanel extends JPanel{
	public TicketPanel(User currentUser, JPanel contentPane){
		setLayout(new BorderLayout());
        
		add(new Nav(currentUser, contentPane), BorderLayout.PAGE_START);
		JPanel table = new JPanel();
		table.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 10, 10);
        gc.gridx = 0;
        gc.gridy = 0;
        
        if (currentUser.permissionLevel.equals("admin")){
        	String[] statuses = {
    			"Pending",
    			"In Progress",
    			"Completed"
        	};
        	String[] header = {
				"Id",
				"Date Submitted",
				"Submitted By",
				"Device Type",
				"Device Description",
				"Comments",
				"Status"
        	};
        	for (String lab : header){
        		table.add(new JLabel("<html><h2>"+lab+"</h2></html>"), gc);
        		gc.gridx++;
        	}
        	Ticket[] allTics = Utils.getAllTickets();
        	int numTics = allTics.length;
        	JComboBox<String>[] combos = new JComboBox[numTics];
        	Arrays.fill(combos, new JComboBox<String>(statuses));
        	for (int i = 0; i < numTics; i++){
        		Ticket tic = allTics[i];
        		gc.gridy++;
        		gc.gridx = 0;
        		
        		String type;
        		String description;
        		if (tic.device == null){
        			type = "Other";
        			description = "Other";
        		}
        		else{
        			Device dev = new Device(tic.device);
        			type = dev.type;
        			description = dev.description;
        		}
        		User user = new User(tic.userId);
        		String[] row = {
    				String.valueOf(tic.id),
    				tic.dateSubmitted,
    				user.first + " " + user.last,
    				type,
    				description,
    				tic.comments
        		};
        		for (String cell : row){
        			table.add(new JLabel(cell), gc);
        			gc.gridx++;
        		}
        		
        		JComboBox combo = new JComboBox<String>(statuses);//combos[i];
        		combo.setSelectedItem(tic.status);
        		table.add(combo, gc);
        		gc.gridx++;
        		JButton update = new JButton("Update");
        		table.add(update, gc);
        		update.addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent e) {
			            tic.Update((String)combo.getSelectedItem());
			            JPanel ticPanel = new TicketPanel(currentUser, contentPane);
			    		contentPane.add(ticPanel, "Tickets");
			    		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
			            cardLayout.show(contentPane, "Tickets");
			         }
		        });
        		
        	}
        }
        else{
        	String[] header = {
				"Id",
				"Date Submitted",
				"Device Type",
				"Device Description",
				"Comments",
				"Status"
        	};
        	for (String lab : header){
        		table.add(new JLabel("<html><h2>"+lab+"</h2></html>"), gc);
        		gc.gridx++;
        	}
        	for (int ticId : currentUser.tickets){
        		gc.gridy++;
        		gc.gridx = 0;
        		
        		Ticket tic = new Ticket(ticId);
        		String type;
        		String description;
        		if (tic.device == null){
        			type = "Other";
        			description = "Other";
        		}
        		else{
        			Device dev = new Device(tic.device);
        			type = dev.type;
        			description = dev.description;
        		}
        		String[] row = {
    				String.valueOf(ticId),
    				tic.dateSubmitted,
    				type,
    				description,
    				tic.comments,
    				tic.status
        		};
        		for (String cell : row){
        			table.add(new JLabel(cell), gc);
        			gc.gridx++;
        		}
        	}
        }
        add(table, BorderLayout.CENTER);
	}
}
