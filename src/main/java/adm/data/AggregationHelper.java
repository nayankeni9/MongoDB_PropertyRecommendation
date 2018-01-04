package adm.data;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.DBObject;

public class AggregationHelper {
	/*Aggregation query with literacy rate collection*/
	public Document getAggregationLookup1(){
		return new Document("$lookup",
		        new Document("from", "Literacy_Rate_US")
                .append("localField", "AccentCity")
                .append("foreignField", "AccentCity")
                .append("as", "City"));
	}
	
	/*Aggregation query with Population collection*/
	public Document getAggregationLookup2(){
		return new Document("$lookup",
		        new Document("from", "uscitiespop")
		                .append("localField", "AccentCity")
		                .append("foreignField", "AccentCity")
		                .append("as", "AccCity"));
	}
	
	/*Aggregation query with Transportation collection*/
	public Document getAggregationLookup3() {
		return new Document("$lookup",
				new Document("from", "Transportation_US")
		                .append("localField", "AccentCity")
		                .append("foreignField", "City")
		                .append("as", "TransCity"));
	}
	
	/*Query for deciding output fields*/
	public Document getAggregationProject() {
		return new Document("$project",
				new Document("TransCity.Ease_of_Transport_Rank", 1)
				.append("AccCity.Population", 1)
				.append("City.Total", 1)
				.append("Rent_Rate", 1)
				.append("City.AccentCity", 1));
	}
	
	/*Query for getting city names from industry type*/
	public Document getAggregationMatch(InputData input) {
		IndustryType ind = new IndustryType();
		List<String>  cityList = ind.getTopCities(input.getIndustryName());
	
		return new Document("$match",
				new Document("AccentCity", new Document("$in",cityList)))  ;
	}
	
	/*Adding aggregation query to filters*/
	public List<Bson> addFilters(Bson lookup1, Bson match, Bson lookup2, Bson lookup3, Bson project) {
		List<Bson> filters = new ArrayList<>();
		filters.add(lookup1);
		filters.add(match);
		filters.add(lookup2);
		filters.add(lookup3);
		filters.add(project);
		
		return filters;
	}
	
	public Double getRentRate(DBObject o){
		return Double.parseDouble(o.get("Rent_Rate").toString()) ;
	}
	
	public Double getPopulation(DBObject o) {
		 String temp = (String) o.get("AccCity").toString();
	        Double population;
	        try
	        {
	        	population = Double.parseDouble(temp.substring(temp.lastIndexOf(GenericConstants.populationstr)+GenericConstants.populationstr.length()+4, 
	        			temp.indexOf('}')));
	        }catch(NumberFormatException e) {population = 0.0;}
	        return population;
	}
	
	public Double getTransport(DBObject o) {
		String temp2 = o.get("TransCity").toString();
        return Double.parseDouble(temp2.substring(temp2.lastIndexOf(GenericConstants.transportstr)+GenericConstants.transportstr.length()+4, 
        		temp2.indexOf('}')));
	}
	
	public Double getLiteracyRate(DBObject o) {
		String temp3 = o.get("City").toString();
        return Double.parseDouble(temp3.substring(temp3.lastIndexOf(GenericConstants.Total)+ GenericConstants.Total.length()+4, temp3.indexOf('}')));
	}
	
	public String getCityName(DBObject o) {
		String temp4 = o.get("City").toString();
        return temp4.substring(temp4.lastIndexOf(GenericConstants.citystr)+ GenericConstants.citystr.length()+4, temp4.indexOf(","));

	}
}
