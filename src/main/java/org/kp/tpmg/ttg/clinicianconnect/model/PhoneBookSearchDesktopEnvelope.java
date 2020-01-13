package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;
import java.util.List;

import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchFacility;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchSpecialty;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PhoneBookSearchDesktopEnvelope implements Serializable {

	private static final long serialVersionUID = 1L;

	private AppVersion appVersion;
	private Boolean hasMoreData;
	private int skipCount;
	private SearchFacility searchFacility;
	private SearchSpecialty searchSpecialty;
	private List<PhoneBookClinicianDesktop> phoneBookClinicians;
	
	public int getSkipCount() {
		return skipCount;
	}
	public void setSkipCount(int skipCount) {
		this.skipCount = skipCount;
	}
	
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
	public List<PhoneBookClinicianDesktop> getPhoneBookClinicians() {
		return phoneBookClinicians;
	}
	public void setPhoneBookClinicians(
			List<PhoneBookClinicianDesktop> phoneBookClinicians) {
		this.phoneBookClinicians = phoneBookClinicians;
	}
	

}
