package com.project.dto;

import com.project.repository.ContainerDB;

public class Jou {
	
	Container c = new Container();

	private boolean onGoing = true;
	private String OriginPort;
	private String Destination;
	private String Content;
	private String Company;
	private String JourneyID;
	private Boolean hasID;
	private String containerID;
	private String ClientID;

	public String getClientID() {
		return ClientID;
	}


	public void setClientID(String clientID) {
		ClientID = clientID;
	}

	public Jou(String op , String dest , String cont , String comp )	{
    	
    	this.OriginPort = op;
    	this.Destination = dest;
    	this.Content = cont;
    	this.Company = comp;
    	
    }

	public Jou() {
		super();
	}

	public boolean isOnGoing() {
		return onGoing;
	}

	public void setOnGoing(boolean onGoing) {
		this.onGoing = onGoing;
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


	public Boolean getHasID() {
		return hasID;
	}
	public void setHasID(Boolean hasID) {
		this.hasID = hasID;
	}

	public void setC(Container container) {
		this.c = container;
	}
	
	public Container getC() {
		return c;
	}

	 public String getContainerID() {
			return containerID;
		}

	public void setContainerID(String containerID) {
			this.containerID = containerID;
		}

	//---------Update Journey Method:------------
		
		//updating any field with a string
		public ResponseObject update(String updateContent, ContainerDB CDB) {
			ResponseObject updateResponse;
			
			if(!(this.c.getContainerID()==null)) {
				this.c.setCurrentPosition(updateContent);
				int code=211;
				updateResponse = new ResponseObject(code, "current position succesfully updated");
				
				if(this.c.getCurrentPosition().equals(this.getDestination())) {
					this.onGoing = false;
					this.c.setInJourney(false);
					updateResponse = new ResponseObject(212, "current position succesfully updated and the journey is terminated");
				}
			}
			else {
				CDB.find(this.getContainerID()).setCurrentPosition(updateContent);
				int code=211;
				updateResponse = new ResponseObject(code, "current position succesfully updated");
				
				if(CDB.find(this.getContainerID()).getCurrentPosition().equals(this.getDestination())) {
					this.onGoing = false;
					CDB.find(this.getContainerID()).setInJourney(false);
					updateResponse = new ResponseObject(212, "current position succesfully updated and the journey is terminated");
				}
				}
			
			return updateResponse;
		}
		
	//------------end of Update current position method--------------

	//--isFound() method, used in the "searchingClient()" method from the ClientDatabase class
	//used to find if a substring can be found in the information of a client	
		public ResponseObject isFound(String searchword) {
			int code = 230;
			String message = "";
			
			if (this.OriginPort.toLowerCase().contains(searchword.toLowerCase())){code = 231; message="Port of origin";}
			else if(this.Destination.toLowerCase().contains(searchword)){code = 232; message="Destination";}
			else if(this.Content.toLowerCase().contains(searchword.toLowerCase())){code = 233; message="Content";}
			else if(this.Company.toLowerCase().contains(searchword.toLowerCase())){code = 234; message="Company";}
			else if(this.getJourneyID().toLowerCase().contains(searchword.toLowerCase())){code = 234; message="ID";}

			return new ResponseObject(code,message);
		}

}