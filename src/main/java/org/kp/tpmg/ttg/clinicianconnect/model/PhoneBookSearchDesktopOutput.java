package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;

import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersion;

public class PhoneBookSearchDesktopOutput implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private Status status;
	private AppVersion appVersion;
	private PhoneBookSearchDesktopEnvelope envelope;
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
	public PhoneBookSearchDesktopEnvelope getEnvelope() {
		return envelope;
	}
	public void setEnvelope(PhoneBookSearchDesktopEnvelope envelope) {
		this.envelope = envelope;
	}
	
}
