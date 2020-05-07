import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@WebServlet("/Utilities")

/* 
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.
	  
*/

public class Utilities extends HttpServlet {
    HttpServletRequest req;
    PrintWriter pw;
    String url;
    HttpSession session;

    public Utilities(HttpServletRequest req, PrintWriter pw) {
        this.req = req;
        this.pw = pw;
        this.url = this.getFullURL();
        this.session = req.getSession(true);
    }



	/*  Printhtml Function gets the html file name as function Argument, 
		If the html file name is Header.html then It gets Username from session variables.
		Account ,Cart Information ang Logout Options are Displayed*/

    public void printHtml(String file) {
        String result = HtmlToString(file);
        //to print the right navigation in header of username cart and logout etc
        if (file.equals("Header.html")) {
            result = result + "<div id='menu' style='float: right;'><ul>";
            if (session.getAttribute("username") != null) {
                String username = session.getAttribute("username").toString();
                username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
                        result = result +"<li style='margin-top: 5px;'><a><span class='username-style'>Hello," + username+"</span></a></li>"
                                + "<li class='btnbuy btn-cust-style'><a href='Logout'><span>Logout</span><div id='latitude' style='display:none'>"+session.getAttribute("latitute")+"</div></a></li>";
            } else
                result = result + "<li class='btnbuy btn-cust-style' ><a href='Login' style=''>Login</a></li>";
            result = result + "</ul></div></div><div id='page'>";
            pw.print(result);
        } else
            pw.print(result);
    }

    public String getFullURL() {
        String scheme = req.getScheme();
        String serverName = req.getServerName();
        int serverPort = req.getServerPort();
        String contextPath = req.getContextPath();
        StringBuffer url = new StringBuffer();
        url.append(scheme).append("://").append(serverName);

        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath);
        url.append("/");
        return url.toString();
    }

    /*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
    public String HtmlToString(String file) {
        String result = null;
        try {
            String webPage = url + file;
            URL url = new URL(webPage);
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            result = sb.toString();
        } catch (Exception ignored) {
        }
        return result;
    }

    /*  logout Function removes the username , usertype attributes from the session variable*/

    public void logout() {
        session.invalidate();
    }

    /*  logout Function checks whether the user is loggedIn or Not*/

    public boolean isLoggedin() {
        return session.getAttribute("username") != null;
    }
    
    public String username(){
		if (session.getAttribute("username")!=null)
			return session.getAttribute("username").toString();
		return null;
	}
    
    
    public String storeReview(String name,String streetaddress,String reviewrating,String comment,
    		String internetRating, String userTotalRating, String photoURL,
    		String lat, String lng){
    	String message=MySQLDataStoreUtilities.insertReview(name,streetaddress,reviewrating,comment,
    			session.getAttribute("userid").toString(),
    			internetRating, userTotalRating, photoURL,
    			lat,lng);
    	if(!message.equals("Successfull"))
		{ return "UnSuccessfull";
		}
		else
		{
		HashMap<String, ArrayList<Review>> reviews= new HashMap<String, ArrayList<Review>>();
		try
		{
			reviews=MySQLDataStoreUtilities.selectReview(name);
		}
		catch(Exception e)
		{
			
		}
		if(reviews==null)
		{
			reviews = new HashMap<String, ArrayList<Review>>();
		}
			// if there exist product review already add it into same list for productname or create a new record with product name
			
		if(!reviews.containsKey(name)){	
			ArrayList<Review> arr = new ArrayList<Review>();
			reviews.put(name, arr);
		}
		ArrayList<Review> listReview = reviews.get(name);		
		Review review = new Review(name,streetaddress,reviewrating,comment,session.getAttribute("userid").toString());
		listReview.add(review);	
			
			// add Reviews into database
		
		return "Successfull";	
		}
	}

   
}
