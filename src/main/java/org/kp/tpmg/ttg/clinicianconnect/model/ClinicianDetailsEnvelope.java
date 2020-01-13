package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;

public class ClinicianDetailsEnvelope implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ClinicianDetails clinicianDetails;

	public ClinicianDetails getClinicianDetails() {
		return clinicianDetails;
	}

	public void setClinicianDetails(ClinicianDetails clinicianDetails) {
		this.clinicianDetails = clinicianDetails;
	}
}
