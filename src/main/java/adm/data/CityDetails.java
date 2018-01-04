package adm.data;

import java.util.List;

public class CityDetails {
	String cityName;
	List<String> industryType;
	String population;
	String transportation;
	String literacyRate;
	String realEstate;
	
	public String getPopulation() {
		return population;
	}
	public void setPopulation(String population) {
		this.population = population;
	}
	public String getTransportation() {
		return transportation;
	}
	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}
	public String getLiteracyRate() {
		return literacyRate;
	}
	public void setLiteracyRate(String literacyRate) {
		this.literacyRate = literacyRate;
	}
	public String getRealEstate() {
		return realEstate;
	}
	public void setRealEstate(String realEstate) {
		this.realEstate = realEstate;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public List<String> getIndustryType() {
		return industryType;
	}
	public void setIndustryType(List<String> industryType) {
		this.industryType = industryType;
	}
	
	public String toString(){
		return " City Name : " + getCityName()+
			   "\n Literacy Rate : " + getLiteracyRate()+
			   "\n Transportation:" + getTransportation()+
			   "\n Real Estate Rate in $/sqft : " + getRealEstate()+
			   "\n Population : " + getPopulation()+
			   "\n Prominent Industries : " + (getIndustryType().size()==0?"None": getIndustryType()) + "\n";
	}
}
