package adm;

import java.io.IOException;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoImport {
	
	/*Main class to import data from csv files*/
	public static void main(String[] args) throws IOException {
		MongoDBDataLoader db= new MongoDBDataLoader();
	    MongoClient mongoClient=db.setDatabase();
	    MongoDatabase database = db.createDatabase(mongoClient);
		db.processInsertData(database);
	}
}
