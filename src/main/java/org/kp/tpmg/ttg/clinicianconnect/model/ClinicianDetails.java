package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;

public class ClinicianDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nuid;
	private String firstName;
	private String lastName;
	private String primaryFacility;
	private String primarySpecialty;
	private String hasAccessToCC;
	private String sourceType;
	private String role;
	private String isUserExists;
	private String available;
	private String medCenterContactsManualOverRideIndicator;
	private String activeInd;

	
	public String getNuid() {
		return nuid;
	}

	public void setNuid(String nuid) {
		this.nuid = nuid;
	}

	public String getHasAccessToCC() {
		return hasAccessToCC;
	}

	public void setHasAccessToCC(String hasAccessToCC) {
		this.hasAccessToCC = hasAccessToCC;
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

	public String getPrimaryFacility() {
		return primaryFacility;
	}

	public void setPrimaryFacility(String primaryFacility) {
		this.primaryFacility = primaryFacility;
	}

	public String getPrimarySpecialty() {
		return primarySpecialty;
	}

	public void setPrimarySpecialty(String primarySpecialty) {
		this.primarySpecialty = primarySpecialty;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getIsUserExists() {
		return isUserExists;
	}

	public void setIsUserExists(String isUserExists) {
		this.isUserExists = isUserExists;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getMedCenterContactsManualOverRideIndicator() {
		return medCenterContactsManualOverRideIndicator;
	}

	public void setMedCenterContactsManualOverRideIndicator(String medCenterContactsManualOverRideIndicator) {
		this.medCenterContactsManualOverRideIndicator = medCenterContactsManualOverRideIndicator;
	}
	public String getActiveInd() {
		return activeInd;
	}

	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
	}

	@Override
	public String toString() {
		return "ClinicianDetails [nuid=" + nuid + ", firstName=" + firstName + ", lastName=" + lastName + ", primaryFacility=" + primaryFacility
				+ ", primarySpecialty=" + primarySpecialty + ", hasAccessToCC=" + hasAccessToCC + ", sourceType=" + sourceType + ", role=" + role
				+ ", isUserExists=" + isUserExists + ", available=" + available + ", medCenterContactsManualOverRideIndicator="
				+ medCenterContactsManualOverRideIndicator + ", activeInd=" + activeInd + "]";
	}
	
}
