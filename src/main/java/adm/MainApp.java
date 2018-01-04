/* 
 * @author - Team3
 * @version - 1.0
 * @since - 10/12/2017
 * This is the main class of the project and calls out to other classes
 * 
 */
package adm;
import java.io.BufferedReader;
import adm.data.Aggregation;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainApp {

	/*Main method to get recommendation and city details, contains a user driven menu
	 to select options*/
	public static void main(String[] args) throws InterruptedException, IOException {
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
	      RecommendationRequest rr = new RecommendationRequest();
	      CityDetailsRequest cdr = new CityDetailsRequest();
	      String s="";
	      while(!s.equals("q")) {
	    	  System.out.println("Hi Welcome to Office Location Recommender \n Enter your option \n 1 - Get city recommendation \n 2 - Get city details \n 3 - Compare Cities \n q - Quit");
		      try {
		    	  s = br.readLine();
		      } catch (IOException e) {
		    	  System.out.println("Enter valid option");
		      }
	    	  if(s.equals("1"))
	    		  rr.processRecommendationData();
	    	  else if(s.equals("2") || s.equals("3"))
	    		  cdr.processCityDetails(s);
	    	  
	    	  System.out.println();
	    	  System.out.println();
	      } 
	}
}
