package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;

public class ClinicianDetailsOutput implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Status status;
	private ClinicianDetailsEnvelope clinicianDetailsEnvelope;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ClinicianDetailsEnvelope getClinicianDetailsEnvelope() {
		return clinicianDetailsEnvelope;
	}

	public void setClinicianDetailsEnvelope(ClinicianDetailsEnvelope clinicianDetailsEnvelope) {
		this.clinicianDetailsEnvelope = clinicianDetailsEnvelope;
	}
}
