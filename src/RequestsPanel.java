import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class RequestsPanel extends JPanel{
	public RequestsPanel(User currentUser, JPanel contentPane){
		setLayout(new BorderLayout());
        
		add(new Nav(currentUser, contentPane), BorderLayout.PAGE_START);
		JPanel tables = new JPanel();
		tables.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(10, 10, 10, 10);
        gc.gridx = 0;
        gc.gridy = 0;
        
		if (currentUser.requests.size() > 0){
			JLabel yourReqs = new JLabel("<html><h2>Your Requests</h2></html>");
			gc.gridwidth = 8;
			tables.add(yourReqs, gc);
			gc.gridwidth = 1;
			gc.gridy++;
			JLabel[] headers = {
				new JLabel("<html><h3>ID</h3></html>"),
				new JLabel("<html><h3>Type</h3></html>"),
				new JLabel("<html><h3>Description</h3></html>"), 
				new JLabel("<html><h3>Comments</h3></html>"), 
				new JLabel("<html><h3>Needed By</h3></html>"), 
				new JLabel("<html><h3>Status</h3></html>")
			};
			for (JLabel lab: headers){
				tables.add(lab, gc);
				gc.gridx++;
			}
			tables.revalidate();
			tables.repaint();
			for (int reqId: currentUser.requests){
				gc.gridy++;
				gc.gridx = 0;
				Request req = new Request(reqId);
				String status;
				if (req.approved == 0){
					status = "Pending";
				}
				else{
					status = "Approved";
				}
				JLabel[] row = {
					new JLabel(String.valueOf(reqId)),
					new JLabel(req.type),
					new JLabel(req.description),
					new JLabel(req.comments),
					new JLabel(req.neededBy),
					new JLabel(status)
				};
				for (JLabel cell: row){
					tables.add(cell, gc);
					gc.gridx++;
				}
			}
			gc.gridy++;
		}
		List<Integer> requests = new ArrayList<Integer>();
		for (String repId: currentUser.reporters){
			User rep = new User(repId);
			requests.addAll(rep.requests);
		}
		
		if (requests.size() > 0){
			JLabel pendingReqs = new JLabel("<html><h2>Requests Pending Your Approval</h2></html>");
			gc.gridx = 0;
			gc.gridwidth = 8;
			tables.add(pendingReqs, gc);
			gc.gridwidth = 1;
			gc.gridy++;
			JLabel[] headers = {
				new JLabel("<html><h3>ID</h3></html>"),
				new JLabel("<html><h3>User</h3></html>"),
				new JLabel("<html><h3>Type</h3></html>"),
				new JLabel("<html><h3>Description</h3></html>"), 
				new JLabel("<html><h3>Comments</h3></html>"), 
				new JLabel("<html><h3>Needed By</h3></html>"), 
			};
			for (JLabel lab: headers){
				tables.add(lab, gc);
				gc.gridx++;
			}
			for (int reqId: requests){
				gc.gridy++;
				gc.gridx = 0;
				Request req = new Request(reqId);
				User user = new User(req.userId);
				if (req.approved == 0){
					JLabel[] row = {
						new JLabel(String.valueOf(reqId)),
						new JLabel(user.first + " " + user.last),
						new JLabel(req.type),
						new JLabel(req.description),
						new JLabel(req.comments),
						new JLabel(req.neededBy),
					};
					for (JLabel cell: row){
						tables.add(cell, gc);
						gc.gridx++;
					}
					JButton approve = new JButton("Approve");
					JButton deny = new JButton("Deny");
					tables.add(approve, gc);
					gc.gridx++;
					tables.add(deny, gc);
					approve.addActionListener(new ActionListener() {
				        public void actionPerformed(ActionEvent e) {
				            req.Approve();
				            JPanel reqPanel = new RequestsPanel(currentUser, contentPane);
				    		contentPane.add(reqPanel, "Requests");
				    		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				            cardLayout.show(contentPane, "Requests");
				         }
			        });
					deny.addActionListener(new ActionListener() {
				        public void actionPerformed(ActionEvent e) {
				            req.Delete();
				            JPanel reqPanel = new RequestsPanel(currentUser, contentPane);
				    		contentPane.add(reqPanel, "Requests");
				    		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				            cardLayout.show(contentPane, "Requests");
				         }
			        });
				}
			}	
		}
		if (currentUser.permissionLevel.equals("admin")){
			Integer[] approvedRequests = Utils.getApprovedRequests();
			System.out.println(approvedRequests.length);
			if (true){
				JLabel approvedReqs = new JLabel("<html><h2>Approved Requests</h2></html>");
				gc.gridx = 0;
				gc.gridwidth = 8;
				tables.add(approvedReqs, gc);
				gc.gridwidth = 1;
				gc.gridy++;
				JLabel[] headers = {
					new JLabel("<html><h3>ID</h3></html>"),
					new JLabel("<html><h3>User</h3></html>"),
					new JLabel("<html><h3>Type</h3></html>"),
					new JLabel("<html><h3>Description</h3></html>"), 
					new JLabel("<html><h3>Comments</h3></html>"), 
					new JLabel("<html><h3>Needed By</h3></html>"), 
				};
				for (JLabel lab: headers){
					tables.add(lab, gc);
					gc.gridx++;
				}
				for (int reqId: approvedRequests){
					gc.gridy++;
					gc.gridx = 0;
					Request req = new Request(reqId);
					User user = new User(req.userId);
					JLabel[] row = {
						new JLabel(String.valueOf(reqId)),
						new JLabel(user.first + " " + user.last),
						new JLabel(req.type),
						new JLabel(req.description),
						new JLabel(req.comments),
						new JLabel(req.neededBy),
					};
					for (JLabel cell: row){
						tables.add(cell, gc);
						gc.gridx++;
					}
					JButton closeReq = new JButton("Close Request");
					gc.gridwidth = 2;
					tables.add(closeReq, gc);
					gc.gridwidth = 1;
					closeReq.addActionListener(new ActionListener() {
				        public void actionPerformed(ActionEvent e) {
				            req.Delete();
				            JPanel reqPanel = new RequestsPanel(currentUser, contentPane);
				    		contentPane.add(reqPanel, "Requests");
				    		CardLayout cardLayout = (CardLayout) contentPane.getLayout();
				            cardLayout.show(contentPane, "Requests");
				         }
			        });
				}	
			}
		}
		add(tables, BorderLayout.CENTER);
	}
}
