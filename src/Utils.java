import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class Utils {
	//Creates a connection to MySQL database
	public static Connection dbConn(){
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/assettracker","root","wit123");
			return con;
		}
		catch(Exception e){ 
			System.out.println(e);
			return null;
		}
	}
	
	//Closes connection to MySQL database
	public static void dbClose(Connection con) {
        try  {
            con.close();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }
	
	//Takes a salt and a string creates a hash using sha256
	public static String hashPass(String salt, String pass){
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			digest.update((salt + pass).getBytes());
			byte byteData[] = digest.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
			    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void insertUser(
		String first, String last, String email, String password, String phone, String permissionLevel, String reportsTo
	){
		String query = "INSERT INTO User (FirstName, LastName, Email, Password, Salt, Phone, PermissionLevel, ReportsTo) VALUES (?,?,?,?,?,?,?,?)";
		String hash;
		String salt;
		try{
			SecureRandom csprng = new SecureRandom();
			byte[] randomBytes = new byte[32];
			csprng.nextBytes(randomBytes);
			salt = new String(randomBytes, "latin1").substring(0,20);
			hash = hashPass(salt, password);
		}catch(UnsupportedEncodingException e1){
			e1.printStackTrace();
			return;
		}
				
		Connection con = dbConn();
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, first);
			ps.setString(2, last);
			ps.setString(3, email);
			ps.setString(4, hash);
			ps.setString(5, salt);
			ps.setString(6, phone);
			ps.setString(7, permissionLevel);
			if (reportsTo.equals("")){
				ps.setString(8, null);
			}
			else{
				ps.setString(8, reportsTo);
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			dbClose(con);
		}
		dbClose(con);
	}
	
	public static void insertDevice(String id, String type, String description, String assignedTo){
		String query = "INSERT INTO Device (Id, Type, Description, AssignedTo) VALUES (?,?,?,?)";
		
		Connection con = dbConn();
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, id);
			ps.setString(2, type);
			ps.setString(3, description);
			ps.setString(4, assignedTo);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			dbClose(con);
		}
		dbClose(con);
	}
	
	public static void insertRequest(String user, String type, String description, String comments, String neededBy){
		String query = "INSERT INTO Request (UserId, DeviceType, Description, Comments, NeededBy) VALUES (?,?,?,?,?)";
		
		Connection con = dbConn();
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, user);
			ps.setString(2, type);
			ps.setString(3, description);
			ps.setString(4, comments);
			ps.setString(5, neededBy);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			dbClose(con);
		}
		dbClose(con);
	}
	
	public static void insertTicket(String user, String device, String comments){
		String query = "INSERT INTO Ticket (Submitter, DateSubmitted, Device, Comments, Status) VALUES (?,?,?,?,?)";
		
		String date = LocalDateTime.now().toString();
		
		Connection con = dbConn();
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, user);
			ps.setString(2, date);
			if (device.equals("")){
				ps.setString(3, null);
			}
			else{
				ps.setString(3, device);
			}
			ps.setString(4, comments);
			ps.setString(5, "Pending");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			dbClose(con);
		}
		dbClose(con);
	}
	
	public static boolean loginAuth(String email, String password){
		String query = "SELECT password, salt FROM User WHERE Email = ?";
		
		Connection con = dbConn();
		
		try{
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, email);
			ResultSet result = ps.executeQuery();
			result.next();
			String hashed = result.getString(1);
			String salt = result.getString(2);
			dbClose(con);
			if (hashPass(salt, password).equals(hashed)){
				return true;
			}
			else{
				return false;
			}
		} catch (SQLException e){
			e.printStackTrace();
			dbClose(con);
			return false;
		}	
	}
	
	//Return array of Device objects for each device in the database
	public static Device[] getAllDevices(){
		Connection con = dbConn();
		String query = "SELECT Id FROM Device";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet result = ps.executeQuery();
			List<String> devices = new ArrayList<String>();
			while(result.next()){
				devices.add(result.getString(1));
			}
			int n = devices.size();
			Device[] devArr = new Device[n];
			
			for (int i = 0; i < n; i++){
				String id = devices.get(i);
				devArr[i] = new Device(id);
			}
			
			return devArr;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void updatePassword(User user, String newPass){
		Connection con = dbConn();
		String query = "UPDATE User SET Password = (?), HasLoggedIn = 1 WHERE Email = '" + user.email + "';";
		String password = hashPass(user.salt, newPass);
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, password);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			dbClose(con);
		}
		dbClose(con);
	}
	
	//Returns a list of Strings of all users first and last names
	//[0] will equal "None" and the default admin account is ignored
	public static String[] getAllUsersNames(){
		Connection con = dbConn();
		String query = "SELECT FirstName, LastName, Email FROM User";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet result = ps.executeQuery();
			List<String> users = new ArrayList<String>();
			users.add("None");
			while(result.next()){
				String first = result.getString(1);
				String last = result.getString(2);
				String email = result.getString(3);
				if (!(first + " " + last).equals("admin admin")){
					users.add(first + " " + last + ": " + email);
				}
			}
			String[] fullNames = new String[users.size()];
			fullNames = users.toArray(fullNames);
			return fullNames;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
