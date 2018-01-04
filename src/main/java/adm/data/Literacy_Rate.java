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

public class Literacy_Rate {
	String collection_name="";
	MongoDBDataLoader db = new MongoDBDataLoader();
	MongoClient client = db.setDatabase();
    MongoDatabase database = client.getDatabase(GenericConstants.dbName);
    
    MongoCollection<Document> collection = database.getCollection(GenericConstants.literacyCollection);
	
    
    /*Get all cities with the user input for Industry Type*/
	public List<String> getTopCities(String literacy, List<String> arrayList)
	{
		List<String> finallist = new ArrayList<String>();		
		try {
			ListCities lc =new ListCities();
			MongoCursor<Document>  it;
			BasicDBObject searchObject = new BasicDBObject();
			BasicDBObject projObject = new BasicDBObject();
			searchObject.put("Literacy_Rate", java.util.regex.Pattern.compile(literacy));
			searchObject.put("AccentCity", new BasicDBObject("$in", arrayList));
			projObject.put("AccentCity",1);
			projObject.put("_id",0);
			FindIterable<Document> iterDoc = collection.find(searchObject).projection(projObject).limit(10); 
			it = iterDoc.iterator(); 
			finallist = lc.processCityList(it);
		}catch(Exception e) {}
		if(finallist.size()<10)
			return arrayList;
		
		return finallist;
	}
}