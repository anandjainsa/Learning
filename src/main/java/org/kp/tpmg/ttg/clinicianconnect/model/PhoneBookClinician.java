package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;
import java.util.List;

import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.ClinicianContact;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchFacility;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchSpecialty;

public class PhoneBookClinician implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nuid;
	private String firstName;
	private String lastName;
	private String middleName;
	private String profTitle;
	private String nickName;
	private String name;
	private String providerType;
	private SearchFacility facility;
	private SearchSpecialty specialty;
	private String photoUrlString;
	private String homePageUrl;
	private boolean favorite;
	private String resourceId;
	private String otherLocations;
	private List<ClinicianContact> contacts;


	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getProfTitle() {
		return profTitle;
	}

	public void setProfTitle(String profTitle) {
		this.profTitle = profTitle;
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

	public String getPhotoUrlString() {
		return photoUrlString;
	}

	public void setPhotoUrlString(String photoUrlString) {
		this.photoUrlString = photoUrlString;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHomePageUrl() {
		return homePageUrl;
	}

	public void setHomePageUrl(String homePageUrl) {
		this.homePageUrl = homePageUrl;
	}

	public String getOtherLocations() {
		return otherLocations;
	}

	public void setOtherLocations(String otherLocations) {
		this.otherLocations = otherLocations;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public List<ClinicianContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<ClinicianContact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return "PhoneBookClinician [nuid=" + nuid + ", name=" + name + ", providerType=" + providerType + ", facility=" + facility
				+ ", specialty=" + specialty + ", photoUrlString=" + photoUrlString + ", homePageUrl=" + homePageUrl + ", favorite="
				+ favorite + ", resourceId=" + resourceId + ", firstName=" + firstName + ", lastName=" + lastName + ", otherLocations= "
				+ otherLocations + " ]";
	}

	
}
