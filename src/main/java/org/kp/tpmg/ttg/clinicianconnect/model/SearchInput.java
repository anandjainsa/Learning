package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;

import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchFacility;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchSpecialty;

public class SearchInput implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nuid;
	private String phonebookFirstName;
	private String phonebookLastName;
	private SearchFacility searchFacility;
	private SearchSpecialty searchSpecialty;
	private int skipCount;
	private String token;
	private String kpEmployeeClass;
	private String kpRegionCode;
	private String appVersion;
	private String deviceType;
	private String iOSVersion;

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getPhonebookFirstName() {
		return phonebookFirstName;
	}

	public void setPhonebookFirstName(String phonebookFirstName) {
		this.phonebookFirstName = phonebookFirstName;
	}

	public String getPhonebookLastName() {
		return phonebookLastName;
	}

	public void setPhonebookLastName(String phonebookLastName) {
		this.phonebookLastName = phonebookLastName;
	}

	public int getSkipCount() {
		return skipCount;
	}

	public void setSkipCount(int skipCount) {
		this.skipCount = skipCount;
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

	public String getNuid() {
		return nuid;
	}

	public void setNuid(String nuid) {
		this.nuid = nuid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getKpEmployeeClass() {
		return kpEmployeeClass;
	}

	public void setKpEmployeeClass(String kpEmployeeClass) {
		this.kpEmployeeClass = kpEmployeeClass;
	}

	public String getKpRegionCode() {
		return kpRegionCode;
	}

	public void setKpRegionCode(String kpRegionCode) {
		this.kpRegionCode = kpRegionCode;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getiOSVersion() {
		return iOSVersion;
	}

	public void setiOSVersion(String iOSVersion) {
		this.iOSVersion = iOSVersion;
	}

	@Override
	public String toString() {
		return "SearchInput [nuid=" + nuid + ", phonebookFirstName=" + phonebookFirstName + ", phonebookLastName="
				+ phonebookLastName + ", searchFacility=" + searchFacility + ", searchSpecialty=" + searchSpecialty + ", skipCount=" + skipCount
				+ ", token=" + token + ", kpEmployeeClass=" + kpEmployeeClass + ", kpRegionCode=" + kpRegionCode + ", appVersion=" + appVersion
				+ ", deviceType=" + deviceType + ", iOSVersion=" + iOSVersion + "]";
	}

}
