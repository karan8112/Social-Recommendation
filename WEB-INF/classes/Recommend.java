import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/Recommend")

public class Recommend extends HttpServlet {

	  @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		  Utilities utility = new Utilities(request, null);
		  MySQLDataStoreUtilities sql = new MySQLDataStoreUtilities();
		  ArrayList<TextSearch> placesList = new ArrayList<TextSearch>(); 
	        try {
	        	HashMap<String,String> placeRecmMap = new HashMap<String,String>();
	        	placeRecmMap = readOutputFile();
	        	
	        	for(String user: placeRecmMap.keySet())
	    		{
	    			if(user.equals(utility.username())) {
	    				String products = placeRecmMap.get(user);
	    				products=products.replace("[","");
	    				products=products.replace("]","");
	    				products=products.replace("\"", " ");
	    				ArrayList<String> placesNameList = new ArrayList<String>(Arrays.asList(products.split(",")));
	    				
	    				for(String place : placesNameList){
	    					place= place.replace("'", "");
	    					TextSearch placeFetched = new TextSearch();
	    					placeFetched = sql.getPlaceByName(place.trim());
	    					placesList.add(placeFetched);
	    				}
	    			}
	    		}
	        	String placesJson = new Gson().toJson(placesList);
	            response.setContentType("application/JSON");
	            response.setCharacterEncoding("UTF-8");
	            response.getWriter().write(placesJson);

	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	        }

	    }
	  
	  public HashMap<String,String> readOutputFile(){

			String TOMCAT_HOME = System.getProperty("catalina.home");
	        BufferedReader br = null;
	        String line = "";
	        String cvsSplitBy = ",";
			HashMap<String,String> placesRecmMap = new HashMap<String,String>();
			try {
				MySQLDataStoreUtilities sql = new MySQLDataStoreUtilities();
				java.nio.file.Path path = null;
				if(sql.selectAppConfiguration().equals("surprise")) {
					path = java.nio.file.Paths.get(TOMCAT_HOME, "webapps", "SocialRecommendationSystem", "output.csv");
				}
				else {
					path = java.nio.file.Paths.get(TOMCAT_HOME, "webapps", "SocialRecommendationSystem", "output_recombee.csv");
				}
				
	            br = new BufferedReader(new FileReader(new File(path.toString())));
	            while ((line = br.readLine()) != null) {

	                // use comma as separator
	                String[] prod_recm = line.split(cvsSplitBy,2);
	                placesRecmMap.put(prod_recm[0],prod_recm[1]);
	            }
				
			} catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
			}
			
			return placesRecmMap;
		}
}
