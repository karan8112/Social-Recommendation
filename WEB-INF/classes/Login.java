import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")

public class Login extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		HashMap<String, User> hm = new HashMap<String, User>();
		try {
			hm = MySQLDataStoreUtilities.selectUser();
		} catch (Exception e) {

		}
		User user = hm.get(username);
		if (user != null) {
			String user_password = user.getPassword();
			if (password.equals(user_password)) {
				HttpSession session = request.getSession(true);
				session.setAttribute("username", username);
				Cookie cookie = new Cookie("username", username);
				response.addCookie(cookie);
				session.setAttribute("userType", user.getUsertype());
				session.setAttribute("latitute", user.getLatlong());
				session.setAttribute("userid", user.getUserid());
				System.out.println("value set in session"+user.getLatlong());
				if(user.getUsertype().equals("user")) {
					response.sendRedirect("RecommendationHome");
				} else if(user.getUsertype().equals("admin")){
					response.sendRedirect("AdminHome");
				}
				return;
			}
		}
		displayLogin(request, response, pw, true);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayLogin(request, response, pw, false);
	}

	/*
	 * Login Screen is Displayed, Registered User specifies credentials and logins
	 * into the Game Speed Application.
	 */
	protected void displayLogin(HttpServletRequest request, HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		pw.print("<div class='post' style='width: 50%;margin-left: 250px;float: left'>");
		pw.print("<h2 class='title meta'><a style='font-size: 24px;color: #333;font-weight: bolder;font-size:30px'>Login</a></h2>"
				+ "<div class='entry'>"
				+ "<div style='width:400px; margin:25px; margin-left: auto;margin-right: auto;'>");
		if (error)
			pw.print("<h4 style='color:red'>Please check your username, password and user type!</h4>");
		HttpSession session = request.getSession(true);
		if (session.getAttribute("login_msg") != null) {
			pw.print("<h4 style='color:red'>" + session.getAttribute("login_msg") + "</h4>");
			session.removeAttribute("login_msg");
		}
		 pw.print("<form method='post' action='Login'>"
	                + "<table style='width:100%'><tr><td>"
	                + "<div class='login-text'>Username</div></td><td><input type='text' class='form-control' name='username' value='' class='input' required></input>"
	                + "</td></tr><tr><td style='padding: 5px;'></td></tr>"
	                + "<tr><td><div class='login-text'>Password</div></td><td><input class='form-control' type='password' name='password' value='' class='input' required></input>"
	                + "</td></tr><tr><td style='padding: 5px;'></td></tr>"
	                + "<tr><td colspan='2'><center><input style='font-size: 20px; width: 95%;' type='submit' class='btnbuy btn-cust-style' value='Login'></input></center>"
	                + "</td></tr><tr><td></td><td style='padding-top: 10px;'>"
	                + "<strong><a class='' href='Registration' style='float: right;height: 20px margin: 20px;color: black;'>New User? Register here!</a></strong>"
	                + "</td></tr></table>"
	                + "</form>" + "</div></div></div>");
		// utility.printHtml("Footer.html");
	}
}
