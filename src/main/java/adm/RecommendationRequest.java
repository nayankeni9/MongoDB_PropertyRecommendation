package adm;

import java.util.List;

import adm.data.Aggregation;
import adm.data.IndustryType;
import adm.data.InputData;
import adm.data.Literacy_Rate;
import adm.data.Population;
import adm.data.Real_Estate;
import adm.data.Transportation;
import adm.data.UserInput;

public class RecommendationRequest {
	
	Transportation transport=new Transportation();
    Population pop = new Population();
    IndustryType ind = new IndustryType();
    Literacy_Rate li = new Literacy_Rate();
    Real_Estate re = new Real_Estate();
    
    /*Method to process recommendations*/
	void processRecommendationData() throws InterruptedException{
		  InputData input = getDataFromUser();
		  
		  getRecommendationNewMethod(input); 
		  //getRecommendationOldMethod(input);
	}
	
	/*Method to get data from users and set it in POJO classes*/
	public InputData getDataFromUser() {
		  InputData input = new InputData();
	      UserInput user= new UserInput();
	      input.setIndustryName(user.convertToCamelCase(user.getIndustrytype()));
	      input.setPopulationRequirement(user.convertToCamelCase(user.getpopulationRequired()));
	      input.setTransportDensity(user.convertToCamelCase(user.getRequiredTransportDensity()));
	      input.setBudget(user.getRequiredBudget());
	      input.setLiteracyRate(user.convertToCamelCase(user.getRequiredLiteracyRate()));
	      return input;
	}
	
	/*Method 1 to get city recommendation list :: Currently not in use*/
	void getRecommendationNewMethod(InputData input){
		  List<String>  cityList = ind.getTopCities(input.getIndustryName());
	      cityList= li.getTopCities(input.getLiteracyRate(), cityList);
	      cityList= re.getTopCities(input.getBudget(), cityList);
	      cityList= pop.getTopCities(input.getPopulationRequirement(), cityList);
	      cityList = transport.getTopCities(input.getTransportDensity(), cityList);
	      System.out.println("Following are the recommended cities:");
	      for (String city: cityList) {
	    	  System.out.println(city);
	      }
	}	
	
	/*Method 2 to get city recommendation list ::  Currently in use*/
	void getRecommendationOldMethod(InputData input ) throws InterruptedException {
		Aggregation a = new Aggregation();
		a.performAggregationAndRecommendation(input);
	}
}
