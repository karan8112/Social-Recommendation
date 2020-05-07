import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@WebServlet("/ViewReview")

public class ViewReview extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	        Utilities utility= new Utilities(request, pw);
		review(request, response);
	}
	protected void review(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try
            {           
            response.setContentType("text/html");
	PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request,pw);
	if(!utility.isLoggedin()){
		HttpSession session = request.getSession(true);				
		session.setAttribute("login_msg", "Please Login to view Review");
		response.sendRedirect("Login");
		return;
	}
	String Name=request.getParameter("name");		 
	HashMap<String, ArrayList<Review>> hm= MySQLDataStoreUtilities.selectReview(Name);
	String PlaceName = "";
	String StreetAddress = "";
	String reviewRating ="";
	String comment = "";	
	String userid ="";
    utility.printHtml("Header.html");
    pw.print("<div id=''><div class='post'><h2 class='title meta'>");
	pw.print("<a style='font-size: 24px;'>View Review</a>");
	pw.print("</h2><div class='entry'>");
		//if there are no reviews for product print no review else iterate over all the reviews using cursor and print the reviews in a table
	if(hm==null)
	{
	pw.println("<h2>Server is not up and running</h2>");
	}
	else
	{
        if(!hm.containsKey(Name)){
		pw.println("<h2>There are no reviews for this place.</h2>");
		}else{
			pw.println("<div class='addresstag'>" + request.getParameter("name") + "</div>");
			pw.println("<div class='streetaddress'>" + request.getParameter("streetaddress") + "</div>");
	for (Review r : hm.get(Name)) 
			 {		
		
		pw.print("<table width='100%' class='gridtable'>");
		pw.println("<br>");
			pw.print("<tr>");
			pw.print("<th width='40%'> Userid: </th>");
			userid = r.getUserid();
			pw.print("<td width='60%'>" +userid+ "</td>");
			pw.print("</tr>");
			pw.print("<tr>");
			pw.print("<th width='40%'> Review Rating: </th>");
			reviewRating = r.getReviewRating();
			pw.print("<td width='60%'>" +reviewRating+ "</td>");
			pw.print("</tr>");
			pw.print("<tr>");
			pw.print("<th width='40%'> Reviews: </th>");
			comment = r.getComment();
			pw.print("<td width='60%'>" +comment+ "</td>");
			pw.print("</tr>");
			pw.println("</table>");
			}					
						
		}       
            pw.print("</div></div></div>");		
	utility.printHtml("Footer.html");
                     	
                }
	
            }
        catch(Exception e) {
        	System.out.println(e.getMessage());
        }
	}
        
}