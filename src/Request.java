import java.sql.*;

public class Request {
	int id;
	String userId;
	String type;
	String description;
	String comments;
	String neededBy;
	int approved;
	
	public Request(int id){
		this.id = id;
		Connection conn = Utils.dbConn();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT UserId, DeviceType, Description, Comments, NeededBy, Approved FROM Request WHERE Id = " + id + ";");
			rs.next();
			this.userId = rs.getString(1);
			this.type = rs.getString(2);
			this.description = rs.getString(3);
			this.comments = rs.getString(4);
			this.neededBy = rs.getString(5);
			this.approved = rs.getInt(6);
			Utils.dbClose(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			Utils.dbClose(conn);
		}
		printRequest();
	}
	public void printRequest(){
		System.out.println("Id: " + this.id);
		System.out.println("Type: " + this.type);
		System.out.println("Description: " + this.description);
		System.out.println("Submitter: " + this.userId);		
	}
}
