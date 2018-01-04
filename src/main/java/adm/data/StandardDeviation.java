package adm.data;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoDatabase;

public class StandardDeviation {
	
	public Double getRentStdDev(MongoDatabase db) {
		return  getStandardDeviation(GenericConstants.rentCol,  GenericConstants.realEstateCollection, db);
	}
	
	public Double getPopStdDev(MongoDatabase db) {
		return getStandardDeviation(GenericConstants.popCol, GenericConstants.populationCollection, db);
	}
	
	public Double getLitRateStdDev(MongoDatabase db) {
		return getStandardDeviation(GenericConstants.litRateCol, GenericConstants.literacyCollection, db);
	}
	
	public Double geTransportStdDev(MongoDatabase db) {
		return getStandardDeviation(GenericConstants.transportCol, GenericConstants.transportCollection, db);
	}
	
	/*Main method for calculating standard deviation*/
	public Double getStandardDeviation(String parameter, String collection_name, MongoDatabase db)
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
}
