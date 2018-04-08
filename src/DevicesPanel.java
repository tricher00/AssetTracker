import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.*;

public class DevicesPanel extends JPanel{
	public DevicesPanel(User currentUser, JPanel contentPane){
		setLayout(new BorderLayout());
        
		add(new Nav(currentUser, contentPane), BorderLayout.PAGE_START);
		JTable devTable;
		if (currentUser.permissionLevel.equals("admin")){
			Device[] devices = Utils.getAllDevices();
			String[] colnames = {"ID", "Assigned To", "Type", "Description"};
			
			int numDevices = devices.length;
			String[][] data = new String[numDevices][4];
			
			int i = 0;
			for (Device dev: devices){
		    	data[i][0] = dev.id;
		    	data[i][1] = dev.getOwnerName();
		    	data[i][2] = dev.type;
		    	data[i][3] = dev.description;
		    	i++;
		    }
			devTable = new JTable(data, colnames);
		}
		else{
		    int numUsers = currentUser.reporters.size() + 1;
		    Device[][] deviceArrs = new Device[numUsers][];
		    deviceArrs[0] = currentUser.getDevices();
		    User[] reporters = currentUser.getReporters();
		    for (int i = 1; i < numUsers; i++){
		    	deviceArrs[i] = reporters[i-1].getDevices();
		    }
		    String[] colnames = {"ID", "Assigned To", "Type", "Description"};
		    int numDevices = 0;
		    for (Device[] arr: deviceArrs){
		    	numDevices += arr.length;
		    }
		    String[][] data = new String[numDevices][4];
		    int i = 0;
		    for (Device[] arr: deviceArrs){
			    for (Device dev: arr){
			    	data[i][0] = dev.id;
			    	data[i][1] = dev.getOwnerName();
			    	data[i][2] = dev.type;
			    	data[i][3] = dev.description;
			    	i++;
			    }
		    }
		    devTable = new JTable(data, colnames);
		}
		//devTable.getModel().setValueAt(aValue, rowIndex, columnIndex);
	    devTable.setEnabled(false);
	    this.add(new JScrollPane(devTable), BorderLayout.CENTER);
	}
}
