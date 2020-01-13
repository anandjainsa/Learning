package org.kp.tpmg.ttg.clinicianconnect.common.util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class ClinicianPhoneObject {

	private String resourceId;
	private String facilityCode;
	private String clinicianCellPhoneNumber;
	private String clinicianPageNumber;
	private String resourceCellPhoneNumber;
	private String resourceNurseInternalPhoneNumber;
	private String resourceNurseExternalPhoneNumber;
	private String resourcePrivateInternalPhoneNumber;
	private String resourcePrivateExternalPhoneNumber;
	private String resourcePagerPhoneNumber;
	private String nuid;
	private String clinicianName;
	private String lastName;
	private String areaSpecialtyName;
	private String emailAddress;


	public ClinicianPhoneObject(){
		
		 resourceId =null;
		 facilityCode =null;
		 clinicianCellPhoneNumber =null;
		 clinicianPageNumber =null;
		 resourceCellPhoneNumber =null;
		 resourceNurseInternalPhoneNumber =null;
		 resourceNurseExternalPhoneNumber =null;
		 resourcePrivateInternalPhoneNumber =null;
		 resourcePrivateExternalPhoneNumber=null;
		 resourcePagerPhoneNumber=null;
		 nuid=null;
		 clinicianName=null;
		 lastName=null;
		 areaSpecialtyName = null;
		 
	}
	
	@XmlElement(name="ResourceId")
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	@XmlElement(name="FacilityCode")
	public String getFacilityCode() {
		return facilityCode;
	}
	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}
	@XmlElement(name="ClinicianCellPhoneNumber")
	public String getClinicianCellPhoneNumber() {

		return clinicianCellPhoneNumber;
	}
	public void setClinicianCellPhoneNumber(String clinicianCellPhoneNumber) {
		this.clinicianCellPhoneNumber = clinicianCellPhoneNumber;
	}
	@XmlElement(name="ClinicianPageNumber")
	public String getClinicianPageNumber() {
	
		return clinicianPageNumber;
	}
	public void setClinicianPageNumber(String clinicianPageNumber) {
		this.clinicianPageNumber = clinicianPageNumber;
	}
	@XmlElement(name="ResourceCellPhoneNumber")
	public String getResourceCellPhoneNumber() {
		return resourceCellPhoneNumber;
	}
	public void setResourceCellPhoneNumber(String resourceCellPhoneNumber) {
		this.resourceCellPhoneNumber = resourceCellPhoneNumber;
	}
	@XmlElement(name="ResourceNurseInternalPhoneNumber")
	public String getResourceNurseInternalPhoneNumber() {
		return resourceNurseInternalPhoneNumber;
	}
	public void setResourceNurseInternalPhoneNumber(
			String resourceNurseInternalPhoneNumber) {
		this.resourceNurseInternalPhoneNumber = resourceNurseInternalPhoneNumber;
	}
	@XmlElement(name="ResourceNurseExternalPhoneNumber")
	public String getResourceNurseExternalPhoneNumber() {
		return resourceNurseExternalPhoneNumber;
	}
	public void setResourceNurseExternalPhoneNumber(
			String resourceNurseExternalPhoneNumber) {
		this.resourceNurseExternalPhoneNumber = resourceNurseExternalPhoneNumber;
	}
	@XmlElement(name="ResourcePrivateInternalPhoneNumber")
	public String getResourcePrivateInternalPhoneNumber() {
		return resourcePrivateInternalPhoneNumber;
	}
	public void setResourcePrivateInternalPhoneNumber(
			String resourcePrivateInternalPhoneNumber) {
		this.resourcePrivateInternalPhoneNumber = resourcePrivateInternalPhoneNumber;
	}
	@XmlElement(name="ResourcePrivateExternalPhoneNumber")
	public String getResourcePrivateExternalPhoneNumber() {
		return resourcePrivateExternalPhoneNumber;
	}
	public void setResourcePrivateExternalPhoneNumber(
			String resourcePrivateExternalPhoneNumber) {
		this.resourcePrivateExternalPhoneNumber = resourcePrivateExternalPhoneNumber;
	}
	@XmlElement(name="ResourcePagerPhoneNumber")
	public String getResourcePagerPhoneNumber() {
		return resourcePagerPhoneNumber;
	}
	public void setResourcePagerPhoneNumber(String resourcePagerPhoneNumber) {
		this.resourcePagerPhoneNumber = resourcePagerPhoneNumber;
	}

	@XmlTransient
	public String getNuid() {
		return nuid;
	}


	public void setNuid(String nuid) {
		this.nuid = nuid;
	}
	@XmlTransient
	public String getClinicianName() {
		return clinicianName;
	}


	public void setClinicianName(String clinicianName) {
		this.clinicianName = clinicianName;
	}

	@XmlTransient
	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
    
	@XmlElement(name="AreaSpecialtyName")
	public String getAreaSpecialtyName() {
		return areaSpecialtyName;
	}
	
	
	public void setAreaSpecialtyName(String areaSpecialtyName) {
		this.areaSpecialtyName = areaSpecialtyName;
	}

	@Override
	public String toString() {
		return "ClinicianPhoneObject [resourceId=" + resourceId
				+ ", facilityCode=" + facilityCode
				+ ", clinicianCellPhoneNumber=" + clinicianCellPhoneNumber
				+ ", clinicianPageNumber=" + clinicianPageNumber
				+ ", resourceCellPhoneNumber=" + resourceCellPhoneNumber
				+ ", resourceNurseInternalPhoneNumber="
				+ resourceNurseInternalPhoneNumber
				+ ", resourceNurseExternalPhoneNumber="
				+ resourceNurseExternalPhoneNumber
				+ ", resourcePrivateInternalPhoneNumber="
				+ resourcePrivateInternalPhoneNumber
				+ ", resourcePrivateExternalPhoneNumber="
				+ resourcePrivateExternalPhoneNumber
				+ ", resourcePagerPhoneNumber=" + resourcePagerPhoneNumber
				+ ", nuid=" + nuid + ", clinicianName=" + clinicianName
				+ ", lastName=" + lastName + ", areaSpecialtyName="
				+ areaSpecialtyName + "]";
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
