import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User {
	String first;
	String last;
	String email;
	String password;
	String salt;
	String phone;
	String permissionLevel;
	String reportsTo;
	int hasLoggedIn;
	List<String> reporters = new ArrayList<String>();
	List<String> devices = new ArrayList<String>();
	List<Integer> requests = new ArrayList<Integer>();
	List<Integer> tickets = new ArrayList<Integer>();
	
	public User(String email){
		this.email = email;
		Connection conn = Utils.dbConn();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery(
				"SELECT FirstName, LastName, Password, Salt, Phone, PermissionLevel, ReportsTo, HasLoggedIn FROM User WHERE Email = '" + email + "';"
			);
			rs.next();
			this.first = rs.getString(1);
			this.last = rs.getString(2);
			this.password = rs.getString(3);
			this.salt = rs.getString(4);
			this.phone = rs.getString(5);
			this.permissionLevel = rs.getString(6);
			this.reportsTo = rs.getString(7);
			this.hasLoggedIn = rs.getInt(8);
			
			ResultSet rps=stmt.executeQuery(
					"SELECT Email FROM User WHERE ReportsTo = '" + email + "';"
			);
			while(rps.next()){
				reporters.add(rps.getString(1));
			}
			
			ResultSet dvs=stmt.executeQuery(
					"SELECT Id FROM Device WHERE AssignedTo = '" + email + "';"
			);
			
			while(dvs.next()){
				devices.add(dvs.getString(1));
			}
			
			ResultSet rqs=stmt.executeQuery(
					"SELECT Id FROM Request WHERE UserId = '" + email + "';"
			);
			while(rqs.next()){
				requests.add(rqs.getInt(1));
			}
			
			ResultSet tks=stmt.executeQuery(
					"SELECT Id FROM Ticket WHERE Submitter = '" + email + "';"
			);
			while(tks.next()){
				tickets.add(tks.getInt(1));
			}
			
			Utils.dbClose(conn);
			
			this.printUser();
		} catch (SQLException e) {
			e.printStackTrace();
			Utils.dbClose(conn);
		}
	}
	
	public void printUser(){
		System.out.println("Name:" + this.first + " " + this.last);
		System.out.println("Phone:" + this.phone);
		System.out.println("Permission Level: " + this.permissionLevel);
	}
	
	public Device[] getDevices(){
		int n = this.devices.size();
		Device[] arr = new Device[n];
		for (int i = 0; i < n; i++){
			arr[i] = new Device(this.devices.get(i));
		}
		return arr;
	}
	
	public User[] getReporters(){
		int n = this.reporters.size();
		User[] arr = new User[n];
		for (int i = 0; i < n; i++){
			arr[i] = new User(this.reporters.get(i));
		}
		return arr;
	}
}
