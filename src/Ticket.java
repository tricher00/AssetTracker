import java.sql.*;

public class Ticket {
	int id;
	String userId;
	String dateSubmitted;
	String device;	
	String comments;
	String status;
	
	public Ticket(int id){
		this.id = id;
		Connection conn = Utils.dbConn();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT Submitter, DateSubmitted, Device, Comments, Status FROM Ticket WHERE Id = " + id + ";");
			rs.next();
			this.userId = rs.getString(1);
			this.dateSubmitted = rs.getString(2);
			this.device = rs.getString(3);
			this.comments = rs.getString(4);
			this.status = rs.getString(5);
			Utils.dbClose(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			Utils.dbClose(conn);
		}
	}
	public void printTicket(){
		System.out.println("Id: " + this.id);
		System.out.println("Device: " + this.device);
		System.out.println("Comments: " + this.comments);
		System.out.println("Submitter: " + this.userId);		
	}
	public void Update(String status) {
		String query = "UPDATE Ticket SET Status = (?) WHERE Id = '" + id + "';";
		
		Connection con = Utils.dbConn();
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, status);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			Utils.dbClose(con);
		}
		Utils.dbClose(con);
		
	}
}
