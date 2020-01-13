package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;

public class PhoneBookFacilityJSON implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PhoneBookFacilityOutput service;

	public PhoneBookFacilityOutput getService() {
		return service;
	}

	public void setService(PhoneBookFacilityOutput service) {
		this.service = service;
	}
	
}
