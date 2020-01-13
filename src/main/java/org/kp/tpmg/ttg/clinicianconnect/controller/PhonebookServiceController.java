package org.kp.tpmg.ttg.clinicianconnect.controller;

import static org.kp.tpmg.ttg.clinicianconnect.common.util.PhoneBookServiceConstants.GET_CLINICIAN_DETAILS_FOR_NUID;
import static org.kp.tpmg.ttg.clinicianconnect.common.util.PhoneBookServiceConstants.GET_PHONE_BOOK_CLINICIAN_DETAILS;
import static org.kp.tpmg.ttg.clinicianconnect.common.util.PhoneBookServiceConstants.GET_PHONE_BOOK_SEARCH_DESKTOP;
import static org.kp.tpmg.ttg.clinicianconnect.common.util.PhoneBookServiceConstants.INCORRECT_INPUT;
import static org.kp.tpmg.ttg.clinicianconnect.common.util.PhoneBookServiceConstants.NO_CONTENT_FOR_THE_PROVIDED_INPUT_PROBLEM;
import static org.kp.tpmg.ttg.clinicianconnect.common.util.PhoneBookServiceConstants.REQUEST_PARAMTERS;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kp.tpmg.clinicianconnect.ccutilityservices.common.ClinicianConnectLogger;
import org.kp.tpmg.clinicianconnect.ccutilityservices.common.Validator;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersionInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchDetailInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.util.CommonUtil;
import org.kp.tpmg.clinicianconnect.ccutilityservices.util.LogUtil;
import org.kp.tpmg.ttg.clinicianconnect.common.util.PhoneBookServiceConstants;
import org.kp.tpmg.ttg.clinicianconnect.model.ClinicianDetails;
import org.kp.tpmg.ttg.clinicianconnect.model.ClinicianDetailsEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.model.ClinicianDetailsJSON;
import org.kp.tpmg.ttg.clinicianconnect.model.ClinicianDetailsOutput;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookFacilityEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookFacilityJSON;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookFacilityOutput;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchDesktopEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchDesktopJSON;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchDesktopOutput;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchDetailEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchDetailJSON;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchDetailOutput;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchJSON;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchOutput;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSpecialtyEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSpecialtyJSON;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSpecialtyOutput;
import org.kp.tpmg.ttg.clinicianconnect.model.SearchCCUser;
import org.kp.tpmg.ttg.clinicianconnect.model.Status;
import org.kp.tpmg.ttg.clinicianconnect.service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PhoneBook Controller
 */
@RestController
@RequestMapping("/phoneBookController")
public class PhonebookServiceController {
	private static final Logger LOGGER = Logger.getLogger(PhonebookServiceController.class);

	@Autowired
	PhoneBookService phoneBookService;

	@Autowired
	LogUtil logUtil;

	@Autowired
	CommonUtil commonUtil;

	@PostMapping(value = "/getClinicianDetailsForNuid", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ClinicianDetailsJSON> getClinicianDetailsForNuid(@RequestBody SearchCCUser searchCCUser) {

		ClinicianConnectLogger.callEnteredLogAtService(LOGGER, GET_CLINICIAN_DETAILS_FOR_NUID,
				PhoneBookServiceConstants.PHONE_BOOK_SERVICE_CLASS_NAME);
		ClinicianDetails clinicianDetails = new ClinicianDetails();
		Status status = new Status();
		if (searchCCUser != null && StringUtils.isNotBlank(searchCCUser.getNuid())) {
			clinicianDetails = phoneBookService.getClinicianDetailsForNuid(searchCCUser.getNuid());
			if (clinicianDetails != null) {
				status.setCode(PhoneBookServiceConstants.SUCCESS_CODE_200);
				status.setMessage(PhoneBookServiceConstants.SUCCESS);
			} else {
				status.setCode(PhoneBookServiceConstants.MSG_CODE_204);
				status.setMessage("Clinician Details for the given user not found");
			}
		} else {
			status.setCode(PhoneBookServiceConstants.BAD_REQUEST);
			status.setMessage(PhoneBookServiceConstants.INCORRECT_INPUT);
		}
		ClinicianDetailsEnvelope envelope = new ClinicianDetailsEnvelope();
		envelope.setClinicianDetails(clinicianDetails);

		ClinicianDetailsOutput output = new ClinicianDetailsOutput();
		output.setClinicianDetailsEnvelope(envelope);
		output.setName(GET_CLINICIAN_DETAILS_FOR_NUID);
		output.setStatus(status);

		ClinicianDetailsJSON json = new ClinicianDetailsJSON();
		json.setService(output);
		ClinicianConnectLogger.callExitedLogAtService(LOGGER, GET_CLINICIAN_DETAILS_FOR_NUID,
				PhoneBookServiceConstants.PHONE_BOOK_SERVICE_CLASS_NAME);

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	@PostMapping(value = "/getPhoneBookClinicianDetails", consumes = "application/json", produces = "application/json")
	public ResponseEntity<PhoneBookSearchDetailJSON> getPhoneBookClinicianDetails(
			@RequestBody SearchDetailInput searchDetailInput) {
		ClinicianConnectLogger.callEnteredLogAtService(LOGGER, GET_PHONE_BOOK_CLINICIAN_DETAILS,
				PhoneBookServiceConstants.PHONE_BOOK_SERVICE_CLASS_NAME);
		PhoneBookSearchDetailEnvelope phoneBookSearchDetailEnvelope;
		PhoneBookSearchDetailOutput phoneBookSearchDetailOutput = new PhoneBookSearchDetailOutput();
		PhoneBookSearchDetailJSON phoneBookSearchDetailJSON = new PhoneBookSearchDetailJSON();
		Status status = new Status();
		phoneBookSearchDetailOutput.setName(GET_PHONE_BOOK_CLINICIAN_DETAILS);
		try {
			if (searchDetailInput == null || StringUtils.isBlank(searchDetailInput.getResourceId())) {
				status.setCode(PhoneBookServiceConstants.BAD_REQUEST);
				status.setMessage(PhoneBookServiceConstants.INCORRECT_INPUT);
				return new ResponseEntity<>(phoneBookSearchDetailJSON, HttpStatus.OK);
			}
			phoneBookSearchDetailEnvelope = phoneBookService.getPhoneBookSearchDetail(searchDetailInput);
			if (phoneBookSearchDetailEnvelope != null) {
				phoneBookService.setAppVersionAndStatusCode(searchDetailInput, phoneBookSearchDetailEnvelope,
						phoneBookSearchDetailOutput, status);
				phoneBookSearchDetailOutput.setiOSVersion(commonUtil.getIOSVersion(searchDetailInput.getDeviceType(),
						searchDetailInput.getiOSVersion(), null));
				phoneBookSearchDetailOutput.setEnvelope(phoneBookSearchDetailEnvelope);
			} else {
				status.setCode(PhoneBookServiceConstants.MSG_CODE_204);
				status.setMessage(PhoneBookServiceConstants.NO_CONTENT_FOR_THE_PROVIDED_INPUT_PROBLEM);
			}
		} catch (Exception e) {
			phoneBookService.setBadRequestStatusAndLogError(status, e);
		}
		phoneBookSearchDetailOutput.setStatus(status);
		phoneBookSearchDetailJSON.setService(phoneBookSearchDetailOutput);
		ClinicianConnectLogger.callExitedLog(LOGGER, GET_PHONE_BOOK_CLINICIAN_DETAILS,
				PhoneBookServiceConstants.PHONE_BOOK_SERVICE_CLASS_NAME);
		return new ResponseEntity<>(phoneBookSearchDetailJSON, HttpStatus.OK);
	}

	@PostMapping(value = "/getPhoneBookSearchDesktop", consumes = "application/json", produces = "application/json")
	public ResponseEntity<PhoneBookSearchDesktopJSON> getPhoneBookSearchDesktop(@RequestBody SearchInput searchInput) {
		ClinicianConnectLogger.callEnteredLogAtService(LOGGER, GET_PHONE_BOOK_SEARCH_DESKTOP,
				PhoneBookServiceConstants.PHONE_BOOK_SERVICE_CLASS_NAME);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(REQUEST_PARAMTERS + logUtil.logRequestParam(searchInput));
		}
		PhoneBookSearchDesktopEnvelope phoneBookSearchDesktopEnvelope = null;
		PhoneBookSearchDesktopOutput phoneBookSearchDesktopOutput = new PhoneBookSearchDesktopOutput();
		PhoneBookSearchDesktopJSON phoneBookSearchDesktopJSON = new PhoneBookSearchDesktopJSON();
		Status status = new Status();
		Validator validator = new Validator();
		try {
			if (searchInput != null) {
				validator.validateSearchInput(searchInput);
				phoneBookSearchDesktopEnvelope = phoneBookService.getPhoneBookSearchDesktop(searchInput);
				if (phoneBookSearchDesktopEnvelope != null) {
					phoneBookService.setPhoneBookSearchStatus(phoneBookSearchDesktopEnvelope,
							phoneBookSearchDesktopOutput, status);
					phoneBookSearchDesktopOutput.setEnvelope(phoneBookSearchDesktopEnvelope);
				} else {
					status.setCode("204");
					status.setMessage(NO_CONTENT_FOR_THE_PROVIDED_INPUT_PROBLEM);
				}
			} else {
				status.setCode("204");
				status.setMessage(INCORRECT_INPUT);
				LOGGER.warn(INCORRECT_INPUT);
			}
		} catch (Exception e) {
			phoneBookService.setBadRequestStatusAndLogError(status, e);
		}
		phoneBookSearchDesktopOutput.setName(GET_PHONE_BOOK_SEARCH_DESKTOP);
		phoneBookSearchDesktopOutput.setStatus(status);

		phoneBookSearchDesktopJSON.setService(phoneBookSearchDesktopOutput);
		ClinicianConnectLogger.callExitedLogAtService(LOGGER, GET_PHONE_BOOK_SEARCH_DESKTOP,
				PhoneBookServiceConstants.PHONE_BOOK_SERVICE_CLASS_NAME);
		return new ResponseEntity<>(phoneBookSearchDesktopJSON, HttpStatus.OK);

	}

	@PostMapping(value = "/getPhoneBookSearch", consumes = "application/json", produces = "application/json")
	public ResponseEntity<PhoneBookSearchJSON> getPhoneBookSearch(@RequestBody SearchInput searchInput) {
		LOGGER.info("getPhoneBookSearch Controller");
		ClinicianConnectLogger.callEnteredLogAtService(LOGGER, PhoneBookServiceConstants.GET_PHONE_BOOK_SEARCH,
				PhoneBookServiceConstants.PHONE_BOOK_SERVICE_CLASS_NAME);

		PhoneBookSearchEnvelope phoneBookSearchEnvelope;
		PhoneBookSearchOutput phoneBookSearchOutput = new PhoneBookSearchOutput();
		PhoneBookSearchJSON phoneBookSearchJSON = new PhoneBookSearchJSON();
		Status status = new Status();
		phoneBookSearchOutput.setName(PhoneBookServiceConstants.GET_PHONE_BOOK_SEARCH);
		phoneBookSearchOutput.setStatus(status);
		phoneBookSearchJSON.setService(phoneBookSearchOutput);

		if (searchInput == null) {
			status.setCode(PhoneBookServiceConstants.BAD_REQUEST);
			status.setMessage(PhoneBookServiceConstants.INCORRECT_INPUT);
			ClinicianConnectLogger.callExitedLogAtService(LOGGER, PhoneBookServiceConstants.GET_PHONE_BOOK_SEARCH,
					PhoneBookServiceConstants.PHONE_BOOK_SERVICE_CLASS_NAME);
			return new ResponseEntity<>(phoneBookSearchJSON, HttpStatus.OK);
		}
		try {
			phoneBookSearchEnvelope = phoneBookService.getPhoneBookSearchResult(searchInput);
			if (phoneBookSearchEnvelope != null) {
				status.setCode(PhoneBookServiceConstants.SUCCESS_CODE_200);
				status.setMessage(PhoneBookServiceConstants.SUCCESS);
				phoneBookService.setStatusCodeForPhoneBookSearch(phoneBookSearchEnvelope, phoneBookSearchOutput,
						status);
				phoneBookSearchOutput.setiOSVersion(phoneBookSearchEnvelope.getiOSVersion());
				phoneBookSearchOutput.setEnvelope(phoneBookSearchEnvelope);
			} else {
				status.setCode(PhoneBookServiceConstants.MSG_CODE_204);
				status.setMessage("Clinician Details for the given user not found");
			}
		} catch (Exception e) {
			phoneBookService.setBadRequestStatusAndLogError(status, e);
		}
		ClinicianConnectLogger.callExitedLogAtService(LOGGER, PhoneBookServiceConstants.GET_PHONE_BOOK_SEARCH,
				PhoneBookServiceConstants.PHONE_BOOK_SERVICE_CLASS_NAME);
		return new ResponseEntity<>(phoneBookSearchJSON, HttpStatus.OK);
	}

	@PostMapping(value = "/getPhoneBookSpecialtyList", consumes = "application/json", produces = "application/json")
	public ResponseEntity<PhoneBookSpecialtyJSON> getPhoneBookSpecialtyList(@RequestBody AppVersionInput input) {
		if (LOGGER.isDebugEnabled()) {
			ClinicianConnectLogger.callEnteredLogAtService(LOGGER, PhoneBookServiceConstants.GET_PHONE_BOOK_SPECIALTY_LIST,
					PhoneBookServiceConstants.PHONE_BOOK_SERVICE_CLASS_NAME);
		}
		Status status = new Status();
		Validator validator = new Validator();
		PhoneBookSpecialtyOutput phoneBookSpecialtyOutput = new PhoneBookSpecialtyOutput();
		try {
			validator.validateAppVersionInput(input);
			PhoneBookSpecialtyEnvelope envelope = phoneBookService.getPhoneBookSpecialtyList(input);
			if (envelope != null && envelope.getAppVersion() != null && envelope.getAppVersion().getVersion() != null) {
				status.setCode(PhoneBookServiceConstants.SUCCESS_CODE_200);
				status.setMessage(PhoneBookServiceConstants.SUCCESS);
				phoneBookSpecialtyOutput.setAppVersion(envelope.getAppVersion());
				phoneBookSpecialtyOutput.setiOSVersion(envelope.getiOSVersion());
				phoneBookSpecialtyOutput.setEnvelope(envelope);
			} else {
				status.setCode(PhoneBookServiceConstants.MSG_CODE_204);
				status.setMessage(PhoneBookServiceConstants.PROBLEM_WHILE_RETRIEVING_THE_DATA_FROM_DATABASE);
			}
		} catch (Exception e) {
			status.setCode(PhoneBookServiceConstants.INTERNAL_SERVER_ERROR_CODE_500);
			status.setMessage(PhoneBookServiceConstants.INTERNAL_SERVER_ERROR);
			LOGGER.error("ERROR : Error while fetching phoneBookSpecialityList in getPhoneBookSpecialtyList action");
		}
		phoneBookSpecialtyOutput.setName(PhoneBookServiceConstants.GET_PHONE_BOOK_SPECIALTY_LIST);
		phoneBookSpecialtyOutput.setStatus(status);
		PhoneBookSpecialtyJSON phoneBookSpecialtyJSON = new PhoneBookSpecialtyJSON();
		phoneBookSpecialtyJSON.setService(phoneBookSpecialtyOutput);
		if (LOGGER.isDebugEnabled()) {
			ClinicianConnectLogger.callExitedLogAtService(LOGGER, PhoneBookServiceConstants.GET_PHONE_BOOK_SPECIALTY_LIST,
					PhoneBookServiceConstants.PHONE_BOOK_SERVICE_CLASS_NAME);
		}
		return new ResponseEntity<>(phoneBookSpecialtyJSON, HttpStatus.OK);
	}

	@PostMapping(value = "/getPhoneBookFacilityList", consumes = "application/json", produces = "application/json")
	public ResponseEntity<PhoneBookFacilityJSON> getPhoneBookFacilityList(@RequestBody AppVersionInput input) {
		if (LOGGER.isDebugEnabled()) {
			ClinicianConnectLogger.callEnteredLogAtService(LOGGER, PhoneBookServiceConstants.GET_PHONE_BOOK_FACILITY_LIST,
					PhoneBookServiceConstants.PHONE_BOOK_SERVICE_CLASS_NAME);
		}
		LOGGER.info("Appversion Input  >>>>>>>>>> " + input);
		Status status = new Status();
		Validator validator = new Validator();
		PhoneBookFacilityOutput phoneBookFacilityOutput = new PhoneBookFacilityOutput();
		try {
			validator.validateAppVersionInput(input);
			PhoneBookFacilityEnvelope envelope = phoneBookService.getPhoneBookFacilityList(input);
			if (envelope != null && envelope.getAppVersion() != null && envelope.getAppVersion().getVersion() != null) {
				status.setCode(PhoneBookServiceConstants.SUCCESS_CODE_200);
				status.setMessage(PhoneBookServiceConstants.SUCCESS);
				phoneBookFacilityOutput.setAppVersion(envelope.getAppVersion());
				phoneBookFacilityOutput.setEnvelope(envelope);
				phoneBookFacilityOutput.setiOSVersion(envelope.getiOSVersion());
			} else {
				status.setCode(PhoneBookServiceConstants.MSG_CODE_204);
				status.setMessage(PhoneBookServiceConstants.PROBLEM_WHILE_RETRIEVING_THE_DATA_FROM_DATABASE);
			}
		} catch (Exception e) {
			status.setCode(PhoneBookServiceConstants.INTERNAL_SERVER_ERROR_CODE_500);
			status.setMessage(PhoneBookServiceConstants.INTERNAL_SERVER_ERROR);
			LOGGER.error("ERROR : Error while fetching phoneBookFacilityList in getPhoneBookFacilityList action");
		}
		phoneBookFacilityOutput.setName(PhoneBookServiceConstants.GET_PHONE_BOOK_FACILITY_LIST);
		phoneBookFacilityOutput.setStatus(status);
		PhoneBookFacilityJSON phoneBookFacilityJSON = new PhoneBookFacilityJSON();
		phoneBookFacilityJSON.setService(phoneBookFacilityOutput);
		if (LOGGER.isDebugEnabled()) {
			ClinicianConnectLogger.callExitedLogAtService(LOGGER, PhoneBookServiceConstants.GET_PHONE_BOOK_FACILITY_LIST,
					PhoneBookServiceConstants.PHONE_BOOK_SERVICE_CLASS_NAME);
		}
		return new ResponseEntity<>(phoneBookFacilityJSON, HttpStatus.OK);
	}
}
