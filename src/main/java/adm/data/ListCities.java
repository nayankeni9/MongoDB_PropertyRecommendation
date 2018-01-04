package adm.data;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCursor;

public class ListCities {
	/*process city list for recommendations*/
	public List<String> processCityList(MongoCursor<Document>  it) {
		List<String>  finallist = new ArrayList<String> ();
		if (it == null) {
		    System.out.println("No values");
		}
		while (it.hasNext()) {  
			String city = it.next().toString();
			city = city.substring(city.lastIndexOf("=")+1,city.indexOf("}"));
			finallist.add(city);
		}
		return finallist;
	}
}
