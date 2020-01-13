package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.OSVersion;

public class PhoneBookFacilityOutput implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private Status status;
	private AppVersion appVersion;
	private PhoneBookFacilityEnvelope envelope;
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
	private OSVersion iOSVersion;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PhoneBookFacilityEnvelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(PhoneBookFacilityEnvelope envelope) {
		this.envelope = envelope;
	}

	public AppVersion getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(AppVersion appVersion) {
		this.appVersion = appVersion;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public OSVersion getiOSVersion() {
		return iOSVersion;
	}

	public void setiOSVersion(OSVersion iOSVersion) {
		this.iOSVersion = iOSVersion;
	}

	@Override
	public String toString() {
		return "PhoneBookFacilityOutput [name=" + name + ", status=" + status + ", appVersion=" + appVersion + ", envelope=" + envelope
				+ ", iOSVersion=" + iOSVersion + "]";
	}

}
