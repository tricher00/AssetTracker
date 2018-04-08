import java.sql.*;

public class Ticket {
	int id;
	String userId;
	String dateSubmitted;
	String device;	
	String comments;
	
	public Ticket(int id){
		this.id = id;
		Connection conn = Utils.dbConn();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT Submitter, DateSubmitted, Device, Comments FROM Ticket WHERE Id = " + id + ";");
			rs.next();
			this.userId = rs.getString(1);
			this.dateSubmitted = rs.getString(2);
			this.device = rs.getString(3);
			this.comments = rs.getString(4);
			Utils.dbClose(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			Utils.dbClose(conn);
		}
		printTicket();
	}
	public void printTicket(){
		System.out.println("Id: " + this.id);
		System.out.println("Device: " + this.device);
		System.out.println("Comments: " + this.comments);
		System.out.println("Submitter: " + this.userId);		
	}
}
