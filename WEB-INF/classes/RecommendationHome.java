import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import java.io.*;

@WebServlet("/RecommendationHome")

public class RecommendationHome extends HttpServlet {
	private String error_msg;
	HttpSession session;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.session = request.getSession(true);
		if (session.getAttribute("username") == null) {
			Cookie[] cookies = request.getCookies();
			// iterate each cookie
			for (Cookie cookie : cookies) {
				// display only the cookie with the name 'website'
				if (cookie.getName().equals("username")) {
					System.out.println(cookie.getValue());
					cookie.setValue("");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}

		String value = request.getParameter("searchdata");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("Content.html");
		utility.printHtml("Footer.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String value = request.getParameter("searchdata");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String json = "[" + "{" + "'formatted_address' : 'test add',"
				+ "'icon' : 'https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png',"
				+ "'name' : 'TEst Name'," + "'rating' : '5'," + "'user_ratings_total' : 'user_ratings_total - 67' "
				+ "}, " + "{" + "'formatted_address' : 'test add1',"
				+ "'icon' : 'https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png',"
				+ "'name' : 'TEst Name1'," + "'rating' : '4'," + "'user_ratings_total' : 'user_ratings_total - 89' "
				+ "}" + "]";

		json = value;
		ArrayList<TextSearch> arrayListTextSearch = parseJsonTextSearchData(json);

		writeAjaxResponse(request, response, "Feedback received succesfully!", arrayListTextSearch);
	}

	protected void writeAjaxResponse(HttpServletRequest req, HttpServletResponse resp, String result,
			ArrayList<TextSearch> arrayListTextSearch) throws ServletException {
		PrintWriter writer = null;
		StringWriter stringWriter = new StringWriter();
		try {
			writer = resp.getWriter();

			PrintWriter pw = new PrintWriter(stringWriter);
			displayRegistration(req, resp, pw, false, arrayListTextSearch);

		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.println(stringWriter.toString());
		return;

	}

	protected ArrayList<TextSearch> parseJsonTextSearchData(String jsonResponse) {

		try {
			TextSearch[] data = new Gson().fromJson(jsonResponse, TextSearch[].class);
			ArrayList<TextSearch> arrayList = new ArrayList<TextSearch>();
			for (int i = 0; i < data.length; i++) {
				arrayList.add(data[i]);
			}

			return arrayList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	protected void displayRegistration(HttpServletRequest request, HttpServletResponse response, PrintWriter pw,
			boolean error, ArrayList<TextSearch> arrayListTextSearch) throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		if (error)
			pw.print("<h4 style='color:red'>" + error_msg + "</h4>");

		for (int i = 0; i < arrayListTextSearch.size(); i++) {

			pw.println("<div class='container'>");
			pw.println("<div class='post' style='widht:105%;'>	");
			pw.println(" <table id='place' style='width: 100%;'>");
			pw.println(" 	<tbody>");
			pw.println("  		<tr style='height: 300px;width: 100%; border: 1px solid #d4d4d4;'>");
			pw.println("  			<td style='width: auto;'>");
			pw.println("   				<div id='cardviewholder' class='row' style='height: 300px;width: 95%;'>");
			pw.println(
					"   					<div class='col-md-3 col-lg-3' id='photoholder' style='vertical-align: middle;margin-left: 20px;margin-top: 20px;float: left;'>");
			pw.println("                    <img style='width: 250px; height: 260px;' src='"
					+ arrayListTextSearch.get(i).getPhoto_url() + "'>");
			pw.println(" 					</div>");
			pw.println("      					<div class='col-md-5 col-lg-5'><div id='addresstag'>"
					+ "					" + arrayListTextSearch.get(i).getName() + " </div>");
			pw.println("      					<div id='completeaddress'>");
			pw.println("      						<div id='streetaddress'>"
					+ arrayListTextSearch.get(i).getFormatted_address() + "</div>");
			pw.println("       					<div id='ratingvalue'>" + "Rating : "
					+ arrayListTextSearch.get(i).getRating() + "</div>");

			pw.println("							<div id='map2'></div>	<div id='usertotalrating'>"
					+ "						Users Total Rating : " + arrayListTextSearch.get(i).getUser_ratings_total()
					+ " </div></div>  ");

			pw.print(
					"<div class='row'><div class='col-md-5 col-lg-5' id='viewreview'><form method='post' action='ViewReview'>"
							+ "<input type='hidden' name='name' value='" + arrayListTextSearch.get(i).getName() + "'>"
							+ "<input type='hidden' name='streetaddress' value='"
							+ arrayListTextSearch.get(i).getFormatted_address() + "'>"
							+ "<input type='hidden' name='rating' value='" + arrayListTextSearch.get(i).getRating()
							+ "'>" + "<input type='hidden' name='userstotalrating' value='"
							+ arrayListTextSearch.get(i).getUser_ratings_total() + "'>"
							+ "<input type='submit' value='View Review' class='btnreview' ></form></div>");

			pw.print("<div class='col-md-5 col-lg-5' id='writereview'><form method='post' action='WriteReview'>"
					+ "<input type='hidden' name='name' value='" + arrayListTextSearch.get(i).getName() + "'>"
					+ "<input type='hidden' id='streetaddress' name='streetaddress' value='"
					+ arrayListTextSearch.get(i).getFormatted_address() + "'>"
					+ "<input type='hidden' name='lat' id ='lat' value='" + arrayListTextSearch.get(i).getLat() + "'>"
					+ "<input type='hidden' name='lng' id ='lng' value='" + arrayListTextSearch.get(i).getLng() + "'>"
					+ "<input type='hidden' name='rating' value='" + arrayListTextSearch.get(i).getRating() + "'>"
					+ "<input type='hidden' name='userstotalrating' value='"
					+ arrayListTextSearch.get(i).getUser_ratings_total() + "'>"
					+ "<input type='hidden' name='photoUrl' value='" + arrayListTextSearch.get(i).getPhoto_url() + "'>"
					+ "<input type='submit' value='Write Review' class='btnreview' ></form></div></div>");

			pw.println("      			</div>");


			pw.println(
					"       				<div class='col-md-2 col-lg-2' id='maps' style='float:left;font-size: 16px;color: black;overflow-wrap:"
							+ " break-word;height: 35px; margin-top: 15px;'><iframe width='280'  height='270' frameborder='0' style='border:0' "
							+ "src='https://www.google.com/maps/embed/v1/place?key=AIzaSyAlEI_D1-GlYlaSLlMUPisFfrkrxIfmw-A&zoom=14;&q="
							+ arrayListTextSearch.get(i).getLat() + "," + arrayListTextSearch.get(i).getLng()
							+ "' allowfullscreen></iframe></div>");

			pw.println("      		</td>");
			pw.println("  		</tr>");
			pw.println(" 	</tbody>");
			pw.println(" </table>");
			pw.println("</div>");
			pw.println("</div>");
		}

		// utility.printHtml("Footer.html");

	}

}
