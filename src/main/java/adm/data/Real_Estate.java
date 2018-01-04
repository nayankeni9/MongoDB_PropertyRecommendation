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

public class Real_Estate {
	String collection_name="";
	MongoDBDataLoader db = new MongoDBDataLoader();
	MongoClient client = db.setDatabase();
    MongoDatabase database = client.getDatabase(GenericConstants.dbName);
    
    MongoCollection<Document> collection = database.getCollection(GenericConstants.realEstateCollection);
	
    
    /*Get all cities with the user input for Industry Type*/
	public List<String> getTopCities(int real_estate, List<String> arrayList)
	{
		List<String> finallist = new ArrayList<String>();		
		try {
			ListCities lc =new ListCities();
			MongoCursor<Document>  it;
			BasicDBObject searchObject = new BasicDBObject();
			BasicDBObject sortObject = new BasicDBObject();
			BasicDBObject projObject = new BasicDBObject();
			searchObject.put("Rent_Rate", new Document("$lt",real_estate));
			searchObject.put("AccentCity", new BasicDBObject("$in", arrayList));
			sortObject.put("Rent_Rate",-1);
			projObject.put("AccentCity",1);
			projObject.put("_id",0);
			
			FindIterable<Document> iterDoc = collection.find(searchObject).sort(sortObject).projection(projObject).limit(10); 
			it = iterDoc.iterator(); 
			finallist = lc.processCityList(it);
		}catch(Exception e) {}
		
		if(finallist.size()<10)
			return arrayList;
		return finallist;
	}
}