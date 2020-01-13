package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;
import java.util.List;

import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookFacility;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.OSVersion;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PhoneBookFacilityEnvelope implements Serializable {

	private static final long serialVersionUID = 1L;

	private AppVersion appVersion;
	private List<PhoneBookFacility> phoneBookFacilities;
	private OSVersion iOSVersion;

	@JsonIgnore
	public AppVersion getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(AppVersion appVersion) {
		this.appVersion = appVersion;
	}

	public List<PhoneBookFacility> getPhoneBookFacilities() {
		return phoneBookFacilities;
	}

	public void setPhoneBookFacilities(List<PhoneBookFacility> phoneBookFacilities) {
		this.phoneBookFacilities = phoneBookFacilities;
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
		return "PhoneBookFacilityEnvelope [appVersion=" + appVersion + ", phoneBookFacilities=" + phoneBookFacilities + ", iOSVersion=" + iOSVersion
				+ "]";
	}

}
