package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;
import java.util.List;

import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookSpecialty;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.OSVersion;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PhoneBookSpecialtyEnvelope implements Serializable {

	private static final long serialVersionUID = 1L;

	private AppVersion appVersion;
	private List<PhoneBookSpecialty> phoneBookSpecialties;
	private OSVersion iOSVersion;

	@JsonIgnore
	public AppVersion getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(AppVersion appVersion) {
		this.appVersion = appVersion;
	}

	public List<PhoneBookSpecialty> getPhoneBookSpecialties() {
		return phoneBookSpecialties;
	}

	public void setPhoneBookSpecialties(List<PhoneBookSpecialty> phoneBookSpecialties) {
		this.phoneBookSpecialties = phoneBookSpecialties;
	}
	@JsonIgnore
	public OSVersion getiOSVersion() {
		return iOSVersion;
	}

	public void setiOSVersion(OSVersion iOSVersion) {
		this.iOSVersion = iOSVersion;
	}

	@Override
	public String toString() {
		return "PhoneBookSpecialtyEnvelope [appVersion=" + appVersion + ", phoneBookSpecialties=" + phoneBookSpecialties + ", iOSVersion="
				+ iOSVersion + "]";
	}

}
