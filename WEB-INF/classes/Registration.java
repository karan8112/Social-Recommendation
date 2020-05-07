import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Registration")

public class Registration extends HttpServlet {
	private String error_msg;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayRegistration(request, response, pw, false);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		String email = request.getParameter("email");
		String userType = request.getParameter("usertype");
		String streetaddress = request.getParameter("streetaddress");
		String aptno = request.getParameter("aptno");
		streetaddress= streetaddress.concat(" "+aptno);
		String cityname = request.getParameter("cityname");
		String state = request.getParameter("state");
		String zipcode = request.getParameter("zipcode");
		String country = request.getParameter("country");
		String latlong = request.getParameter("latlong");
		String preference = null;
		String usertype = "customer";
		if (!utility.isLoggedin())
			usertype = request.getParameter("email");

		if (!password.equals(repassword)) {
			error_msg = "Passwords doesn't match!";
		} else {

			HashMap<String, User> hm = new HashMap<String, User>();
			try {
				hm = MySQLDataStoreUtilities.selectUser();
			} catch (Exception e) {
			}
			if (hm.containsKey(userid))
				error_msg = "Username already exist as " + usertype;
			else {

				User user = new User(userid, password, usertype);
				hm.put(userid, user);
				if (MySQLDataStoreUtilities.insertUser(userid,   email,preference,password, repassword, streetaddress,
						cityname,state,zipcode,country,latlong,userType)) {
					HttpSession session = request.getSession(true);
					session.setAttribute("login_msg", "Your " + usertype + " account has been created. Please login");
					
					if (!utility.isLoggedin()) {
						response.sendRedirect("Login");
						return;
					} else {
						response.sendRedirect("Account");
						return;
					}
				}
			}
		}
		if (utility.isLoggedin()) {
			HttpSession session = request.getSession(true);
			session.setAttribute("login_msg", error_msg);
			response.sendRedirect("Account");
			return;
		}
		displayRegistration(request, response, pw, true);
	}

	protected void displayRegistration(HttpServletRequest request, HttpServletResponse response, PrintWriter pw,
			boolean error) throws ServletException, IOException {
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		pw.println("<script type='text/javascript' src='geocode.js'></script>");
		pw.print("<div class='post' style='float: left; width: 85%;margin-left: 78px;'>");
		pw.print("<h2 class='title meta' style='text-align: center;height: auto;'><a style='color: #333;font-weight: bolder; font-size: 30px;'>Registration</a></h2>" + "<div class='entry'>"
				+ "<div style='padding:20px;'>");
		if (error)
			pw.print("<h4 style='color:red'>" + error_msg + "</h4>");
		pw.println(
				"<script type='text/javascript' src ='geocode.js'>                                                                        ");
		pw.println("</script>                                                                     ");
		pw.print("<form method='post' action='Registration'>" + "<table style='width:100%'><tr><td>"
				+ "<div class='reg-form-text-label'>Userid</div></td><td><input type='text' name='userid' value='' class='form-control' required style='margin-bottom: 5px;'></input>"
				+ "</td></tr><tr><td>"
				+ "<div class='reg-form-text-label'>Password</div></td><td><input type='password' name='password' class='form-control' required style='margin-bottom: 5px;'></input>"
				+ "</td></tr><tr><td>"
				+ "<div class='reg-form-text-label'>Confirm Password</div></td><td><input type='password' name='repassword' value='' class='form-control' required style='margin-bottom: 5px;'></input>"
				+ "</td></tr><tr><td>"
				+ "<div class='reg-form-text-label'>Email(Username)</div></td><td><input type='text' name='email' value='' class='form-control' required style='margin-bottom: 5px;'></input>"
				+ "</td></tr><tr><td>"
				+ "<div class='reg-form-text-label'>User Type</div></td><td><select name='usertype' class='form-control' style='margin-bottom: 5px; width: 107%;'><option value='user' selected>User</option><option value='admin'>Admin</option></select>"
				+ "</td></tr><tr><td>"
				+ "<div class='reg-form-text-label'>Address</div></td><td style='padding-bottom: 5px;'><input id='autocomplete' placeholder='Enter your address'  onFocus='geolocate()' type='text' class='form-control'>"
				+ "</td></tr><tr><td>"
				+ "<div class='reg-form-text-label'>Street No</div></td><td style='padding-bottom: 5px'><input class='form-control' required id='street_number' name='streetaddress' disabled='true'>"
				+ "</td></tr><tr><td>"
				+ "<div class='reg-form-text-label'>Street</div></td><td style='padding-bottom: 5px'><input class='form-control' id='route' disabled='true' name ='aptno'>"
				+ "</td></tr><tr><td>"
				+ "<div class='reg-form-text-label'>City</div></td><td style='padding-bottom: 5px'><input class='form-control field' id='locality' name ='cityname' disabled='true'>"
				+ "</td></tr><tr><td>"
				+ "<div class='reg-form-text-label'>State</div></td><td style='padding-bottom: 5px'><input class='form-control' id='administrative_area_level_1' name ='state' disabled='true'>"
				+ "</td></tr><tr><td>"
				+ "<div class='reg-form-text-label'>Zipcode</div></td><td style='padding-bottom: 5px'><input class='form-control' id='postal_code' disabled='true' name ='zipcode'>"
				+ "</td></tr><tr><td>"
				+ "<div class='reg-form-text-label'>Country</div></td><td style='padding-bottom: 5px'><input class='form-control' id='country' disabled='true' name ='country'>"
				+ "</td></tr><tr><td>"
				+ "<div class='reg-form-text-label'>Latlong</div></td><td style='padding-bottom: 5px'><input class='form-control'  type = 'text' id='longlat' name ='latlong'>"
				+ "</td></tr><tr><td style='padding-top: 20px;padding-bottom: 10px;'>"
				+ "<div class='reg-form-text-label'>Preferance</div></td><td>"
				
				+ "</td></tr><tr><td style='font-size: 17px'>"
				+ "<input type='checkbox' style='margin-right: 10px;' name='amusement_park' value='amusement_park'>Amusement Park"
				+ "</td>"
				+ "<td style='font-size: 17px'><input type='checkbox' style='margin-right: 10px;' name='restaurant' value='restaurant'>Restaurant"
				+ "</td>"
				+ "<td style='font-size: 17px'><input type='checkbox' style='margin-right: 10px;' name='cafe' value='cafe'>Cafe"
				+ "</td></tr><tr>"
				+ "<td style='font-size: 17px'><input type='checkbox' style='margin-right: 10px;' name='shoping_mall' value='shoping_mall'>Shoping Mall"
				+ "</td>"
				+ "<td style='font-size: 17px'><input type='checkbox' style='margin-right: 10px;' name='tourist_attraction' value='tourist_attraction'>Tourist Attraction"
				+ "</td>"
				+ "<td style='font-size: 17px'><input type='checkbox' style='margin-right: 10px;' name='bar' value='bar'>Bar"
				+ "</td></tr><tr>"
				+ "<td style='font-size: 17px'><input type='checkbox' style='margin-right: 10px;' name='nightclub' value='nightclub'>Night Club"
				+ "</td>"
				+ "<td style='font-size: 17px'><input type='checkbox' style='margin-right: 10px;' name='museum' value='museum'>Museum"
				+ "</td></tr></table>"
				+ "<div style='margin-top: 20px;'><input type='submit' class='btnbuy btn-cust-style' style='float:none;' name='ByUser' value='Create User'></input></div>");
		pw.println("<script type='text/javascript' src='geocode.js'></script>");
		
		pw.println(
				"<script src='https://maps.googleapis.com/maps/api/js?key=AIzaSyAbgbsd1R1T4yzOzyJrp5uC3YTy1jIWgHg&libraries=places&callback=initAutocomplete' async defer></script> ");
		pw.println("</form>" + "</div></div></div>");
		utility.printHtml("Footer.html");
	}
}
