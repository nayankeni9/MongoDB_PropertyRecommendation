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

public class Population {
	String collection_name="";
	MongoDBDataLoader db = new MongoDBDataLoader();
	MongoClient client = db.setDatabase();
    MongoDatabase database = client.getDatabase(GenericConstants.dbName);
    MongoCollection<Document> collection = database.getCollection(GenericConstants.populationCollection);
	
    
    /*Get all cities with the user input for Population Requirement*/
	public List<String> getTopCities(String pop_req, List<String> arrayList)
	{
		List<String> finallist = new ArrayList<String>();
		
		try {
			ListCities lc = new ListCities();
			MongoCursor<Document>  it;
			BasicDBObject createIndex = new BasicDBObject();
			BasicDBObject searchObject = new BasicDBObject();
			BasicDBObject projObject = new BasicDBObject();
			BasicDBObject sortObject = new BasicDBObject();
			
			createIndex.put("Population", 1);
			collection.createIndex(createIndex);
			searchObject.put("Population_level", java.util.regex.Pattern.compile(pop_req));
			searchObject.put("AccentCity", new BasicDBObject("$in", arrayList));
			projObject.put("AccentCity",1);
			projObject.put("_id",0);
			sortObject.put("Population", 1);				
			FindIterable<Document> iterDoc = collection.find(searchObject).sort(sortObject).projection(projObject).limit(10); 
			it = iterDoc.iterator(); 
			finallist = lc.processCityList(it);
		}catch(Exception e) {}	
		
		if(finallist.size()<10)
			return arrayList;
		
		return finallist;
	}
}