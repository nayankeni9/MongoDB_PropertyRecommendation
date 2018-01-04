package adm.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInput {
	BufferedReader br;

	public String getIndustrytype() {
		String s="";
		System.out.println("Enter Industry type : Accounting, Wholesale, Wireless etc.");
		while(true) {
			br =new BufferedReader(new InputStreamReader(System.in));
			try {
				s = br.readLine();
				if((s.equalsIgnoreCase("accounting"))||(s.equalsIgnoreCase("wholesale"))||(s.equalsIgnoreCase("wireless"))) {
					break;
				}else {	throw new IOException();}
			} catch (IOException e) {
				System.out.println("Enter valid industry type");
			}
		}
		return s;
	}

	public String getpopulationRequired() {
		String s="";
		System.out.println("Enter population criteria: Low or Medium or High");
		while(true) {
			br =new BufferedReader(new InputStreamReader(System.in));
			try {
				s = br.readLine();
				if((s.equalsIgnoreCase("high"))||(s.equalsIgnoreCase("low"))||(s.equalsIgnoreCase("medium"))) {
					break;
				}else {	throw new IOException();}
			} 
			catch (IOException e) {
				System.out.println("Enter valid population");
			}
		}
		return s;
	}

	public String getRequiredLiteracyRate() {
		String s="";
		System.out.println("Enter required literacy rate");
		while (true) {
			br =new BufferedReader(new InputStreamReader(System.in));
			try {
				s = br.readLine();
				if((s.equalsIgnoreCase("high"))||(s.equalsIgnoreCase("low"))||(s.equalsIgnoreCase("medium"))) {
					break;
				}else {throw new IOException();	}
			} catch (IOException e) {
				System.out.println("Enter valid literacy rate");
			}
		}
		return s;
	}

	public String getRequiredTransportDensity() {
		String s="";
		while (true) {
			System.out.println("Enter transport density");
			br =new BufferedReader(new InputStreamReader(System.in));
			try {
				s = br.readLine();
				if((s.equalsIgnoreCase("high"))||(s.equalsIgnoreCase("low"))||(s.equalsIgnoreCase("medium"))) {
					break;
				}
				else {throw new IOException();}
			} catch (IOException e) {
				System.out.println("Enter valid transport density");
			}
		}
		return s;
	}

	public int getRequiredBudget() {
		String s="";
		int budget=0;
		System.out.println("Enter required budget in $/sqft");
		while(true) {
			br =new BufferedReader(new InputStreamReader(System.in));
			try {
				s = br.readLine();
				budget = Integer.parseInt(s);
				break;
			} catch (Exception e) {
				System.out.println("Enter valid budget");
			}
		}
		return budget;
	}

	public String convertToCamelCase(String s)
	{
		return (s.substring(0,1)).toUpperCase()+(s.substring(1)).toLowerCase();
	}
}