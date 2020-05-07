import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class MySQLDataStoreUtilities {
	public static Connection conn = null;
	public String query = null;
	public static String message = "";

	public static String getConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SocialRecommendation?useUnicode=true&characterEncoding=utf-8", "root",
					"root");
			message = "Connection Successfull";
			return message;
		} catch (SQLException ex) {
			message = "NoConnection";
			return message;

		} catch (Exception e) {
			message = e.getMessage();
			return message;

		}
	}

	public static boolean insertUser(String username, String email, String preference, String password,
			String rePassword, String streetaddress, String cityname, String state, String zipcode, String country,
			String latlong, String userType) {
		try {
			HashMap<String, User> map_Insertuser = new HashMap<String, User>();
			getConnection();
			String insertIntoCustomerRegisterQuery = "INSERT INTO registration VALUES ("
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?);";
			String pattern = "MM-dd-yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			String date = simpleDateFormat.format(new Date());
			System.out.println(date);

			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setString(1, username);
			pst.setString(2, email);
			pst.setString(3, email);
			pst.setString(4, password);
			pst.setString(5, streetaddress);
			pst.setString(6, cityname);
			pst.setString(7, state);
			pst.setString(8, zipcode);
			pst.setString(9, country);
			pst.setString(10, preference);
			pst.setString(11, String.valueOf(date));
			pst.setString(12, latlong);
			pst.setString(13, userType);
			pst.execute();
			User user = new User(latlong);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

		return true;
	}

	public static HashMap<String, User> selectUser() {
		HashMap<String, User> hm = new HashMap<String, User>();
		try {
			getConnection();
			Statement stmt = conn.createStatement();
			String selectCustomerQuery = "select * from registration";
			ResultSet rs = stmt.executeQuery(selectCustomerQuery);
			while (rs.next()) {
				User user = new User(rs.getString("username"), rs.getString("password"), rs.getString("userType"),
						rs.getString("longlat"), rs.getString("userid"));
				hm.put(rs.getString("userid"), user);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return hm;
	}

	/* Nitika */
	public static String insertReview(String name, String streetaddress, String reviewrating, String comment,
			String userid, String internetRating, String userTotalRating, String photoURL, String lat, String lng) {
		try {
			HashMap<String, Review> hm = new HashMap<String, Review>();
			getConnection();
			String insertreviewquery = "INSERT INTO Review (name, streetaddress,reviewrating,comment,userid,"
					+ "internetRating,userTotalRating,photoURL,latitude, longitude)" + "VALUES( ?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement pst = conn.prepareStatement(insertreviewquery);
			pst.setString(1, name);
			pst.setString(2, streetaddress);
			pst.setString(3, reviewrating);
			pst.setString(4, comment);
			pst.setString(5, userid);
			pst.setString(6, internetRating);
			pst.setString(7, userTotalRating);
			pst.setString(8, photoURL);
			pst.setString(9, lat);
			pst.setString(10, lng);
			pst.execute();
			return "Successfull";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "UnSuccessfull";
		}
	}

	public static HashMap<String, ArrayList<Review>> selectReview(String Name) {
		HashMap<String, ArrayList<Review>> reviews = new HashMap<String, ArrayList<Review>>();
		;
		try {
			getConnection();
			Statement stmt = conn.createStatement();
			String selectReviewQuery = "select * from Review where name = '" + Name + "'";
			System.out.println("selectReviewQuery" + selectReviewQuery);
			ResultSet rs = stmt.executeQuery(selectReviewQuery);
			ArrayList<Review> listReview = new ArrayList<Review>();
			while (rs.next()) {

				Review review = new Review(rs.getString("Name"), rs.getString("streetaddress"),
						rs.getString("reviewrating"), rs.getString("comment"), rs.getString("userid"));
				listReview.add(review);
				reviews.put(rs.getString("Name"), listReview);

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return reviews;
	}
	
	
	public static void insertApplicationConfiguration(String source)
	{
		try
		{
			
			getConnection();
			PreparedStatement pstSelect = null;
			ResultSet rs = null;
			int size = 0;
			String selectQuery ="select count(*) as recordCount from configuration where recommendationsource = '"+ source +"'";
			pstSelect = conn.prepareStatement(selectQuery);
			rs = pstSelect.executeQuery();
			while(rs.next())
			{
				size = Integer.parseInt(rs.getString("recordCount"));
			}
			if(size == 0)
			{
				//Setting all the configuration flag to false
				String updateFlagQuery = "update configuration set activeflag='false' where 1=1;";
				PreparedStatement pst = conn.prepareStatement(updateFlagQuery);
				pst.execute();
				
				
				//Insert Record 
				String insertQuery = "INSERT INTO configuration(recommendationsource, activeflag) VALUES (?,?);";
				PreparedStatement pst1 = conn.prepareStatement(insertQuery);
				pst1.setString(1,source);
				pst1.setString(2,"true");
				pst1.execute();
			} 
			else {
				
				//Setting all the configuration flag to false
				String updateFlagQuery = "update configuration set activeflag='false' where 1=1;";
				PreparedStatement pst = conn.prepareStatement(updateFlagQuery);
				pst.execute();
				
				// Update Existing Record
				String updateQuery = "update configuration set activeflag=? where recommendationsource=? ;";
				PreparedStatement pst1 = conn.prepareStatement(updateQuery);
				pst1.setString(1,"true");
				pst1.setString(2,source);
				pst1.execute();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			}
			catch(Exception e) {
				
			}
		}
	}
	
	public static String selectAppConfiguration() {
		String source = "surprise";
		try {
			getConnection();
			Statement stmt = conn.createStatement();
			String selectCustomerQuery = "select * from configuration where activeflag = 'true';";
			ResultSet rs = stmt.executeQuery(selectCustomerQuery);
			while (rs.next()) {
				source = rs.getString("recommendationsource");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return source;
	}
	
	public static TextSearch getPlaceByName(String name){
		TextSearch place = new TextSearch();
		try 
		{
			String msg = getConnection();
			String selectProd="select * from review where name=? LIMIT 1;";
			PreparedStatement pst = conn.prepareStatement(selectProd);
			pst.setString(1,name);
			ResultSet rs = pst.executeQuery();
		
			while(rs.next())
			{	
				place = new TextSearch();
				place.setFormatted_address(rs.getString("streetaddress"));
				place.setIcon("");
				place.setName(rs.getString("name"));
				place.setRating(rs.getString("internetRating"));
				place.setUser_ratings_total(rs.getString("userTotalRating"));
				place.setPhoto_url(rs.getString("photoURL"));
				place.setLat(rs.getString("latitude"));
				place.setLng(rs.getString("longitude"));
				place.setGmap("");
				place.setPlaceId("");
			}
			rs.close();
			pst.close();
			conn.close();
		}
		catch(Exception e)
		{
		}
		return place;	
	}
	

}