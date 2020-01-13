package org.kp.tpmg.ttg.clinicianconnect.model;


import java.io.Serializable;
import java.util.List;

import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.ClinicianContact;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookClinicianLocation;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookDepartment;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchFacility;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchSpecialty;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PhoneBookClinicianDesktop implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nuid;
	private String name;
	private String providerType;
	private SearchFacility facility;
	private SearchSpecialty specialty;
	private PhoneBookDepartment department;
	private String photoUrlString;
	private String homePageUrl;
	private String eMailAddress;
	
	private boolean favorite;

	private String resourceId;
	private String firstName;
	private String lastName;
	private List<ClinicianContact> contacts;
	private List<PhoneBookClinicianLocation> clinicianLocations;
	private String secondaryLocations;
	private OnCallTelepresence telepresence;
	
	private String primaryLocInd;
	private String nickName;
	private String secureTextUrl;
	
	public String getSecureTextUrl() {
		return secureTextUrl;
	}
	public void setSecureTextUrl(String secureTextUrl) {
		this.secureTextUrl = secureTextUrl;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	public String getNuid() {
		return nuid;
	}
	public void setNuid(String nuid) {
		this.nuid = nuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SearchFacility getFacility() {
		return facility;
	}
	public void setFacility(SearchFacility facility) {
		this.facility = facility;
	}
	public SearchSpecialty getSpecialty() {
		return specialty;
	}
	public void setSpecialty(SearchSpecialty specialty) {
		this.specialty = specialty;
	}
	public PhoneBookDepartment getDepartment() {
		return department;
	}
	public void setDepartment(PhoneBookDepartment department) {
		this.department = department;
	}
	public String getPhotoUrlString() {
		return photoUrlString;
	}
	public void setPhotoUrlString(String photoUrlString) {
		this.photoUrlString = photoUrlString;
	}

	
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	@JsonIgnore
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@JsonIgnore
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getHomePageUrl() {
		return homePageUrl;
	}
	public void setHomePageURL(String homePageUrl) {
		this.homePageUrl = homePageUrl;
	}
	/**
	 * @return the eMailAddress
	 */
	public String geteMailAddress() {
		return eMailAddress;
	}
	/**
	 * @param eMailAddress the eMailAddress to set
	 */
	public void seteMailAddress(String eMailAddress) {
		this.eMailAddress = eMailAddress;
	}
	public List<ClinicianContact> getContacts() {
		return contacts;
	}
	public void setContacts(List<ClinicianContact> contacts) {
		this.contacts = contacts;
	}
	
	public List<PhoneBookClinicianLocation> getClinicianLocations() {
		return clinicianLocations;
	}
	public void setClinicianLocations(List<PhoneBookClinicianLocation> clinicianLocations) {
		this.clinicianLocations = clinicianLocations;
	}
	
	public String getSecondaryLocations() {
		return secondaryLocations;
	}
	public void setSecondaryLocations(String secondaryLocations) {
		this.secondaryLocations = secondaryLocations;
	}
	
	public OnCallTelepresence getTelepresence() {
		return telepresence;
	}
	public void setTelepresence(OnCallTelepresence telepresence) {
		this.telepresence = telepresence;
	}

	public String getPrimaryLocInd() {
		return primaryLocInd;
	}
	public void setPrimaryLocInd(String primaryLocInd) {
		this.primaryLocInd = primaryLocInd;
	}

	public String getProviderType() {
		return providerType;
	}
	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}
	@JsonIgnore
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
	@Override
	public String toString() {
		return "PhoneBookClinicianDesktop [nuid=" + nuid + ", name=" + name + ", providerType=" + providerType + ", facility=" + facility + ", specialty="
				+ specialty + ", department=" + department + ", primaryLocInd=" + primaryLocInd
				+ ", secondaryLocations=" + secondaryLocations + ", photoUrlString=" + photoUrlString + ", homePageUrl="
				+ homePageUrl + ", eMailAddress=" + eMailAddress + ", favorite=" + favorite + ", resourceId="
				+ resourceId + ", firstName=" + firstName + ", lastName=" + lastName + ", telepresence=" + telepresence
				+ ", contacts=" + contacts + ", clinicianLocations=" + clinicianLocations + "]";
	}

}
