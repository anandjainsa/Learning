package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;
import java.util.List;

import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.OSVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchFacility;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchSpecialty;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PhoneBookSearchEnvelope implements Serializable {

	private static final long serialVersionUID = 1L;
	private AppVersion appVersion;
	private Boolean hasMoreData;
	private SearchFacility searchFacility;
	private SearchSpecialty searchSpecialty;
	private List<PhoneBookClinician> phoneBookClinicians;
	private OSVersion iOSVersion;

	@JsonIgnore
	public AppVersion getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(AppVersion appVersion) {
		this.appVersion = appVersion;
	}

	public Boolean getHasMoreData() {
		return hasMoreData;
	}

	public void setHasMoreData(Boolean hasMoreData) {
		this.hasMoreData = hasMoreData;
	}

	public SearchFacility getSearchFacility() {
		return searchFacility;
	}

	public void setSearchFacility(SearchFacility searchFacility) {
		this.searchFacility = searchFacility;
	}

	public SearchSpecialty getSearchSpecialty() {
		return searchSpecialty;
	}

	public void setSearchSpecialty(SearchSpecialty searchSpecialty) {
		this.searchSpecialty = searchSpecialty;
	}

	public List<PhoneBookClinician> getPhoneBookClinicians() {
		return phoneBookClinicians;
	}

	public void setPhoneBookClinicians(List<PhoneBookClinician> phoneBookClinicians) {
		this.phoneBookClinicians = phoneBookClinicians;
	}

	@JsonIgnore
	public OSVersion getiOSVersion() {
		return iOSVersion;
	}

	public void setiOSVersion(OSVersion iOSVersion) {
		this.iOSVersion = iOSVersion;
	}

}
