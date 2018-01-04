package adm.data;

/*POJO for user input*/
public class InputData {
	String industryName;
	String populationRequirement;
	String literacyRate;
	int budget;
	String transportDensity;
	
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	
	public int getBudget() {
		return budget;
	}
	public void setBudget(int budget) {
		this.budget = budget;
	}
	public String getPopulationRequirement() {
		return populationRequirement;
	}
	public void setPopulationRequirement(String populationRequirement) {
		this.populationRequirement = populationRequirement;
	}
	public String getLiteracyRate() {
		return literacyRate;
	}
	public void setLiteracyRate(String literacyRate) {
		this.literacyRate = literacyRate;
	}
	public String getTransportDensity() {
		return transportDensity;
	}
	public void setTransportDensity(String transportDensity) {
		this.transportDensity = transportDensity;
	}
	
	public String toString() {
		return "Industry Name: "+industryName+
				"\n Budget :" +budget+
				"\n Population :" +populationRequirement+
				"\n Transportation : "+transportDensity+
				"\n Litearcy :" + literacyRate+ "\n";
	}
	
	
}
