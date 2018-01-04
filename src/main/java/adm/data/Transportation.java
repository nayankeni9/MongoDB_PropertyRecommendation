package adm.data;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import adm.MongoDBDataLoader;

public class Transportation {
	
	String collection_name="";
	MongoDBDataLoader db = new MongoDBDataLoader();
	MongoClient client = db.setDatabase();
    MongoDatabase database = client.getDatabase(GenericConstants.dbName);
    
    MongoCollection<Document> collection = database.getCollection(GenericConstants.transportCollection);
	
    
	/*Get all cities with the user input for Transport Density*/
	public List<String> getTopCities(String transportDensity, List<String> cities)
	{
		List<String> cityList = new ArrayList<String>();
		try {
			ListCities lc = new ListCities();
			MongoCursor<Document>  it;
			BasicDBObject inQuery = new BasicDBObject();
			BasicDBObject sortQuery = new BasicDBObject();
		    BasicDBObject projObject = new BasicDBObject();

		    inQuery.put("Transport_level", transportDensity);
		    sortQuery.put("Ease_of_Transport_Rank", 1);
		    inQuery.put("City", new BasicDBObject("$in", cities));
			projObject.put("City",1);
			projObject.put("_id",0);
			
		    FindIterable<Document> iterDoc = collection.find(inQuery).sort(sortQuery).projection(projObject); 
			it = iterDoc.iterator(); 
			cityList = lc.processCityList(it);
		}catch(Exception e) {}
		
		if(cityList.size() <= 10)
			return cities;
		return cityList;
	}
	
}
