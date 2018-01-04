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

public class IndustryType {
	String collection_name="";
	MongoDBDataLoader db = new MongoDBDataLoader();
	MongoClient client = db.setDatabase();
    MongoDatabase database = client.getDatabase(GenericConstants.dbName);
    
    MongoCollection<Document> collection = database.getCollection(GenericConstants.industryCollection);
	
    
    /*Get all cities with the user input for Industry Type*/
	public List<String> getTopCities(String industry)
	{
		List<String> finallist = new ArrayList<String>();		
		try {
			MongoCursor<Document>  it;
			BasicDBObject searchObject = new BasicDBObject();
			BasicDBObject projObject = new BasicDBObject();
			BasicDBObject sortObject = new BasicDBObject();
			ListCities lc = new ListCities();
			searchObject.put("Industry_Name", java.util.regex.Pattern.compile(industry));
			projObject.put("City",1);
			projObject.put("_id",0);
			sortObject.put("Rank", 1);
			FindIterable<Document> iterDoc = collection.find(searchObject).sort(sortObject).projection(projObject); 
			it = iterDoc.iterator(); 
			finallist = lc.processCityList(it);
		}catch(Exception e) {}
		return finallist;
	}
}