package adm.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import adm.MongoDBDataLoader;

public class Aggregation {
	MongoDBDataLoader mdb = new MongoDBDataLoader();
	MongoClient mongoClient = mdb.setDatabase();
	MongoDatabase db = mongoClient.getDatabase(GenericConstants.dbName);
	AggregationHelper aggHelp = new AggregationHelper();
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	Average avg = new Average();
	StandardDeviation std = new StandardDeviation();
	
	Double rentAvg =  avg.getRentAvg(db);
    Double popAvg =  avg.getPopAvg(db);
    Double liRateAvg =   avg.getLitRateAvg(db);
    Double transportAvg =  avg.geTransportAvg(db);
    
    Double rentStdDev =  std.getRentStdDev(db);
    Double popStdDev =  std.getPopStdDev(db);
    Double liRateStdDev = std.getLitRateStdDev(db);
    Double transportStdDev = std.geTransportStdDev(db);
	
	/*Main function to perform aggregation tasks and provide recommendation*/
	public void performAggregationAndRecommendation(InputData input) throws InterruptedException
	{
		AggregateIterable<Document> it = db.getCollection("Real_Estate_US").aggregate(aggHelp.addFilters(aggHelp.getAggregationLookup1(),
																						aggHelp.getAggregationMatch(input), 
																						aggHelp.getAggregationLookup2(),
																						aggHelp.getAggregationLookup3(), 
																						aggHelp.getAggregationProject()));	
		insertIntoTempDatabase(it);	
		Thread.sleep(3000);		
		DBCursor doc = getTempView(); 
		while(doc.hasNext()) {
			DBObject o = doc.next();	      
	        Double rent_norm = standardize(aggHelp.getRentRate(o), "$Rent_Rate", GenericConstants.realEstateCollection, rentAvg, rentStdDev);
	        Double pop_norm = standardize(aggHelp.getPopulation(o), "$Population", GenericConstants.populationCollection,popAvg, popStdDev);
	        Double lr_norm = standardize(aggHelp.getLiteracyRate(o), "$Total", GenericConstants.literacyCollection, liRateAvg, liRateStdDev);
	        Double trans_norm = standardize(aggHelp.getTransport(o), "$Ease_of_Transport_Rank", GenericConstants.transportCollection, transportAvg, transportStdDev);
	        Double final_score = rent_norm*(getWeightsForRent(input.getBudget()))+pop_norm*(getWeights(input.getPopulationRequirement()))
	        		+lr_norm*(getWeights(input.getLiteracyRate()))+trans_norm * (getWeights(input.getTransportDensity()));
	        
	        Map<String, String> m = new HashMap<String, String>();
	        m.put("City", aggHelp.getCityName(o));
	        m.put("Score", final_score.toString());
	        list.add(m);
		}
		sortList();
		printRecommendations();
	}
	
	/*Calculate average for the parameters*/
	public Double getAverage(String parameter, String collection_name)
	{
		Double result=(double) 0;
		Bson avg = new Document("$group", new Document("_id",null).append("Mean", new Document("$avg", parameter)));
		List<Bson> average = new ArrayList<>();
		average.add(avg);
		Document project = new Document("$project",
				new Document("_id",0)
				.append("Mean", 1));
		
		average.add(project);
		AggregateIterable<Document> it2 = db.getCollection(collection_name).aggregate(average);
		
		for (Document row : it2) {
		    result = Double.parseDouble(row.toJson().substring(row.toJson().lastIndexOf(':')+2,row.toJson().length()-3));
		}
		
		return result;
	}
	/*Calculate standard deviation different parameters*/
	public Double getStandardDeviation(String parameter, String collection_name)
	{
		Double result = (double) 0;
		Bson stddev = new Document("$group", new Document("_id",null).append("StdDev", new Document("$stdDevPop", parameter)));
		List<Bson> sd = new ArrayList<>();
		sd.add(stddev);
		Document project = new Document("$project",
				new Document("_id",0)
				.append("StdDev", 1));
		sd.add(project);
		AggregateIterable<Document> it2 = db.getCollection(collection_name).aggregate(sd);
		for (Document row : it2) {
		    result = Double.parseDouble(row.toJson().substring(row.toJson().lastIndexOf(':')+2,row.toJson().length()-3));
		}
		
		return result;
	}
	
	/*Calculate standardized value for each city*/
	public Double standardize(Double value, String parameter, String collection_name, Double avg, Double stdDev){
		Double result = (double) 0;		
		result = (value - avg)/(stdDev);
		return result;
	}
	
	/*Set weights according to LOW / MEDIUM / HIGH specified by user for different input criteria*/
	public int getWeights(String input){
		if(input.toUpperCase().equals("LOW"))
			return -5000;
        else if(input.toUpperCase().equals("MEDIUM"))
        	return 5;
        else if(input.toUpperCase().equals("HIGH"))
            	return 5000; 
 
        return 0;
	}
	
	/*Set weights for the rent value of user input*/
	public int getWeightsForRent(int rate){
		if(100 >= rate && rate < 300)
			return 3;
        else if(300 >= rate && rate <= 600)
        	return 5000;
        else if(rate >= 600 && rate <= 1000)
        	return 700000;
        return 0;
	}
	
	public void insertIntoTempDatabase(AggregateIterable<Document> it) {
		db.getCollection(GenericConstants.tempJoinedView).drop();
		db.createCollection(GenericConstants.tempJoinedView);
		MongoCollection<Document> collection = db.getCollection(GenericConstants.tempJoinedView);
		
		for (Document row : it) {
			collection.insertOne(row);
		    
		}
	}
	
	/*Gets the Aggregated collection*/
	public DBCursor getTempView() {
		Mongo s = new Mongo();
	    DB db1 = s.getDB("Train");		
		DBCursor doc = db1.getCollection(GenericConstants.tempJoinedView).find();
		return doc;
	}
	
	/*Sort the list of cities in descending order of their standardized values*/
	public void sortList() {
		Comparator<Map<String, String>> mapComparator = new Comparator<Map<String, String>>() {
		    public int compare(Map<String, String> m1, Map<String, String> m2) {
		        if(Double.parseDouble(m1.get("Score")) > Double.parseDouble(m2.get("Score")))
		        	return -1;
		        else if(Double.parseDouble(m1.get("Score")) < Double.parseDouble(m2.get("Score")))
		        	return 1;
		        else return 0;
		    }
		};

		Collections.sort(list, mapComparator);
	}
	
	/*Print Top 10 cities based on the criteria*/
	public void printRecommendations() {
		int count = 0;
		System.out.println("Following are the recommended cities");
		for(Map<String, String> m : list) {
			count++;
			if(count < 10) {
				System.out.println(m.get("City"));
			}else {
				break;
			}
		}
	}
}
	

