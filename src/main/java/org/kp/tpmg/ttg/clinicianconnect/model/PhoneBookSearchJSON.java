package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;

public class PhoneBookSearchJSON implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PhoneBookSearchOutput service;

	public PhoneBookSearchOutput getService() {
		return service;
	}

	public void setService(PhoneBookSearchOutput service) {
		this.service = service;
	}
	
	

}
