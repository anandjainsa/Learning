package org.kp.tpmg.ttg.clinicianconnect.service;

import org.kp.tpmg.clinicianconnect.ccutilityservices.exception.CCUtilityServicesException;
import org.kp.tpmg.clinicianconnect.ccutilityservices.exception.InvalidDataFormatException;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersionInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchDetailInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchInput;
import org.kp.tpmg.ttg.clinicianconnect.model.ClinicianDetails;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookFacilityEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchDesktopEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchDesktopOutput;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchDetailEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchDetailOutput;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchOutput;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSpecialtyEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.model.Status;

public interface PhoneBookService {

	public ClinicianDetails getClinicianDetailsForNuid(String nuid);
    public PhoneBookSearchDetailEnvelope getPhoneBookSearchDetail(SearchDetailInput searchDetailInput) throws CCUtilityServicesException;
	public PhoneBookSearchDesktopEnvelope getPhoneBookSearchDesktop(SearchInput searchInput);
	public void setAppVersionAndStatusCode(SearchDetailInput searchDetailInput,
			PhoneBookSearchDetailEnvelope phoneBookSearchDetailEnvelope,
			PhoneBookSearchDetailOutput phoneBookSearchDetailOutput, Status status) throws CCUtilityServicesException;
	public void setBadRequestStatusAndLogError(Status status, Exception e);
	public void setPhoneBookSearchStatus(PhoneBookSearchDesktopEnvelope phoneBookSearchDesktopEnvelope,
			PhoneBookSearchDesktopOutput phoneBookSearchDesktopOutput, Status status);
	public PhoneBookSearchEnvelope getPhoneBookSearchResult(SearchInput searchInput);
	public void setStatusCodeForPhoneBookSearch(PhoneBookSearchEnvelope phoneBookSearchEnvelope,
			PhoneBookSearchOutput phoneBookSearchOutput, Status status);
	public PhoneBookSpecialtyEnvelope getPhoneBookSpecialtyList(AppVersionInput input) throws InvalidDataFormatException;
	public PhoneBookFacilityEnvelope getPhoneBookFacilityList(AppVersionInput input) throws InvalidDataFormatException;
}
