/* 
 * @author - Team3
 * @version - 1.0
 * @since - 10/12/2017
 * This class is used to load and display the data JSON file to Mongo collection
 * 
 */

package adm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import adm.data.GenericConstants;

public class MongoDBDataLoader {
	MongoClient mongoClient;

	public MongoClient setDatabase() {
		mongoClient = new MongoClient( "localhost" , 27017 );
		return mongoClient;
	}

	/**
	 * This is the insertData method which imports JSON file data into Mongo collection.
	 * @param args Unused.
	 * @return Nothing.
	 * @exception IOException On file not found error.
	 * @see IOException
	 */

	public void insertData(String folder, String file, String filewithoutExtension, String header) {
		Runtime r = Runtime.getRuntime();
		String command = "mongoimport --host 127.0.0.1 --port 27017 --db Train --collection "+filewithoutExtension+" -f "+header+" --type csv --file \"Data/"+file+"\"";
		try {
			r.exec(command); 
		}	catch (Exception e){ 
			System.out.println("Error executing " + command + e.toString());
		}
	}

	/**
	 * This will display the data that has been put into Mongo collection. 
	 * @param args - Mongo Collection object
	 * @return Nothing.
	 * @exception Exception on mongodb connectivity issues.
	 * @see Exception
	 */

	public void displayData(MongoCollection<Document> collection) {

		try {
			FindIterable<Document> iterDoc = collection.find(); 
			MongoCursor<Document> it = iterDoc.iterator(); 

			while (it.hasNext()) {  
				System.out.println(it.next());  
			}
		}catch(Exception e) {}
	}
	
	/*Returns list of all the data files in folders*/
	public List<String> listFilesForFolder(File folder) {
		List<String> fileList= new ArrayList<String>();
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	fileList.add(fileEntry.getName());
	        }
	    }
	    return fileList;
	}
	
	/*Checks if database exist, drops it  and creates new database*/
	public MongoDatabase createDatabase(MongoClient mongoClient) {
		try {
	    	mongoClient.dropDatabase(GenericConstants.dbName);
	    }catch(Exception e){}
	    MongoDatabase database = mongoClient.getDatabase(GenericConstants.dbName);
		System.out.println("Connected to the database successfully");
	    return database;
	}
	
	/*Main function for data import, reads each file and extracts header from it */
	public void processInsertData(MongoDatabase database) throws IOException {
		File folder = new File(GenericConstants.folderName);
		List<String> fileList= listFilesForFolder(folder);
		for(String file: fileList) {
			String s = file.substring(0, file.lastIndexOf("."));
			BufferedReader fileInput;
			String header="";
			try {
				fileInput = new BufferedReader(new FileReader("Data/"+file));
				header = fileInput.readLine();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			checkAndInsertData(database,GenericConstants.folderName, file, s, header);
		}
	}
	
	/*Creates new tables for data import based on filenames given*/
	public void checkAndInsertData(MongoDatabase database, String Folder, String file, String fileWithoutExtension, String header) {
		boolean collectionExists = mongoClient.getDatabase(GenericConstants.dbName).listCollectionNames().into(new ArrayList<String>()).contains(fileWithoutExtension);
		if (!collectionExists)
		{
			database.createCollection(fileWithoutExtension);
		}
	    insertData(Folder, file, fileWithoutExtension, header);
	}
}
