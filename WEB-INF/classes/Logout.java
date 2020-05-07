import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Logout")

public class Logout extends HttpServlet {

	/*
	logout Function of Utilities class is Called to Logout the User.
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilities utility = new Utilities(request, null);
		Cookie[] cookies = request.getCookies();
        //iterate each cookie
        for (Cookie cookie : cookies) {
            //display only the cookie with the name 'website'
            if (cookie.getName().equals("username")) {
                System.out.println(cookie.getValue());
                cookie.setValue("");
	            cookie.setMaxAge(0);
	            response.addCookie(cookie);
            }
        }
		utility.logout();
		response.sendRedirect("Login");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilities utility = new Utilities(request, null);
		 

		Cookie[] cookies = request.getCookies();
        //iterate each cookie
        for (Cookie cookie : cookies) {
            //display only the cookie with the name 'website'
            if (cookie.getName().equals("username")) {
                System.out.println(cookie.getValue());
                cookie.setValue("");
	            cookie.setMaxAge(0);
	            response.addCookie(cookie);
            }
        }
        
		// eraseCookie(request, response);
		utility.logout();
		response.sendRedirect("Login");
	}

}
