package org.kp.tpmg.ttg.clinicianconnect.model;


public class ClinicianContactDetails {

	private String nuid;
	private String resourceId;
	private String facilityCode;
	private String facilityName;
	private Integer specialtyId;
	private String specialtyName;
	private String emailAddress;
	private String resourcePagerPhoneNumber;
	private String resourceCellPhoneNumber;
	private String resourcePrivateInternalPhoneNumber;
	private String resourcePrivateExternalPhoneNumber;
	private String primaryLocationIndicator;
	private String clinicianFirstName;
	private String clinicianLastName;


	public ClinicianContactDetails(){
		
		nuid = null;
		resourceId = null;
		facilityCode = null;
		facilityName = null;
		specialtyId = null;
		specialtyName = null;
		emailAddress = null;
		resourcePagerPhoneNumber = null;
		resourceCellPhoneNumber = null;
		resourcePrivateInternalPhoneNumber = null;
		resourcePrivateExternalPhoneNumber = null;
		primaryLocationIndicator = null;
		clinicianFirstName = null;
		clinicianLastName = null;
		 
	}
	
	/**
	 * @return the nuid
	 */
	public String getNuid() {
		return nuid;
	}

	/**
	 * @param nuid the nuid to set
	 */
	public void setNuid(String nuid) {
		this.nuid = nuid;
	}

	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the facilityCode
	 */
	public String getFacilityCode() {
		return facilityCode;
	}

	/**
	 * @param facilityCode the facilityCode to set
	 */
	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}

	/**
	 * @return the facilityName
	 */
	public String getFacilityName() {
		return facilityName;
	}

	/**
	 * @param facilityName the facilityName to set
	 */
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	/**
	 * @return the specialtyId
	 */
	public Integer getSpecialtyId() {
		return specialtyId;
	}


	/**
	 * @param specialtyId the specialtyId to set
	 */
	public void setSpecialtyId(Integer specialtyId) {
		this.specialtyId = specialtyId;
	}


	/**
	 * @return the specialtyName
	 */
	public String getSpecialtyName() {
		return specialtyName;
	}


	/**
	 * @param specialtyName the specialtyName to set
	 */
	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}


	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the resourcePagerPhoneNumber
	 */
	public String getResourcePagerPhoneNumber() {
		return resourcePagerPhoneNumber;
	}


	/**
	 * @param resourcePagerPhoneNumber the resourcePagerPhoneNumber to set
	 */
	public void setResourcePagerPhoneNumber(String resourcePagerPhoneNumber) {
		this.resourcePagerPhoneNumber = resourcePagerPhoneNumber;
	}


	/**
	 * @return the resourceCellPhoneNumber
	 */
	public String getResourceCellPhoneNumber() {
		return resourceCellPhoneNumber;
	}


	/**
	 * @param resourceCellPhoneNumber the resourceCellPhoneNumber to set
	 */
	public void setResourceCellPhoneNumber(String resourceCellPhoneNumber) {
		this.resourceCellPhoneNumber = resourceCellPhoneNumber;
	}


	/**
	 * @return the resourcePrivateInternalPhoneNumber
	 */
	public String getResourcePrivateInternalPhoneNumber() {
		return resourcePrivateInternalPhoneNumber;
	}


	/**
	 * @param resourcePrivateInternalPhoneNumber the resourcePrivateInternalPhoneNumber to set
	 */
	public void setResourcePrivateInternalPhoneNumber(String resourcePrivateInternalPhoneNumber) {
		this.resourcePrivateInternalPhoneNumber = resourcePrivateInternalPhoneNumber;
	}


	/**
	 * @return the resourcePrivateExternalPhoneNumber
	 */
	public String getResourcePrivateExternalPhoneNumber() {
		return resourcePrivateExternalPhoneNumber;
	}


	/**
	 * @param resourcePrivateExternalPhoneNumber the resourcePrivateExternalPhoneNumber to set
	 */
	public void setResourcePrivateExternalPhoneNumber(String resourcePrivateExternalPhoneNumber) {
		this.resourcePrivateExternalPhoneNumber = resourcePrivateExternalPhoneNumber;
	}


	/**
	 * @return the primaryLocationIndicator
	 */
	public String getPrimaryLocationIndicator() {
		return primaryLocationIndicator;
	}


	/**
	 * @param primaryLocationIndicator the primaryLocationIndicator to set
	 */
	public void setPrimaryLocationIndicator(String primaryLocationIndicator) {
		this.primaryLocationIndicator = primaryLocationIndicator;
	}


	/**
	 * @return the clinicianFirstName
	 */
	public String getClinicianFirstName() {
		return clinicianFirstName;
	}


	/**
	 * @param clinicianFirstName the clinicianFirstName to set
	 */
	public void setClinicianFirstName(String clinicianFirstName) {
		this.clinicianFirstName = clinicianFirstName;
	}


	/**
	 * @return the clinicianLastName
	 */
	public String getClinicianLastName() {
		return clinicianLastName;
	}


	/**
	 * @param clinicianLastName the clinicianLastName to set
	 */
	public void setClinicianLastName(String clinicianLastName) {
		this.clinicianLastName = clinicianLastName;
	}


	@Override
	public String toString() {
		return "ClinicianContactDetails [nuid=" + nuid + ", resourceId=" + resourceId + ", clinicianFirstName="
				+ clinicianFirstName + ", clinicianLastName=" + clinicianLastName + ", facilityCode=" + facilityCode
				+ ", facilityName=" + facilityName + ", facilityName=" + specialtyId + ", specialtyName="
				+ specialtyName + ", emailAddress=" + emailAddress + ", resourcePagerPhoneNumber="
				+ resourcePagerPhoneNumber + ", resourceCellPhoneNumber=" + resourceCellPhoneNumber
				+ ", resourcePrivateInternalPhoneNumber=" + resourcePrivateInternalPhoneNumber
				+ ", resourcePrivateExternalPhoneNumber=" + resourcePrivateExternalPhoneNumber
				+ ", primaryLocationIndicator=" + primaryLocationIndicator + "]";
	}
	
}
