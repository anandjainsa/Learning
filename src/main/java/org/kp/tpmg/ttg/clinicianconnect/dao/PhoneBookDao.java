package org.kp.tpmg.ttg.clinicianconnect.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookFacility;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookSpecialty;
import org.kp.tpmg.clinicianconnect.ccutilityservices.exception.InvalidDataFormatException;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersionInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.ClinicianPhoneObject;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchInput;
import org.kp.tpmg.ttg.clinicianconnect.model.ClinicianDetails;

public interface PhoneBookDao {

	ClinicianDetails getClinicianDetailsForNuid(String nuid);
	String getNickNameUsingResourceId(String name);
	List<ClinicianPhoneObject> getClinicianContacts(String resourceId);
	public Map<String, Object> getPhoneBookSearchDesktop(final String firstName,final String lastName, final String speciality,
			final String facility,final SearchInput searchInput);
	public Map<String, List<String>> preparePhonBookCliniciansOtherLocations( HashSet<String> nuIds);
	public Map<String, Object> getPhoneBookSearchResult(final String firstName, final String lastName, final String facilityCode, final String specialty, final int skipCount);
	public List<PhoneBookSpecialty> getPhoneBookSpecialtyList(AppVersionInput input) throws InvalidDataFormatException;
	public List<PhoneBookFacility> getPhoneBookFacilityList(AppVersionInput input) throws InvalidDataFormatException;
	
}
