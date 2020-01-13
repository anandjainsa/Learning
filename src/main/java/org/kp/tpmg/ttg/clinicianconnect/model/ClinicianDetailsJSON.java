package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;

public class ClinicianDetailsJSON implements Serializable {

	private static final long serialVersionUID = 1L;

	private ClinicianDetailsOutput service;

	public ClinicianDetailsOutput getService() {
		return service;
	}

	public void setService(ClinicianDetailsOutput service) {
		this.service = service;
	}

}
