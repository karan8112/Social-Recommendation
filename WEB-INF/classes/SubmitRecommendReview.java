	import java.io.IOException;
	import java.io.PrintWriter;
	import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import javax.servlet.http.HttpSession;
	
	@WebServlet("/SubmitRecommendReview")
	
	public class SubmitRecommendReview extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
		        Utilities utility= new Utilities(request, pw);
			storeReview(request, response);
		}
	protected void storeReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try
	        {
	        response.setContentType("text/html");
	PrintWriter pw = response.getWriter();
	        Utilities utility = new Utilities(request,pw);
	if(!utility.isLoggedin()){
		HttpSession session = request.getSession(true);				
		session.setAttribute("login_msg", "Please Login to add items to cart");
		response.sendRedirect("Login");
		return;
	}
	String Name=request.getParameter("Name");		
    String StreetAddress=request.getParameter("StreetAddress");
	String reviewrating=request.getParameter("reviewrating");
	String comment=request.getParameter("comment");
	String internetRating=request.getParameter("rating");
	String userstotalrating=request.getParameter("userstotalrating");
	String photoUrl=request.getParameter("photoUrl");
	String lat=request.getParameter("lat");
	String lng=request.getParameter("lng");
	String message=utility.storeReview(Name,StreetAddress,reviewrating,comment,internetRating,userstotalrating,photoUrl,lat,lng);	
	
   	System.out.println(message);	
	utility.printHtml("Header.html");
	if(message.equals("Successfull"))
  	pw.print("<div class='review-success-msg'>Review for &nbsp"+Name+" Stored </div>");
	else
	pw.print("<h2>Some Problem has occured </h2>");
    
	pw.print("</div></div></div>");		
	utility.printHtml("Footer.html");
}
	    catch(Exception e) {
	    	System.out.println(e.getMessage());
	    }
}
	}