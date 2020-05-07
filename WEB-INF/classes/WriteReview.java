import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/WriteReview")
public class WriteReview extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request,pw);
		review(request, response);
		}
	protected void review(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request,pw);
            if(!utility.isLoggedin()) {
            	HttpSession session = request.getSession(true);
            	session.setAttribute("login_msg", "Please Login to Write a Review");
            	response.sendRedirect("Login");
            	return;
            }
            	String Name = request.getParameter("name");
            	String StreetAddress = request.getParameter("streetaddress");
            	String Rating = request.getParameter("rating");
            	String UserTotalRating = request.getParameter("userstotalrating");
            	String photoUrl = request.getParameter("photoUrl");
            	String latitude = request.getParameter("lat");
            	String longitude = request.getParameter("lng");
            	utility.printHtml("Header.html");
            	//utility.printHtml("LeftNavigationBar.html");
            	pw.print("<form name = 'WriteReview' action='SubmitRecommendReview' method='post'>");
            	pw.print("<div id=''><div class='post'><h2 class='title meta'>");
        		pw.print("<a style='font-size: 24px;'>Write Review</a>");
        		pw.print("</h2><div class='entry'>");
                pw.print("<table width='100%' class='gridtable'>");
        		pw.print("<tr><th width='30%'>Name: </th><td>");
        		pw.print(Name);
        		pw.print("<input type='hidden' name='Name' value='"+Name+"'>");
        		pw.print("</td></tr>");
    	        pw.print("<tr><th width='30%'> Street Address:</th><td>");
                pw.print(StreetAddress);
                pw.print("<input type='hidden' name='StreetAddress' value='"+StreetAddress+"'>");
                pw.print("<input type='hidden' name='rating' value='"+Rating+"'>");
                pw.print("<input type='hidden' name='userstotalrating' value='"+UserTotalRating+"'>");
                pw.print("<input type='hidden' name='photoUrl' value='"+photoUrl+"'>");
                pw.print("<input type='hidden' name='lat' value='"+latitude+"'>");
                pw.print("<input type='hidden' name='lng' value='"+longitude+"'>");
                pw.print("</td></tr><table>");
                pw.print(" ");
                pw.print("<table><tr></tr><tr></tr><tr><td style='padding:10px;'> Review Rating: </td>");
				pw.print("<td>");
				pw.print("<select style='width: 30%;' name='reviewrating'>");
				pw.print("<option value='1' selected>1</option>");
				pw.print("<option value='2'>2</option>");
				pw.print("<option value='3'>3</option>");
				pw.print("<option value='4'>4</option>");
				pw.print("<option value='5'>5</option>");  
			pw.print("</td></tr>");
			pw.print("<tr>");
			pw.print("<td style='padding:10px;'> Comment: </td>");
			pw.print("<td><textarea name='comment' required rows='4' cols='50'></textarea></td></tr>");
		pw.print("<tr><td colspan='2'><input type='submit' class='btnbuy' name='SubmitReview' value='SubmitReview'></td></tr></table>");

        pw.print("</h2></div></div></div>");		
utility.printHtml("Footer.html");
            
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}