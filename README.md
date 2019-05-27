Workplace Locator v0.5



*******************************************************************************

SUMMARY

*******************************************************************************


Globalization has resulted in thousands of cities across continents to be 
empowered in multitudes of industries. It has become imperative for companies
 to discover an ideal location to base their workforce to foster growth and 
 development. Whenever a company tries to identify a new location for 
 expanding their business, it has become increasingly difficult for companies
 to select an ideal location as they have so many cities to choose from. 
Factors such as standard of living, proximity to airport, ease of 
transportation, availability of skilled labor, predominant market industry
 trends are important to consider while selecting a new location for the firm. 
We envision creating a one stop solution to help organizations who are willing 
to launch or expand their business and workplace by analyzing business 
priorities, budget and requirements and provide recommendations.

*******************************************************************************

CHANGES

*******************************************************************************
version 0.5

-Final Release

-added input validations for all the use cases

-implemented 'Compare two cities' use case. User is able to select two cities and add them for comparison.

version 0.4

-release candidate 1

-refined the recommendation algorithm (using weighted score) for primary use case to get more 
accurate results

-created separate modules to switch between different recommendation methods

-refactored the code to make it more readable

version 0.3

-beta release

-implemented secondary use case - display city parameters for a given city

-improved recommendation by adding 2 new parameters Literacy rate and 
real estate rates

version 0.2
 
- alpha release

- we have provided 3 input criteria - industry type, population and
transport density - On the basis of the above 3 criteria, we are 
expecting top 10 cities where the expected condition matches the input
- Previously, the input was taken from JSON file, we have changed the file 
format to csv
- We have decided to use csv because our initial data was stored in the csv 
file and therefore fetching and manipulation of data was more favorable 
using csv. 
- we have completed first pass of primary use case (recommendations based on user criteria)
- naive implementation of recommendations (piped filter, classification and sorting)
- indexed a few columns to increase speed

version 0.1

- proof of architecture

**************************************************************************

SETUP

**************************************************************************
 
Steps to execute the project :-

1. MongoDB and java should be installed.
2. Once installed, set the environment variable for mongoDB and JAVA. 
	Path should be selected up to bin folder.
3. Unzip the file "Team 3 - assign5"
4. Open Eclipse and import from Eclipse File -> Import -> Existing Gradle 
	Project 
5. Browse to unzipped folder location till "Team 3 - Project"(from step 2) 
	and click on Finish
6. Go to gradle tasks under project folder in Eclipse. Go to application 
	and click on run.

****************************************************************************
OTHER NOTES
****************************************************************************
1. Input required
Enter "1" or "2" to select any option

Input for Option 1
Try "Accounting", "Wholesale" or "Wireless" when prompted to enter industry 
type
For Population criteria try "Low", "Medium" or "High"
For Transport Density try "Low", "Medium" or "High"
For budget try any value between 1 to 1000
For literacy rate try "Low", "Medium" or "High"

Input for Option 2
Try "Houston", "Austin" or "New York" when prompted to enter city

Enter "q" to quit
