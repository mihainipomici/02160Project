import com.project.dto.*;
import com.project.repository.ClientDatabase;
import com.project.repository.ContainerDB;
import com.project.repository.JourneyDB;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StepDefinition {


//Feature: register client
	Client client = new Client();
	ClientDatabase CD;
	ResponseObject registerResponse;
	LogisticCompany company = new LogisticCompany();

	//Background

	@Given("a logistic company {string}")
	public void a_logistic_company(String name) {
		company = new LogisticCompany();
		company.setName(name);
	}

	@Given("a client database")
	public void a_client_database() {
		CD = company.getClientDatabase();
	}

	//Scenario 1: all correct info

	@Given("a valid client name {string}")
	public void a_valid_client_name(String name) {
	    client.setName(name);
	}

	@Given("a valid address: {string}, {string}, {string}, {string}, {string}")
	public void a_valid_address(String country, String city, String postcode, String streetname, String streetnumber ) {
	    Address address = new Address(country,city,postcode,streetname,streetnumber);
	    client.setAddress(address);
	}

	@Given("a valid reference person {string}")
	public void a_valid_reference_person(String referencePerson) {
	    client.setReferencePerson(referencePerson);
	}

	@Given("a valid email {string}")
	public void a_valid_email(String email) {
	    client.setEmail(email);
	}

	@When("Registering in {string}")
	public void registering(String database) {
		if (database.equals("client database")) {
			registerResponse = CD.registering(client);
		}
		else if (database.equals("journey database")) {
			
            registerResponse = JDB.registerStep1(journey);
            
            if(registerResponse.getCode()==200) {
            	
            	availableContainers = CDB.availableContainerAt(journey.getOriginPort());
            	JDB.registerStep2(journey , selectedContainer, client, company);
            	
            	
            }
		}
	}

	@Then("the system displays a message confirming the registration of client") //code 100
	public void the_system_displays_a_message_confirming_the_registration_of_client() {
	    assertEquals("Client succesfully registered.", registerResponse.getMessage());
	}

	@Then("a new client id is automaticly generated")
	public void a_new_client_id_is_automaticly_generated() {
	    assertEquals(client.getHasID(),true);
	}

	//Scenario 2 - missing info
	@Then("the system displays a message confirming the registration and indicating the missing fields") //code 101
	public void the_system_displays_a_message_confirming_the_registration_and_indicating_the_missing_fields() {
		 assertEquals("Client registered, but some or all fields are missing.", registerResponse.getMessage());
	}

	//Scenario 3 - missing name
	@Then("the system displays a message telling that the client needs a name")
	public void the_system_displays_a_message_telling_that_the_client_needs_a_name() {
		assertEquals("Clients needs a name to be registered, no changes were made!", registerResponse.getMessage());
	}

	//Scenario 4 - already registered
	@Then("the system displays a message telling that the client is already registered")
	public void the_system_displays_a_message_telling_that_the_client_is_already_registered() {
		assertEquals("Client with the same name already registered, no changes were made!", registerResponse.getMessage());
	}

//Feature: client update client info

	String updateChoice;
	String updateContent;
	ResponseObject updateResponse;

	//Background:
	@Given("a client database with the client {string}")
	public void a_client_database_with_the_client(String name) {

		CD = company.getClientDatabase();

		Client c= new Client();
	    c.setName(name);

	    CD.registering(c);
	}

	//Scenario 1: The client updates a piece of information that can be changed

	@Given("an update choice {string}")
	public void an_update_choice(String Choice) {
	    updateChoice = Choice;
	}

	@When("Update client information")
	public void update_client_information() {
	    updateResponse = client.update(updateChoice, updateContent);
	}

	@Then("the client information is updated")
	public void the_client_information_is_updated() {
		assertEquals("Client information succesfully updated", updateResponse.getMessage());
	}

//Feature: Company find client

	String searchword;
	String filter;
	ResponseObject searchResponse;

	//Background
	@Given("a client database with the client {string} {string} {string} and {string}")
	public void a_client_database_with_the_client_and(String name1, String name2, String name3, String name4) {
	    Client c1 = new Client(name1, new Address("Denmark","Lyngby","2800","Fysikvej","315A"),"Oskar","sara@student.du.dk", "000");
	    Client c2 = new Client(name2, new Address("Denmark","Lyngby","2800","Kampsax","30"),"None","mihai@student.dtu.dk", "000");
	    Client c3 = new Client(name3, new Address("Denmark","Lyngby","2800","Fysikvej","315A"),"Sammy","thomas@student.dtu.dk", "000");
	    Client c4 = new Client(name4, new Address("Denmark","Lyngby","2800","Kampsax","48"), "Suzan", "miguel@student.dtu.dk", "000");

	    company.getClientDatabase().registering(c1);
	    company.getClientDatabase().registering(c2);
	    company.getClientDatabase().registering(c3);
	    company.getClientDatabase().registering(c4);
	}


	//Scenario 1: The company uses the criteria from an existing client without filter
	//Scenario 2: no matches
	@Given("a searchword {string}")
	public void a_searchword(String word) {
	    searchword = word;
	}

	@Given("a filter {string}")
	public void a_filter(String filt) {
	    filter = filt;
	}

	@When("searching for client")
	public void searching_for_client() {
	    searchResponse=company.getClientDatabase().resultSearchClient(searchword, filter);
	}

	@Then("the corresponding {string} client\\(s) is\\/are found.")
	public void the_corresponding_client_s_is_are_found(String expectedClients) {
		assertEquals(expectedClients+" clients found with the searchword: ["+searchword+"] and the filter: ["+filter+"]", searchResponse.getMessage());
	}



	//Feature:login

	String loginType;
	String username;
	String password;
	ResponseObject loginResponse;
	User activeUser;

	@Given("The log-in type {string}")
	public void the_log_in_type(String string) {
		loginType = string;
	}

	@Given("The password is {string}")  //WARNING - NOT BEST IMPLEMENTATION ON SETTING THE PASSWORD IN CLIENT
	public void the_password_is(String string) {
	    if (loginType.equals("Company")){company.setPassword(string);}
	    else if (loginType.equals("Client")){company.getClientDatabase().getClients().get(3).setPassword(string);}
	}

	@Given("The username {string}")
	public void the_username(String string) {
	    username = string;
	}

	@Given("The password {string}")
	public void the_password(String string) {
	    password = string;
	}

	@When("Logging-in")
	public void logging_in() {
	    loginResponse = Application.login(loginType, username, password, company);
	}

	@Then("Log-in is succesfull")
	public void log_in_is_succesfull() {
	    assertEquals("Succesfull login.", loginResponse.getMessage());
	}

	@Then("Log-in fails")
	public void log_in_fails() {
		assertEquals("Failed login.", loginResponse.getMessage());
	}

	@Then("active user is updated")
	public void active_user_is_updated() {

		if (loginType.equals("Company")){activeUser = company;}

	    else if (loginType.equals("Client")){activeUser = client;}

	}




	//feature: Client search journey
	//Scenario: The client uses the criteria from an existing journey without filter

	Jou journey = new Jou();
	JourneyDB JDB = new JourneyDB();

	@Given("a journey database with the departing port {string}  {string} and {string}")
	public void a_journey_database_with_the_departing_port_and(String poo1, String poo2, String poo3) {
	
		JDB = new JourneyDB();
		client.setJDB(JDB);
	
		Jou j1 = new Jou(poo1, "BEY","Bananas","NETTO");
		Jou j2 = new Jou(poo2, "BRU","Bananas","Fotex");
		Jou j3 = new Jou(poo3, "PAR","Spinach","Brus");
	
		JDB.registerStep1(j1);
		JDB.registerStep1(j2);
		JDB.registerStep1(j3);
	
		con = new Container(CDB , poo1);
		CDB.getContainers().add(con);
	
		con1 = new Container(CDB , poo2);
		CDB.getContainers().add(con1);
	
		con2 = new Container(CDB , poo3);
		CDB.getContainers().add(con2);
	
		JDB.registerStep2(j1 , con, client, company);
		JDB.registerStep2(j2 , con1, client, company);
		JDB.registerStep2(j3 , con2, client, company);	
	}

	@When("searching for journey")
	public void searching_for_journey() {
		searchResponse=client.getJDB().resultSearchJourney(searchword, filter);
	}

	@Then("the corresponding {string} journey\\(s) is\\/are found.")
	public void the_corresponding_journey_s_is_are_found(String expectedJourneys) {
		assertEquals(expectedJourneys+" journeys found with the searchword: ["+searchword+"] and the filter: ["+filter+"]", searchResponse.getMessage());
	}



	ContainerDB CDB = new ContainerDB();
	Container con;
	Container con1;
	Container con2;
	Container selectedContainer;
	ArrayList<Container> availableContainers;

//Feature: User registers journey

	//Background
	@Given("a client {string}")
	public void a_client(String name) {
		CD = new ClientDatabase();
		client = new Client(CD);
		client.setName(name);
	}

	@Given("a journey database")
	public void a_journey_database() {
		JDB = new JourneyDB();
		client.setJDB(JDB);
	}


	//Scenario: The client does not give all of the container's journey

	@Given("a valid port of origin {string}")
	public void a_valid_port_of_origin(String poo) {
		journey.setOriginPort(poo);
	}

	@Given("a valid destination {string}")
	public void a_valid_destination(String D) {
		journey.setDestination(D);
	}

	@Given("a valid company {string}")
	public void a_valid_company(String C) {
		journey.setCompany(C);
	}

	@Given("a valid content {string}")
	public void a_valid_content(String string) {
		journey.setContent(string);
	}

	@Then("the system displays a message telling that the field {string} needs to be filled")
	public void the_system_displays_a_message_telling_that_the_field_needs_to_be_filled(String field) {
	
		String missingMessage="";
		if (field.contains("port of origin")) {missingMessage +=" Journey needs a port of origin to be registered.";}
		if (field.contains("destination")) {missingMessage +=" Journey needs a destination to be registered.";}
		if (field.contains("content")) {missingMessage +=" Journey needs a content to be registered.";}
		if (field.contains("company")) {missingMessage +=" Journey needs a company to be registered.";}
	
		assertEquals("No changes were made!"+ missingMessage, registerResponse.getMessage());
	}

	//Scenario: The client gives all information about the container's journey, chooses a container that is available at the port of origin.

	@Given("an available container at the port of origin")
	public void an_available_container_at_the_port_of_origin() {

		con = new Container(CDB , journey.OriginPort);
		CDB.getContainers().add(con);
	
		con1 = new Container(CDB , "Beirut");
		CDB.getContainers().add(con1);
	
		con2 = new Container(CDB , "Amsterdam");
		CDB.getContainers().add(con2);
	}

	@Given("a selected container")
	public void a_selected_container() {
		selectedContainer = con;
	}

	@Then("the system displays a message confirming the registration of journey")
	public void the_system_displays_a_message_confirming_the_registration_of_journey() {
		assertEquals("Journey successfully created.", registerResponse.getMessage());
	}

	@Then("a new journey id is automaticly generated")
	public void a_new_journey_id_is_automaticly_generated() {
		assertEquals(true,journey.getHasID());
	}

	@Then("the container is in that journey")
	public void the_container_is_in_that_journey() {
		assertTrue(JDB.containerInAJourney(selectedContainer.getContainerID()));
	}
	
	
	
	
	
	
	
	








	//Scenario: Client registers a journey

	//Scenario: The client gives all information about the container's journey, chooses a new container when there is no available ones at the port of origin.

	//Scenario: Client registers a journey

	//Scenario: The client gives all information about the container's journey, chooses a new container when there is no available ones at the port of origin.

	@Given("a new generated container")
	public void a_new_generated_container() {
		selectedContainer = new Container(CDB , journey.OriginPort);
		CDB.getContainers().add(selectedContainer);
	}

	@Given("no container at the port of origin")
	public void no_container_at_the_port_of_origin() {
		con = new Container(CDB , "123");
		CDB.getContainers().add(con);
	}
	//Scenario: The client gives all information about the container's journey, chooses a new container when there is available ones at the port of origin.

	//Scenario: The client gives all information about the container's journey, chooses a new container when there is no available ones at the port of origin.

	//Feature: Company updates journey information
	//Scenario: The company updates the current position

	@Given("a container in a journey with destination {string}")
	public void a_container_in_a_journey_with_destination(String destination) {
		journey = new Jou("CPH", destination,"Bananas","NETTO");
		//con = new Container(CDB , "Beirut");
		journey.setC(con);
	}

	@Given("a container with the ID {string}")
	public void a_container_with_the_ID(String CID) {
		con = new Container(true , CID);
	}

	//Scenario: The company updates the current position"

	@When("Update current position to {string}")
	public void update_current_position(String updateContent) {
		updateResponse = journey.update(updateContent, CDB);
	}

	@Then("the current position is updated")
	public void the_current_position_is_updated() {
		assertEquals("current position succesfully updated" , updateResponse.getMessage());
	}

	@Then("the current position is updated and the journey is terminated")
	public void the_current_position_is_updated_and_the_journey_is_terminated() {
		assertEquals("current position succesfully updated and the journey is terminated" , updateResponse.getMessage());
		}













//Mandatory 3 and Optional 1 ////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Feature: Add measurements about the container’s internal status
	Container container = new Container();
	double updateData;

	//Scenario: Update measurements about the container’s internal status

	@Given("a container with a container id {string}")
	public void a_container_with_a_container_id(String containerID) {
		Container container = new Container(containerID);
		CDB.getContainers().add(container);
	}


	@When("updating the internal status")
	public void updating_the_internal_status() {
		updateResponse = container.update(updateChoice,updateData);
	}

	@Then("the system sets the internal status to the latests measurements")
	public void the_system_sets_the_internal_status_to_the_latests_measurements() {
		assertEquals("Measurement successfully added.",updateResponse.getMessage());
	}


	Container initialData;
	double temperature; double humidity; double pressure;
	double temp; double hum; double press;
	double retrievedTemp; double retrievedHum; double retrievedPress;


	@Given("a container with temperature {double} humidity {double} pressure {double}")
	public void a_container_with_temperature_humidity_pressure(double temp,double hum,double press) {
		initialData = new Container();
		initialData.setTemp(this.temp);
		initialData.setHum(this.hum);
		initialData.setPress(this.press);
	}

	@When("a client wants to retrieve the measurements from the internal status")
	public void a_client_wants_to_retrieve_the_measurements_from_the_internal_status() {
		retrievedTemp = initialData.getTemp();
		retrievedHum = initialData.getHum();
		retrievedPress = initialData.getPress();
	}

	@Then("the measurements are retrieved")
	public void the_measurements_are_retrieved() {
		assertEquals(temperature,retrievedTemp,0.001);
		assertEquals(humidity,retrievedHum,0.001);
		assertEquals(pressure,retrievedPress,0.001);
	}


//Feature: Track each container
	Container internalStatusHistory = new Container();
	Container containerLocation = new Container();
	ResponseObject trackData;
	ResponseObject locateContainer;


	//Scenario: Tracking the internal status

	/*
	@Given("a container with a container id {string}")
	public void a_container_with_a_container_id(String containerID) {
		Container container = new Container(containerID);
		CDB.getContainers().add(container);
	} */

	@When("the logistic company measures a {string} of {double}")
	public void the_logistic_company_measures_a_of(String updateChoice, double value) {
		trackData = internalStatusHistory.track(updateChoice,value);
	}

	@Then("the logistic company adds the data given to the internal status database")
	public void the_logistic_company_adds_the_data_given_to_the_internal_status_database() {
		assertEquals("Tracked internal status.",trackData.getMessage());
	}

	//Scenario: Tracking the journey

	/*
	@Given("a container with a container id {string}")
	public void a_container_with_a_container_id(String containerID) {
		Container container = new Container(containerID);
		CDB.getContainers().add(container);
	} */

	@When("the logistic company determines the location {string} of the container")
	public void the_logistic_company_determines_the_location_of_the_container(String location) {
		locateContainer = containerLocation.locate(location);
	}

	@Then("the logistic company adds the location found to the journey database")
	public void the_logistic_company_adds_the_location_found_to_the_journey_database() {
		assertEquals("Tracked location.",locateContainer.getMessage());
	}

//Feature: Retrieve info about each container
	ArrayList<Double> InternalTemperature = new ArrayList<>();
	ArrayList<Double> AirHumidity = new ArrayList<>();
	ArrayList<Double> AtmosphericPressure = new ArrayList<>();

	ArrayList<Double> retrievedTemperature;
	ArrayList<Double> retrievedHumidity;
	ArrayList<Double> retrievedPressure;

	ArrayList<String> historyLocation = new ArrayList<>();
	ArrayList<String> retrievedLocation;


	@Given("a container with a temperature history {double}, {double}, {double}")
	public void a_container_with_a_temperature_history(double temp1, double temp2, double temp3) {
		InternalTemperature.add(temp1);InternalTemperature.add(temp2);InternalTemperature.add(temp3);
		internalStatusHistory.setTemperature(InternalTemperature);
	}

	@Given("a container with a humidity history {double}, {double}, {double}")
	public void a_container_with_a_humidity_history(double hum1, double hum2, double hum3) {
		InternalTemperature.add(hum1);InternalTemperature.add(hum2);InternalTemperature.add(hum3);
		internalStatusHistory.setHumidity(AirHumidity);
	}

	@Given("a container with a pressure history {double}, {double}, {double}")
	public void a_container_with_a_pressure_history(double press1, double press2, double press3) {
		InternalTemperature.add(press1);InternalTemperature.add(press2);InternalTemperature.add(press3);
		internalStatusHistory.setPressure(AtmosphericPressure);
	}

	@When("the system decides to retrieve the internal status measurements")
	public void the_system_decides_to_retrieve_the_internal_status_measurements() {
		retrievedTemperature = internalStatusHistory.getTemperature();
		retrievedHumidity = internalStatusHistory.getHumidity();
		retrievedPressure = internalStatusHistory.getPressure();
	}
			
	@Then("the internal status measurements are retrived")
	public void the_internal_status_measurements_are_retrived() {
		assertEquals(InternalTemperature,retrievedTemperature);
		assertEquals(AirHumidity,retrievedHumidity);
		assertEquals(AtmosphericPressure,retrievedPressure);
	}

	//Scenario: Retrieve data from the journey evolution

	/*
	@Given("a container with a container id {string}")
	public void a_container_with_a_container_id(String containerID) {
		Container container = new Container(containerID);
		CDB.getContainers().add(container);
	} */

	@Given("a container with a history of locations {string}, {string}, {string}")
	public void a_container_with_a_history_of_locations(String loc1, String loc2, String loc3) {
		historyLocation.add(loc1);historyLocation.add(loc2);historyLocation.add(loc3);
		containerLocation.setLocation(historyLocation);
	}

	@When ("the system decides to retrieve a location from the database")
	public void the_system_decides_to_retrieve_a_location_from_the_database() {
		retrievedLocation = containerLocation.getLocation();
	}

	@Then ("the system retrieves the location")
	public void the_system_retrieves_the_location() {
		assertEquals(historyLocation,retrievedLocation);
	}
}
