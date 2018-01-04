package adm.data;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoDatabase;

public class Average {
	public Double getRentAvg(MongoDatabase db) {
		return  getAverage(GenericConstants.rentCol,  GenericConstants.realEstateCollection, db);
	}
	
	public Double getPopAvg(MongoDatabase db) {
		return getAverage(GenericConstants.popCol, GenericConstants.populationCollection, db);
	}
	
	public Double getLitRateAvg(MongoDatabase db) {
		return getAverage(GenericConstants.litRateCol, GenericConstants.literacyCollection, db);
	}
	
	public Double geTransportAvg(MongoDatabase db) {
		return getAverage(GenericConstants.transportCol, GenericConstants.transportCollection, db);
	}
	
	/*Main function to calculate average*/
	public Double getAverage(String parameter, String collection_name, MongoDatabase db)
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
}
