package com.project.dto;

import java.util.ArrayList;

public class Jou {
	
	Container container;
	public String OriginPort;
	public String Destination;
	public String Content;
	public String Company;
	public String JourneyID;
	public ArrayList<String> Container;

    public Jou(String op , String dest , String cont , String comp )	{
    	
    	this.OriginPort = op;
    	this.Destination = dest;
    	this.Content = cont;
    	this.Company = comp;
    	
    }

	public Jou() {
		super();
	}
	public String getOriginPort() {
		return OriginPort;
	}
	public void setOriginPort(String originPort) {
		OriginPort = originPort;
	}
	public String getDestination() {
		return Destination;
	}
	public void setDestination(String destination) {
		Destination = destination;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getCompany() {
		return Company;
	}
	public void setCompany(String company) {
		Company = company;
	}
	public String getJourneyID() {
		return JourneyID;
	}
	public void setJourneyID(String journeyID) {
		JourneyID = journeyID;
	}
	public ArrayList<String> getContainer() {
		return Container;
	}
	public void setContainer(ArrayList<String> container) {
		Container = container;
	}

	

	//---------Update Journey Method:------------
		
		//updating any field with a string
		public ResponseObject update(String updateContent) {
			ResponseObject updateResponse = null;
			int code=216; //216 = error code by default (it is changed if an update successfully happens)
			this.container.currentPosition = updateContent;
			code=211;
			
			
			if (code!=216) {updateResponse = new ResponseObject(code, "current position succesfully updated");}
			return updateResponse;
		}
		
	//------------end of Update current position method--------------

	//--isFound() method, used in the "searchingClient()" method from the ClientDatabase class
	//used to find if a substring can be found in the information of a client	
		public ResponseObject isFound(String searchword) {
			int code = 230;
			String message = "";
			
			if (this.OriginPort.contains(searchword)){code = 231; message="Port of origin";}
			else if(this.Destination.contains(searchword)){code = 232; message="Destination";}
			else if(this.Content.contains(searchword)){code = 233; message="Content";}
			else if(this.Company.contains(searchword)){code = 234; message="Company";}
					
			ResponseObject isFoundResponse = new ResponseObject(code,message);
			return isFoundResponse;
		}
		
	
}