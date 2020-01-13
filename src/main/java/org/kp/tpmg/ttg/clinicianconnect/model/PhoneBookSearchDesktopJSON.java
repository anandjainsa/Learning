package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;

public class PhoneBookSearchDesktopJSON implements Serializable {

	private static final long serialVersionUID = 1L;
	private PhoneBookSearchDesktopOutput service;

	public PhoneBookSearchDesktopOutput getService() {
		return service;
	}

	public void setService(PhoneBookSearchDesktopOutput service) {
		this.service = service;
	}

}
