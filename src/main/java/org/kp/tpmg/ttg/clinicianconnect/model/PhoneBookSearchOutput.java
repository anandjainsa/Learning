package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;

import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.OSVersion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class PhoneBookSearchOutput implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonInclude(Include.NON_EMPTY)
	private OSVersion iOSVersion;

	private String name;
	private Status status;
	private AppVersion appVersion;
	private PhoneBookSearchEnvelope envelope;
	
	
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

	public AppVersion getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(AppVersion appVersion) {
		this.appVersion = appVersion;
	}

	public PhoneBookSearchEnvelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(PhoneBookSearchEnvelope envelope) {
		this.envelope = envelope;
	}

	public OSVersion getiOSVersion() {
		return iOSVersion;
	}

	public void setiOSVersion(OSVersion iOSVersion) {
		this.iOSVersion = iOSVersion;
	}

}
