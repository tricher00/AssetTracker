import java.sql.*;

public class Device {
	String id;
	String type;
	String description;
	String assignedTo;
	public Device(String id){
		this.id = id;
		Connection conn = Utils.dbConn();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT Type, Description, AssignedTo FROM Device WHERE Id = '" + id + "';");
			rs.next();
			this.type = rs.getString(1);
			this.description = rs.getString(2);
			this.assignedTo = rs.getString(3);
			if (rs.wasNull()){
				this.assignedTo = "Inventory";
			}
			Utils.dbClose(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			Utils.dbClose(conn);
		}
		printDevice();
	}
	public void printDevice(){
		System.out.println("Id: " + this.id);
		System.out.println("Type: " + this.type);
		System.out.println("Description: " + this.description);
		System.out.println("Owner: " + this.assignedTo);		
	}
	
	public String getOwnerName(){
		if (this.assignedTo.equals("Inventory")){
			return "Inventory";
		}
		else{
			User owner = new User(this.assignedTo);
			return owner.first + " " + owner.last;
		}
	}
}
