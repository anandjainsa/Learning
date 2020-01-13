package org.kp.tpmg.ttg.clinicianconnect.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.kp.tpmg.clinicianconnect.cclib.mdoprovider.MdoProviderUtil;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.ClinicianContact;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.ClinicianDetailProvider;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.ClinicianProvider;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookClinicianLocation;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookDepartment;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookFacility;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookSpecialty;
import org.kp.tpmg.clinicianconnect.ccutilityservices.common.ClinicianConnectLogger;
import org.kp.tpmg.clinicianconnect.ccutilityservices.common.ClinicianConnectUtilityMethods;
import org.kp.tpmg.clinicianconnect.ccutilityservices.common.StringUtil;
import org.kp.tpmg.clinicianconnect.ccutilityservices.exception.CCUtilityServicesException;
import org.kp.tpmg.clinicianconnect.ccutilityservices.exception.InvalidDataFormatException;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersion;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersionInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.ClinicianPhoneObject;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchDetailInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchFacility;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchSpecialty;
import org.kp.tpmg.clinicianconnect.ccutilityservices.util.CommonUtil;
import org.kp.tpmg.ttg.clinicianconnect.common.util.PhoneBookServiceConstants;
import org.kp.tpmg.ttg.clinicianconnect.dao.PhoneBookDao;
import org.kp.tpmg.ttg.clinicianconnect.model.ClinicianContactDetails;
import org.kp.tpmg.ttg.clinicianconnect.model.ClinicianDetails;
import org.kp.tpmg.ttg.clinicianconnect.model.OnCallTelepresence;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookClinician;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookClinicianDesktop;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookFacilityEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchDesktopEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchDesktopOutput;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchDetailEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchDetailOutput;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchOutput;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSpecialtyEnvelope;
import org.kp.tpmg.ttg.clinicianconnect.model.Status;
import org.kp.tpmg.ttg.clinicianconnect.service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneBookServiceImpl implements PhoneBookService {

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	PhoneBookDao phoneBookDao;

	private static final Logger LOGGER = Logger.getLogger(PhoneBookServiceImpl.class);

	@Override
	public ClinicianDetails getClinicianDetailsForNuid(String nuid) {
		LOGGER.debug("entered into getClinicianDetailsForNuid action in PhoneBookServiceImpl");
		return phoneBookDao.getClinicianDetailsForNuid(nuid);
	}

	private void setAppVersion(SearchDetailInput searchDetailInput, PhoneBookSearchDetailEnvelope clncnDetail) {
		try {
			AppVersion appversion = commonUtil.getAppVersion(searchDetailInput.getAppVersion());
			if (appversion != null) {
				clncnDetail.setAppVersion(appversion);
			}
		} catch (CCUtilityServicesException e1) {
			LOGGER.error("ERROR: PhonebookServices: problem in PhoneBookServiceImpl/getAppVersion", e1);
		}
	}

	@Override
	public PhoneBookSearchDetailEnvelope getPhoneBookSearchDetail(SearchDetailInput searchDetailInput)
			throws CCUtilityServicesException {
		Calendar startTime = Calendar.getInstance();
		ClinicianConnectLogger.logPerformanceMessage(LOGGER, "Starting getPhoneBookSearchDetail() service : ", startTime);
		PhoneBookSearchDetailEnvelope clncnDetail = new PhoneBookSearchDetailEnvelope();
		setAppVersion(searchDetailInput, clncnDetail);
		clncnDetail.setiOSVersion(commonUtil.getIOSVersion(searchDetailInput.getDeviceType(),
				searchDetailInput.getiOSVersion(), "getPhoneBookSearchDetail"));
		if (StringUtils.isNotBlank(searchDetailInput.getResourceId())) {
			List<ClinicianPhoneObject> clnPhoneList = null;
			ClinicianDetailProvider clinicianDetailProvider = null;
			try {
				Calendar cal1 = Calendar.getInstance();

				String provider = PhoneBookServiceConstants.PROVIDER_BY_RESOURCE_IDS_API_NAME;
				MdoProviderUtil mdoProviderUtil = commonUtil.getMdoProviderUtil(provider);
				try {
					clinicianDetailProvider = mdoProviderUtil.getClinicianDetailByResourceIdsDirectRestCall(
							new String[] { searchDetailInput.getResourceId() });
				} catch (Exception e) {
					LOGGER.error(
							"ERROR: PhonebookServices: PhoneBookSearchDetail - Unable to get contacts or ttgprovider for "
									+ searchDetailInput.getResourceId(),
							e);
				}

				Calendar cal2 = Calendar.getInstance();
				ClinicianConnectLogger.logPerformanceTime(LOGGER, "Time spent in getting TtgProviderService Data : ", cal1,
						cal2);
				clnPhoneList = phoneBookDao.getClinicianContacts(searchDetailInput.getResourceId());
				Calendar cal3 = Calendar.getInstance();
				ClinicianConnectLogger.logPerformanceTime(LOGGER,
						"Time spent in executing getClinicianContacts() service : ", cal2, cal3);
			} catch (Exception e) {
				LOGGER.error("ERROR: PhonebookServices: PhoneBookSearchDetail - Unable to get contacts or ttgprovider for "
						+ searchDetailInput.getResourceId(), e);
			}

			if (clinicianDetailProvider != null) {
				String nickName = phoneBookDao.getNickNameUsingResourceId(searchDetailInput.getResourceId().trim());
				if (StringUtils.isNotBlank(nickName)) {
					clncnDetail.setNickName(nickName.trim());
				}
				populatePhoneBookSearchDetailEnvelope(searchDetailInput.getResourceId(), clncnDetail, clnPhoneList,
						clinicianDetailProvider);
			}
		}
		return clncnDetail;
	}

	private void populatePhoneBookSearchDetailEnvelope(String resourceId, PhoneBookSearchDetailEnvelope clncnDetail,
			List<ClinicianPhoneObject> clnPhoneList, ClinicianDetailProvider clinicianDetailProvider) {

		setNamesAndTitile(clncnDetail, clinicianDetailProvider);
		setFacilityCodeAndSpecialityCode(clncnDetail, clinicianDetailProvider);
		setPrimaryDepartmentCode(clncnDetail, clinicianDetailProvider);
		setDisplayName(clncnDetail, clinicianDetailProvider);
		setResourceIdAndNuid(clncnDetail, clinicianDetailProvider);
		OnCallTelepresence tele = setOtherDetails(clncnDetail, clinicianDetailProvider);

		if (clinicianDetailProvider.getProviderLocations() != null
				&& !clinicianDetailProvider.getProviderLocations().isEmpty()) {
			List<PhoneBookClinicianLocation> detailClinicianLocations = new ArrayList<>();
			List<String> facCode = new ArrayList<>();

			Calendar cal4 = Calendar.getInstance();

			for (PhoneBookClinicianLocation providerLocations : clinicianDetailProvider.getProviderLocations()) {

				List<ClinicianContact> contacts = null;
				if (facCode.contains(providerLocations.getFacility().getCode())
						&& StringUtils.isNotBlank(providerLocations.getPrimaryIndicator())
						&& "N".equals(providerLocations.getPrimaryIndicator())) {
					continue;
				}

				// set contacts in PhoneBookClinicianDetailEnvelope
				if (clnPhoneList != null && !clnPhoneList.isEmpty()) {
					for (ClinicianPhoneObject phoneObject : clnPhoneList) {
						contacts = new ArrayList<>();
						if (providerLocations.getFacility().getCode().equals(phoneObject.getFacilityCode())) {

							setContacts(clinicianDetailProvider, providerLocations, contacts, phoneObject);
							if (contacts != null && !contacts.isEmpty()) {
								facCode.add(providerLocations.getFacility().getCode());
								break;
							}

						} else if ("Y".equals(providerLocations.getPrimaryIndicator())) {

							if (validatePhoneObjectContatcs(phoneObject)) {
								ClinicianContact contact = new ClinicianContact();
								contact.setType(PhoneBookServiceConstants.MOBILE);
								contact.setNumber(StringUtil.getNumberFormat(phoneObject.getResourceCellPhoneNumber()));
								LOGGER.debug("MobileNumber is avaliable from clinicianphone where facilitycode is null");
								contacts.add(contact);
							}
							prepareAndAddContact(clinicianDetailProvider, providerLocations, contacts);
						}
					}
				} else {
					contacts = new ArrayList<>();
					prepareAndAddContact(clinicianDetailProvider, providerLocations, contacts);
				}

				if (contacts != null && !contacts.isEmpty()) {
					providerLocations.setContacts(contacts);
					detailClinicianLocations.add(providerLocations);
				} else {
					if (StringUtils.isNotBlank(providerLocations.getPrimaryIndicator())
							&& "Y".equals(providerLocations.getPrimaryIndicator())) {
						facCode.add(providerLocations.getFacility().getCode());
						detailClinicianLocations.add(providerLocations);
					}
				}
			}
			Calendar cal5 = Calendar.getInstance();
			ClinicianConnectLogger.logPerformanceTime(LOGGER, "Time spent in creating/formatting Contact Data : ", cal4,
					cal5);
			if (detailClinicianLocations != null && !detailClinicianLocations.isEmpty())
				clncnDetail.setClinicianLocations(detailClinicianLocations);
		} else {
			LOGGER.debug("PhoneBookSearchDetail - ProviderLocation is not available for " + resourceId);
		}

		Calendar cal6 = Calendar.getInstance();

		if (StringUtils.isNotBlank(clinicianDetailProvider.getEmailAddress())) {
			clncnDetail.setSecureTextUrl(PhoneBookServiceConstants.CORETEXT_URL
					+ PhoneBookServiceConstants.EMAIL_ADDRESS_SMALL + "=" + clinicianDetailProvider.getEmailAddress()
					+ PhoneBookServiceConstants.CORTEXT_START_TYPING_MSG);
		} else {
			clncnDetail.setSecureTextUrl(
					PhoneBookServiceConstants.CORETEXT_URL + PhoneBookServiceConstants.EMAIL_ADDRESS_WITH_NULL_STRING
							+ PhoneBookServiceConstants.CORTEXT_START_TYPING_MSG);
		}
		tele.setText("true");

		Calendar cal7 = Calendar.getInstance();
		ClinicianConnectLogger.logPerformanceTime(LOGGER, "Time spent in Cortext Call : ", cal6, cal7);
	}

	private void setNamesAndTitile(PhoneBookSearchDetailEnvelope clncnDetail,
			ClinicianDetailProvider clinicianDetailProvider) {
		if (StringUtils.isNotBlank(clinicianDetailProvider.getFirsName())) {
			clncnDetail.setFirstName(clinicianDetailProvider.getFirsName().trim());
		}
		if (StringUtils.isNotBlank(clinicianDetailProvider.getLastName())) {
			clncnDetail.setLastName(clinicianDetailProvider.getLastName().trim());
		}
		if (StringUtils.isNotBlank(clinicianDetailProvider.getProfTitle())) {
			clncnDetail.setProfTitle(clinicianDetailProvider.getProfTitle().trim());
		}
		if (StringUtils.isNotBlank(clinicianDetailProvider.getMiddleName())) {
			clncnDetail.setMiddleName(clinicianDetailProvider.getMiddleName().trim());
		}
	}

	private void setFacilityCodeAndSpecialityCode(PhoneBookSearchDetailEnvelope clncnDetail,
			ClinicianDetailProvider clinicianDetailProvider) {
		if (StringUtils.isNotBlank(clinicianDetailProvider.getPrimaryFacilityCode())
				&& StringUtils.isNotBlank(clinicianDetailProvider.getPrimaryFacilityName())) {
			PhoneBookFacility fac = new PhoneBookFacility();
			fac.setCode(clinicianDetailProvider.getPrimaryFacilityCode());
			fac.setName(clinicianDetailProvider.getPrimaryFacilityName());
			clncnDetail.setFacility(fac);
		}
		if (StringUtils.isNotBlank(clinicianDetailProvider.getPrimarySpecialtyCode())
				&& StringUtils.isNotBlank(clinicianDetailProvider.getPrimarySpecialtyName())) {
			PhoneBookSpecialty sp = new PhoneBookSpecialty();
			sp.setCode(clinicianDetailProvider.getPrimarySpecialtyCode());
			sp.setName(clinicianDetailProvider.getPrimarySpecialtyName());
			clncnDetail.setSpecialty(sp);
		}
	}

	private void setPrimaryDepartmentCode(PhoneBookSearchDetailEnvelope clncnDetail,
			ClinicianDetailProvider clinicianDetailProvider) {
		if (StringUtils.isNotBlank(clinicianDetailProvider.getPrimaryDepartmentCode())
				&& StringUtils.isNotBlank(clinicianDetailProvider.getPrimaryDepartmentName())) {
			PhoneBookDepartment dept = new PhoneBookDepartment();
			dept.setCode(clinicianDetailProvider.getPrimaryDepartmentCode());
			dept.setName(clinicianDetailProvider.getPrimaryDepartmentName());
			clncnDetail.setDepartment(dept);
		}
	}

	private void setDisplayName(PhoneBookSearchDetailEnvelope clncnDetail,
			ClinicianDetailProvider clinicianDetailProvider) {
		if (StringUtils.isNotBlank(clncnDetail.getNickName())) {
			clncnDetail.setName(ClinicianConnectUtilityMethods.displayNickName(clinicianDetailProvider.getFirsName(),
					clinicianDetailProvider.getLastName(), clncnDetail.getNickName(),
					clinicianDetailProvider.getProfTitle()));
		} else if (StringUtils.isNotBlank(clinicianDetailProvider.getDisplayName())) {
			clncnDetail.setName(clinicianDetailProvider.getDisplayName());
		}
	}

	private void setResourceIdAndNuid(PhoneBookSearchDetailEnvelope clncnDetail,
			ClinicianDetailProvider clinicianDetailProvider) {
		if (StringUtils.isNotBlank(clinicianDetailProvider.getNuid())) {
			clncnDetail.setNuid(clinicianDetailProvider.getNuid());
		}
		if (StringUtils.isNotBlank(clinicianDetailProvider.getResourceId())) {
			clncnDetail.setResourceId(clinicianDetailProvider.getResourceId());
		}
	}

	private OnCallTelepresence setOtherDetails(PhoneBookSearchDetailEnvelope clncnDetail,
			ClinicianDetailProvider clinicianDetailProvider) {
		if (StringUtils.isNotBlank(clinicianDetailProvider.getPhotoUrl())) {
			clncnDetail.setPhotoUrlString(PhoneBookServiceConstants.PHOTO_URL + clinicianDetailProvider.getPhotoUrl()
					.replaceAll(PhoneBookServiceConstants.PHOTO_WEB_, PhoneBookServiceConstants.THUMBNAIL1_));
		}
		if (StringUtils.isNotBlank(clinicianDetailProvider.getHomePageUrl())) {
			clncnDetail.setHomePageURL(clinicianDetailProvider.getHomePageUrl());
		}
		clncnDetail.setProviderType(clinicianDetailProvider.getProviderType());

		OnCallTelepresence tele = new OnCallTelepresence();
		tele.setVideo("YES");
		clncnDetail.setTelepresence(tele);
		return tele;
	}

	private void setContacts(ClinicianDetailProvider clinicianDetailProvider,
			PhoneBookClinicianLocation providerLocations, List<ClinicianContact> contacts,
			ClinicianPhoneObject phoneObject) {
		if (StringUtils.isNotBlank(phoneObject.getResourceCellPhoneNumber())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(PhoneBookServiceConstants.MOBILE);
			contact.setNumber(StringUtil.getNumberFormat(phoneObject.getResourceCellPhoneNumber()));
			LOGGER.debug(PhoneBookServiceConstants.MOBILE_NUMBER_IS_AVALIBLE);
			contacts.add(contact);
		}
		if (StringUtils.isNotBlank(phoneObject.getResourcePagerPhoneNumber())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(PhoneBookServiceConstants.PAGER);
			contact.setNumber(StringUtil.getNumberFormat(phoneObject.getResourcePagerPhoneNumber()));
			LOGGER.debug(PhoneBookServiceConstants.PAGER_NUMBER_IS_AVALIBLE);
			contacts.add(contact);
		}
		if (StringUtils.isNotBlank(phoneObject.getResourcePrivateExternalPhoneNumber())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(PhoneBookServiceConstants.OFFICE);
			contact.setNumber(StringUtil.getNumberFormat(phoneObject.getResourcePrivateExternalPhoneNumber()));
			LOGGER.debug(PhoneBookServiceConstants.OFFICE_NUMBER_IS_AVALIBLE);
			contacts.add(contact);
		}
		if (StringUtils.isNotBlank(phoneObject.getResourcePrivateInternalPhoneNumber())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(PhoneBookServiceConstants.TIE_LINE);
			contact.setNumber(StringUtil.convertToTieLineNumber(phoneObject.getResourcePrivateInternalPhoneNumber()));
			LOGGER.debug(PhoneBookServiceConstants.TIE_LINE_IS_AVALIABLE);
			contacts.add(contact);
		}
		prepareAndAddContact(clinicianDetailProvider, providerLocations, contacts);
	}

	private boolean validatePhoneObjectContatcs(ClinicianPhoneObject phoneObject) {
		boolean conditionOne = (phoneObject.getFacilityCode() == null && phoneObject.getClinicianPageNumber() == null
				&& phoneObject.getResourceNurseExternalPhoneNumber() == null) ? true : false;
		boolean conditionTwo = evalCondition(phoneObject);
		boolean conditionThree = (phoneObject.getResourcePagerPhoneNumber() == null
				&& phoneObject.getResourceCellPhoneNumber() != null) ? true : false;
		return (conditionOne && conditionTwo && conditionThree) ? true : false;
	}

	private void prepareAndAddContact(ClinicianDetailProvider clinicianDetailProvider,
			PhoneBookClinicianLocation providerLocations, List<ClinicianContact> contacts) {
		if (StringUtils.isNotBlank(clinicianDetailProvider.getEmailAddress())
				&& "Y".equals(providerLocations.getPrimaryIndicator())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(PhoneBookServiceConstants.EMAIL);
			contact.setNumber(clinicianDetailProvider.getEmailAddress());
			LOGGER.debug(PhoneBookServiceConstants.EMAIL_IS_AVALIABLE);
			contacts.add(contact);
		}
	}

	private boolean evalCondition(ClinicianPhoneObject phoneObject) {
		return (phoneObject.getResourceNurseInternalPhoneNumber() == null
				&& phoneObject.getResourcePrivateExternalPhoneNumber() == null
				&& phoneObject.getResourcePrivateInternalPhoneNumber() == null) ? true : false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PhoneBookSearchDesktopEnvelope getPhoneBookSearchDesktop(SearchInput searchInput) {

		LOGGER.debug("entered into getPhoneBookSearchDesktop action in PhoneBookServiceImpl");
		Calendar startTime = Calendar.getInstance();
		PhoneBookSearchDesktopEnvelope dsktpSearchEnvelope = new PhoneBookSearchDesktopEnvelope();
		if (null != searchInput && (validateSearchInput(searchInput) || null != searchInput.getNuid())) {
			String firstName = null;
			String lastName = null;
			List<PhoneBookClinicianDesktop> phoneBookClinicians = new ArrayList<>();
			List<ClinicianContactDetails> clinicianPhoneList = new ArrayList<>();
			List<Integer> records = new ArrayList<>();
			String specialty = null;
			String facility = null;
			String nuid = null;

			if (StringUtils.isNotBlank(searchInput.getPhonebookFirstName())) {
				firstName = searchInput.getPhonebookFirstName().trim();
				// firstName = StringUtil.removeSpChForSearch(firstName);
				firstName = firstName.replace("'", "''");
				if ("".equals(firstName)) {
					firstName = null;
				}
			}
			if (StringUtils.isNotBlank(searchInput.getPhonebookLastName())) {
				lastName = searchInput.getPhonebookLastName().trim();
				// lastName = StringUtil.removeSpChForSearch(lastName);
				lastName = lastName.replace("'", "''");
				if ("".equals(lastName)) {
					lastName = null;

				}
			}
			if (null != searchInput.getSearchFacility() && !("").equals(searchInput.getSearchFacility().getCode())) {
				facility = searchInput.getSearchFacility().getCode();
			}
			if (null != searchInput.getSearchSpecialty()) {
				specialty = searchInput.getSearchSpecialty().getName();
			}
			if (null != searchInput.getNuid() && !("").equalsIgnoreCase(searchInput.getNuid())) {
				nuid = searchInput.getNuid();
			}
			if (validateFileds(firstName, lastName, specialty, facility, nuid)) {
				List<String> nuidList = new ArrayList<>();
				int srchResultCount = 0;
				int dispayNoOfRecords = 0;
				searchInput.setPhonebookFirstName(firstName);
				searchInput.setPhonebookLastName(lastName);
				Map<String, Object> response = phoneBookDao.getPhoneBookSearchDesktop(firstName, lastName, specialty,
						facility, searchInput);
				phoneBookClinicians = (List<PhoneBookClinicianDesktop>) response.get("phoneBookClinicians");
				clinicianPhoneList = (List<ClinicianContactDetails>) response.get("clinicianPhoneList");
				nuidList = (List<String>) response.get("nuidList");
				records = (List<Integer>) response.get("records");
				srchResultCount = records.get(0);
				dispayNoOfRecords = records.get(1);

				if (null != phoneBookClinicians && !phoneBookClinicians.isEmpty()) {
					Map<String, ClinicianProvider> clinicianProviderMap = null;
					if (null != nuidList && !nuidList.isEmpty()) {
						String[] nuids = nuidList.toArray(new String[nuidList.size()]);
						try {
							String provider = PhoneBookServiceConstants.PROVIDER_BY_NUIDS_API_NAME;
							MdoProviderUtil mdoProviderUtil = commonUtil.getMdoProviderUtil(provider);
							clinicianProviderMap = mdoProviderUtil.getProvidersByNUIDsDirectRestCall(nuids);
						} catch (Exception e) {
							LOGGER.error("ERROR: PhonebookServices: problem while getting TTG Provider Information", e);
						}
						populateTTGProviderDetails(phoneBookClinicians, clinicianProviderMap);
					}
					if (new Integer(srchResultCount).equals(new Integer(dispayNoOfRecords))
							|| new Integer(srchResultCount) > (new Integer(dispayNoOfRecords))) {
						dsktpSearchEnvelope.setHasMoreData(true);
						dsktpSearchEnvelope.setSkipCount(srchResultCount - 1);
						phoneBookClinicians.remove((phoneBookClinicians.size()) - 1);
					} else if (new Integer(srchResultCount) < new Integer(dispayNoOfRecords)) {
						dsktpSearchEnvelope.setHasMoreData(false);
						dsktpSearchEnvelope.setSkipCount(srchResultCount);
					}

					Map<String, Map<String, List<ClinicianContact>>> clinicianContactMap = getClinicianContactMap(
							clinicianPhoneList);
					Map<String, ClinicianContactDetails> clinicainLocationMap = getClinicianLocationMap(
							clinicianPhoneList);

					for (Iterator<PhoneBookClinicianDesktop> iterator = phoneBookClinicians.iterator(); iterator
							.hasNext();) {
						PhoneBookClinicianDesktop phoneBookClinicianDesktop = iterator.next();
						if (null != phoneBookClinicianDesktop) {
							List<PhoneBookClinicianLocation> clinicianLocations = getPhoneBookClinicianSecondaryLocationList(
									phoneBookClinicianDesktop, clinicainLocationMap, clinicianContactMap);
							if (null != clinicianLocations && !clinicianLocations.isEmpty()) {
								Calendar cal10 = Calendar.getInstance();
								Collections.sort(clinicianLocations, new Comparator<PhoneBookClinicianLocation>() {
									@Override
									public int compare(PhoneBookClinicianLocation o1, PhoneBookClinicianLocation o2) {
										if (null != o1 && null != o1.getFacility() && null != o1.getFacility().getName()
												&& null != o2 && null != o2.getFacility()) {
											return o1.getFacility().getName().compareTo(o2.getFacility().getName());
										}
										return 0;
									}
								});

								clinicianLocations = sortSecondaryLocationsForFacilitySearch(clinicianLocations,
										facility);
								phoneBookClinicianDesktop.setClinicianLocations(clinicianLocations);
								Calendar cal11 = Calendar.getInstance();
								ClinicianConnectLogger.logPerformanceTime(LOGGER,
										"Time taken to sort secondary Locations : ", cal10, cal11);

								String secondaryLocationStr = getSecondaryLocations(clinicianLocations, facility);
								Calendar cal12 = Calendar.getInstance();
								ClinicianConnectLogger.logPerformanceTime(LOGGER,
										"Time taken to create secondary Location string : ", cal11, cal12);

								if (StringUtils.isNotBlank(secondaryLocationStr)) {
									phoneBookClinicianDesktop.setSecondaryLocations(secondaryLocationStr);
								}
							}

							OnCallTelepresence telepresence = new OnCallTelepresence();
							telepresence.setVideo("YES");
							telepresence.setText("true");
							phoneBookClinicianDesktop.setTelepresence(telepresence);
						}
					}
				}
				if (null != searchInput.getSearchFacility()
						&& StringUtils.isNotBlank(searchInput.getSearchFacility().getCode())) {
					SearchFacility sf = new SearchFacility();
					sf.setCode(searchInput.getSearchFacility().getCode());
					sf.setName(searchInput.getSearchFacility().getName());
					dsktpSearchEnvelope.setSearchFacility(sf);
				}
				if (null != searchInput.getSearchSpecialty()
						&& StringUtils.isNotBlank(searchInput.getSearchSpecialty().getName())) {
					SearchSpecialty sp = new SearchSpecialty();

					sp.setName(searchInput.getSearchSpecialty().getName());
					if (StringUtils.isNotBlank(searchInput.getSearchSpecialty().getCode()))
						sp.setCode(searchInput.getSearchSpecialty().getCode());
					dsktpSearchEnvelope.setSearchSpecialty(sp);
				}

				Collections.sort(phoneBookClinicians, new Comparator<PhoneBookClinicianDesktop>() {
					@Override
					public int compare(PhoneBookClinicianDesktop o1, PhoneBookClinicianDesktop o2) {
						try {

							String s1 = (o1.getFacility().getName() + " " + o1.getLastName() + " " + o1.getFirstName()
									+ " " + o1.getSpecialty().getName()).toLowerCase();
							String s2 = (o2.getFacility().getName() + " " + o2.getLastName() + " " + o2.getFirstName()
									+ " " + o2.getSpecialty().getName()).toLowerCase();
							return s1.compareTo(s2);
						} catch (Exception e) {
							LOGGER.error("Error while Sorting PhoneBookClinicianDesktop in getPhoneBookSearchDesktop() ",
									e);
						}
						return 0;
					}
				});

				dsktpSearchEnvelope.setPhoneBookClinicians(phoneBookClinicians);
				LOGGER.debug("Envelope  " + dsktpSearchEnvelope.getHasMoreData());
			}
		}
		if (null == dsktpSearchEnvelope.getHasMoreData())
			dsktpSearchEnvelope.setHasMoreData(false);

		AppVersion appversion = null;
		try {
			appversion = commonUtil.getAppVersion(searchInput.getAppVersion());
		} catch (CCUtilityServicesException e) {
			LOGGER.error("ERROR: PhonebookServices: problem while getting App Version Information", e);
		}
		if (null != appversion)
			dsktpSearchEnvelope.setAppVersion(appversion);

		Calendar endTime = Calendar.getInstance();
		ClinicianConnectLogger.logPerformanceTime(LOGGER, "Time taken to execute getPhoneBookSearchDesktop() service : ",
				startTime, endTime);
		return dsktpSearchEnvelope;
	}

	/**
	 * This method populates the Clinician details got from TTG Service into the
	 * given PhoneBookClinicianDesktop details
	 * 
	 * @param phoneBookClinicians
	 * @param clinicianProviderMap
	 */
	private void populateTTGProviderDetails(List<PhoneBookClinicianDesktop> phoneBookClinicians,
			Map<String, ClinicianProvider> clinicianProviderMap) {
		if (null == phoneBookClinicians || phoneBookClinicians.isEmpty() || null == clinicianProviderMap
				|| clinicianProviderMap.isEmpty()) {
			return;
		}
		for (PhoneBookClinicianDesktop phoneBookClinicianDesktop : phoneBookClinicians) {
			if (null == phoneBookClinicianDesktop) {
				continue;
			}
			ClinicianProvider ttgProviderDetails = clinicianProviderMap.get(phoneBookClinicianDesktop.getNuid());
			if (null != ttgProviderDetails) {
				if (StringUtils.isNotBlank(ttgProviderDetails.getFirsName())) {
					phoneBookClinicianDesktop.setFirstName(ttgProviderDetails.getFirsName());
				}
				if (StringUtils.isNotBlank(ttgProviderDetails.getLastName())) {
					phoneBookClinicianDesktop.setLastName(ttgProviderDetails.getLastName());
				}

				if (StringUtils.isNotBlank(phoneBookClinicianDesktop.getNickName())) {
					phoneBookClinicianDesktop.setName(ClinicianConnectUtilityMethods.displayNickName(
							ttgProviderDetails.getFirsName(), ttgProviderDetails.getLastName(),
							phoneBookClinicianDesktop.getNickName(), ttgProviderDetails.getProfTitle()));
				} else {
					phoneBookClinicianDesktop
							.setName(ClinicianConnectUtilityMethods.getDisplayName(ttgProviderDetails.getFirsName(),
									ttgProviderDetails.getLastName(), ttgProviderDetails.getProfTitle()));

				}
				if (StringUtils.isNotBlank(ttgProviderDetails.getHomePageURL())) {
					phoneBookClinicianDesktop.setHomePageURL(ttgProviderDetails.getHomePageURL());
				}
				if (StringUtils.isNotBlank(ttgProviderDetails.getEmailAddress())) {
					phoneBookClinicianDesktop.seteMailAddress(ttgProviderDetails.getEmailAddress());
				}
				populateSecureTextUrl(phoneBookClinicianDesktop, ttgProviderDetails);
				if (StringUtils.isNotBlank(ttgProviderDetails.getResourceId())) {
					phoneBookClinicianDesktop.setResourceId(ttgProviderDetails.getResourceId());
				}
				if (null != ttgProviderDetails.getDepartment()) {
					phoneBookClinicianDesktop.setDepartment(ttgProviderDetails.getDepartment());
				}
				if (StringUtils.isNotBlank(ttgProviderDetails.getPrimaryFacilityCode())
						&& StringUtils.isNotBlank(ttgProviderDetails.getPrimaryFacilityName())) {
					SearchFacility primFacility = new SearchFacility();
					primFacility.setCode(ttgProviderDetails.getPrimaryFacilityCode());
					primFacility.setName(ttgProviderDetails.getPrimaryFacilityName());
					phoneBookClinicianDesktop.setFacility(primFacility);
				}
				if (StringUtils.isNotBlank(ttgProviderDetails.getPrimarySpecialtyCode())
						&& StringUtils.isNotBlank(ttgProviderDetails.getPrimarySpecialtyName())) {
					SearchSpecialty primSpecialty = new SearchSpecialty();
					primSpecialty.setCode(ttgProviderDetails.getPrimarySpecialtyCode());
					primSpecialty.setName(ttgProviderDetails.getPrimarySpecialtyName());
					phoneBookClinicianDesktop.setSpecialty(primSpecialty);
				}
			}
		}
	}

	private void populateSecureTextUrl(PhoneBookClinicianDesktop phoneBookClinicianDesktop,
			ClinicianProvider ttgProviderDetails) {
		if (StringUtils.isNotBlank(ttgProviderDetails.getEmailAddress())) {
			phoneBookClinicianDesktop.setSecureTextUrl(PhoneBookServiceConstants.CORETEXT_URL
					+ PhoneBookServiceConstants.EMAIL_ADDRESS_SMALL + "=" + ttgProviderDetails.getEmailAddress()
					+ PhoneBookServiceConstants.CORTEXT_START_TYPING_MSG);
		} else {
			phoneBookClinicianDesktop.setSecureTextUrl(
					PhoneBookServiceConstants.CORETEXT_URL + PhoneBookServiceConstants.EMAIL_ADDRESS_WITH_NULL_STRING
							+ PhoneBookServiceConstants.CORTEXT_START_TYPING_MSG);
		}
	}

	private boolean validateSearchInput(SearchInput searchInput) {
		boolean isValidInput = false;
		if (null != searchInput) {
			boolean isValidFacilityAndSpecailty = (null != searchInput.getSearchFacility()
					|| null != searchInput.getSearchSpecialty()) ? true : false;
			boolean isFnAndLn = (null != searchInput.getPhonebookFirstName()
					|| null != searchInput.getPhonebookLastName()) ? true : false;
			if (isValidFacilityAndSpecailty || isFnAndLn) {
				isValidInput = true;
			}
		}
		return isValidInput;
	}

	private Map<String, Map<String, List<ClinicianContact>>> getClinicianContactMap(
			List<ClinicianContactDetails> clinicianPhoneList) {

		Map<String, Map<String, List<ClinicianContact>>> clinicianLocationContactMap = new HashMap<>();
		if (null != clinicianPhoneList && !clinicianPhoneList.isEmpty()) {
			for (ClinicianContactDetails clinicianContactDetails : clinicianPhoneList) {
				if (null == clinicianContactDetails) {
					continue;
				}

				List<ClinicianContact> contacts = new ArrayList<>();
				addMobile(clinicianContactDetails, contacts);
				addPager(clinicianContactDetails, contacts);
				addOffice(clinicianContactDetails, contacts);
				addTieLine(clinicianContactDetails, contacts);
				addEmail(clinicianContactDetails, contacts);
				if (!contacts.isEmpty()) {
					Map<String, List<ClinicianContact>> currClinicianFacMap = clinicianLocationContactMap
							.get(clinicianContactDetails.getNuid());
					prepareCUrrClinicianFacMap(clinicianLocationContactMap, clinicianContactDetails, contacts,
							currClinicianFacMap);
				}
			}

		}
		return clinicianLocationContactMap;
	}

	private void addEmail(ClinicianContactDetails clinicianContactDetails, List<ClinicianContact> contacts) {
		if (PhoneBookServiceConstants.YES_INDICATOR
				.equalsIgnoreCase(clinicianContactDetails.getPrimaryLocationIndicator())
				&& StringUtils.isNotBlank(clinicianContactDetails.getEmailAddress())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(PhoneBookServiceConstants.EMAIL);
			contact.setNumber(clinicianContactDetails.getEmailAddress().toLowerCase());
			LOGGER.debug(PhoneBookServiceConstants.EMAIL_IS_AVALIABLE);
			contacts.add(contact);
		}
	}

	private void addTieLine(ClinicianContactDetails clinicianContactDetails, List<ClinicianContact> contacts) {
		if (StringUtils.isNotBlank(clinicianContactDetails.getResourcePrivateInternalPhoneNumber())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(PhoneBookServiceConstants.TIE_LINE);
			contact.setNumber(
					StringUtil.convertToTieLineNumber(clinicianContactDetails.getResourcePrivateInternalPhoneNumber()));
			LOGGER.debug(PhoneBookServiceConstants.TIE_LINE_IS_AVALIABLE);
			contacts.add(contact);
		}
	}

	private void addOffice(ClinicianContactDetails clinicianContactDetails, List<ClinicianContact> contacts) {
		if (StringUtils.isNotBlank(clinicianContactDetails.getResourcePrivateExternalPhoneNumber())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(PhoneBookServiceConstants.OFFICE);
			contact.setNumber(
					StringUtil.getNumberFormat(clinicianContactDetails.getResourcePrivateExternalPhoneNumber()));
			LOGGER.debug(PhoneBookServiceConstants.OFFICE_NUMBER_IS_AVALIBLE);
			contacts.add(contact);
		}
	}

	private void addPager(ClinicianContactDetails clinicianContactDetails, List<ClinicianContact> contacts) {
		if (StringUtils.isNotBlank(clinicianContactDetails.getResourcePagerPhoneNumber())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(PhoneBookServiceConstants.PAGER);
			contact.setNumber(StringUtil.getNumberFormat(clinicianContactDetails.getResourcePagerPhoneNumber()));
			LOGGER.debug(PhoneBookServiceConstants.PAGER_NUMBER_IS_AVALIBLE);
			contacts.add(contact);
		}
	}

	private void addMobile(ClinicianContactDetails clinicianContactDetails, List<ClinicianContact> contacts) {
		if (StringUtils.isNotBlank(clinicianContactDetails.getResourceCellPhoneNumber())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(PhoneBookServiceConstants.MOBILE);
			contact.setNumber(StringUtil.getNumberFormat(clinicianContactDetails.getResourceCellPhoneNumber()));
			LOGGER.debug(PhoneBookServiceConstants.MOBILE_NUMBER_IS_AVALIBLE);
			contacts.add(contact);
		}
	}

	private void prepareCUrrClinicianFacMap(
			Map<String, Map<String, List<ClinicianContact>>> clinicianLocationContactMap,
			ClinicianContactDetails clinicianContactDetails, List<ClinicianContact> contacts,
			Map<String, List<ClinicianContact>> currClinicianFacMap) {
		if (null == currClinicianFacMap || currClinicianFacMap.size() == 0) {
			currClinicianFacMap = new HashMap<>();
			currClinicianFacMap.put(clinicianContactDetails.getFacilityCode(), contacts);
			clinicianLocationContactMap.put(clinicianContactDetails.getNuid(), currClinicianFacMap);
		} else {
			if (null == clinicianLocationContactMap.get(clinicianContactDetails.getFacilityCode())) {
				currClinicianFacMap.put(clinicianContactDetails.getFacilityCode(), contacts);
			} else {
				// replacing and not adding to the list to avoid duplicate facility data
				if (PhoneBookServiceConstants.YES_INDICATOR
						.equalsIgnoreCase(clinicianContactDetails.getPrimaryLocationIndicator())) {
					currClinicianFacMap.put(clinicianContactDetails.getFacilityCode(), contacts);
				}
			}
		}
	}

	/**
	 * This method creates a map of contacts for Each clinician with NUID+Location
	 * as key and Contact for that Location as value
	 * 
	 * @param clinicianPhoneList
	 * @return
	 */
	private Map<String, ClinicianContactDetails> getClinicianLocationMap(
			List<ClinicianContactDetails> clinicianPhoneList) {

		Map<String, ClinicianContactDetails> clinicianLocationMap = new HashMap<>();
		if (null != clinicianPhoneList && !clinicianPhoneList.isEmpty()) {
			for (ClinicianContactDetails clinicianContactDetails : clinicianPhoneList) {
				if (null == clinicianContactDetails) {
					continue;
				}
				ClinicianContactDetails currClinicianLocation = clinicianLocationMap
						.get(clinicianContactDetails.getNuid() + clinicianContactDetails.getFacilityCode());
				if (null == currClinicianLocation) {
					clinicianLocationMap.put(
							clinicianContactDetails.getNuid() + clinicianContactDetails.getFacilityCode(),
							clinicianContactDetails);
				} else {
					addToClinicianLocationMap(clinicianLocationMap, clinicianContactDetails);
				}
			}
		}
		return clinicianLocationMap;
	}

	private void addToClinicianLocationMap(Map<String, ClinicianContactDetails> clinicianLocationMap,
			ClinicianContactDetails clinicianContactDetails) {
		if (PhoneBookServiceConstants.YES_INDICATOR
				.equalsIgnoreCase(clinicianContactDetails.getPrimaryLocationIndicator())) {
			clinicianLocationMap.put(clinicianContactDetails.getNuid() + clinicianContactDetails.getFacilityCode(),
					clinicianContactDetails);
		}
	}

	/**
	 * This method creates the Clinician's Secondary Location Contact details
	 * 
	 * @param phoneBookClinicianDesktop
	 * @param clinicainLocationMap
	 * @param clinicianContactMap
	 * @return
	 */
	private List<PhoneBookClinicianLocation> getPhoneBookClinicianSecondaryLocationList(
			PhoneBookClinicianDesktop phoneBookClinicianDesktop,
			Map<String, ClinicianContactDetails> clinicainLocationMap,
			Map<String, Map<String, List<ClinicianContact>>> clinicianContactMap) {
		if (null == phoneBookClinicianDesktop || null == clinicainLocationMap || null == clinicianContactMap) {
			return new ArrayList<>();
		}

		List<PhoneBookClinicianLocation> clinicianLocationsList = new ArrayList<>();
		Map<String, List<ClinicianContact>> contactMap = clinicianContactMap.get(phoneBookClinicianDesktop.getNuid());
		if (null != contactMap && contactMap.size() != 0) {
			if (null != phoneBookClinicianDesktop && null != phoneBookClinicianDesktop.getFacility()
					&& StringUtils.isNotBlank(phoneBookClinicianDesktop.getFacility().getCode())) {
				List<ClinicianContact> primContacts = contactMap.get(phoneBookClinicianDesktop.getFacility().getCode());
				phoneBookClinicianDesktop.setContacts(primContacts);
			}
			Set<String> facilitySet = contactMap.keySet();
			for (String facCode : facilitySet) {
				if (StringUtils.isNotBlank(facCode)) {
					ClinicianContactDetails clinicianContactDetails = clinicainLocationMap
							.get(phoneBookClinicianDesktop.getNuid() + facCode);
					if (null != clinicianContactDetails) {
						if (null != phoneBookClinicianDesktop && null != phoneBookClinicianDesktop.getFacility()
								&& StringUtils.isNotBlank(phoneBookClinicianDesktop.getFacility().getCode())
								&& phoneBookClinicianDesktop.getFacility().getCode()
										.equalsIgnoreCase(clinicianContactDetails.getFacilityCode())) {
							// Primary Location and Contacts
							phoneBookClinicianDesktop.setContacts(contactMap.get(facCode));
						} else {
							// Secondary Location and Contacts
							PhoneBookClinicianLocation phoneBookClinicianLocation = new PhoneBookClinicianLocation();
							PhoneBookFacility facility = new PhoneBookFacility();
							facility.setCode(clinicianContactDetails.getFacilityCode());
							facility.setName(clinicianContactDetails.getFacilityName());
							phoneBookClinicianLocation.setFacility(facility);

							PhoneBookSpecialty specialty = new PhoneBookSpecialty();
							specialty.setCode(clinicianContactDetails.getSpecialtyId() + "");
							specialty.setName(clinicianContactDetails.getSpecialtyName());
							phoneBookClinicianLocation.setSpecialty(specialty);

							phoneBookClinicianLocation
									.setPrimaryIndicator(clinicianContactDetails.getPrimaryLocationIndicator());
							List<ClinicianContact> contactList = contactMap.get(facCode);
							if (null != contactList && !contactList.isEmpty()) {
								phoneBookClinicianLocation.setContacts(contactList);
							}

							clinicianLocationsList.add(phoneBookClinicianLocation);
						}
					}
				}
			}
		}
		// when it is not associated with facility
		String nullKey = phoneBookClinicianDesktop.getNuid() + "null";
		ClinicianContactDetails clinicianContactDetails = clinicainLocationMap.get(nullKey);

		if (null != clinicianContactDetails
				&& StringUtils.isNotBlank(clinicianContactDetails.getResourceCellPhoneNumber())) {
			List<ClinicianContact> primContacts = phoneBookClinicianDesktop.getContacts();
			if (null != primContacts && !primContacts.isEmpty()) {
				isMobileAlreadyAvaliable(clinicianContactDetails, primContacts);
			} else {
				// Create a contact list and set only Cell Number contact in it
				List<ClinicianContact> newPrimContacts = new ArrayList<>();
				ClinicianContact contact = new ClinicianContact();
				contact.setType(PhoneBookServiceConstants.MOBILE);
				contact.setNumber(clinicianContactDetails.getResourceCellPhoneNumber());
				newPrimContacts.add(contact);
				phoneBookClinicianDesktop.setContacts(newPrimContacts);
			}
		}
		return clinicianLocationsList;
	}

	private void isMobileAlreadyAvaliable(ClinicianContactDetails clinicianContactDetails,
			List<ClinicianContact> primContacts) {
		boolean foundMobile = false;
		for (Iterator<ClinicianContact> iterator = primContacts.iterator(); iterator.hasNext();) {
			ClinicianContact clinicianContact = iterator.next();
			if (null != clinicianContact
					&& PhoneBookServiceConstants.MOBILE.equalsIgnoreCase(clinicianContact.getType())) {
				foundMobile = true;
			}
		}
		if (!foundMobile) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(PhoneBookServiceConstants.MOBILE);
			contact.setNumber(clinicianContactDetails.getResourceCellPhoneNumber());
			primContacts.add(contact);
		}
	}

	/**
	 * This method sorts the Clinician Location List to put Primary Location in the
	 * first and searched secondary location in the second place if the search input
	 * has Location
	 * 
	 * @param clinicianLocations
	 * @param facility
	 * @return
	 */
	private List<PhoneBookClinicianLocation> sortSecondaryLocationsForFacilitySearch(
			List<PhoneBookClinicianLocation> clinicianLocations, String facility) {

		boolean primaryAdded = false;
		List<PhoneBookClinicianLocation> sortedClinicianLocations = new ArrayList<>();
		for (Iterator<PhoneBookClinicianLocation> iterator = clinicianLocations.iterator(); iterator.hasNext();) {
			PhoneBookClinicianLocation phoneBookClinicianLocation = iterator.next();
			if (null == phoneBookClinicianLocation) {
				return sortedClinicianLocations;
			}
			if (PhoneBookServiceConstants.YES_INDICATOR
					.equalsIgnoreCase(phoneBookClinicianLocation.getPrimaryIndicator())) {
				sortedClinicianLocations.add(0, phoneBookClinicianLocation);
				primaryAdded = true;
			} else {
				sortClinicianLocations(facility, primaryAdded, sortedClinicianLocations, phoneBookClinicianLocation);
			}
		}
		return sortedClinicianLocations;
	}

	private void sortClinicianLocations(String facility, boolean primaryAdded,
			List<PhoneBookClinicianLocation> sortedClinicianLocations,
			PhoneBookClinicianLocation phoneBookClinicianLocation) {
		if (null != phoneBookClinicianLocation.getFacility() && StringUtils.isNotBlank(facility)
				&& facility.equalsIgnoreCase(phoneBookClinicianLocation.getFacility().getCode())) {
			if (primaryAdded) {
				sortedClinicianLocations.add(1, phoneBookClinicianLocation);
			} else {
				sortedClinicianLocations.add(0, phoneBookClinicianLocation);
			}
		} else {
			sortedClinicianLocations.add(phoneBookClinicianLocation);
		}
	}

	/**
	 * This method creates the Secondary Locations String based on the given
	 * Locations and facility code given in the search criteria
	 * 
	 * @param clinicianLocations
	 * @param facility
	 * @return
	 */
	private String getSecondaryLocations(List<PhoneBookClinicianLocation> clinicianLocations, String facility) {
		if (null == clinicianLocations || clinicianLocations.isEmpty()) {
			return null;
		}
		String secLocString = null;
		for (Iterator<PhoneBookClinicianLocation> iterator = clinicianLocations.iterator(); iterator.hasNext();) {
			PhoneBookClinicianLocation phoneBookClinicianLocation = iterator.next();
			if (null == phoneBookClinicianLocation) {
				continue;
			}
			String currFacilityCode = null;
			String currFacilityName = null;
			if (null != phoneBookClinicianLocation.getFacility()) {
				currFacilityCode = phoneBookClinicianLocation.getFacility().getCode();
				currFacilityName = phoneBookClinicianLocation.getFacility().getName();
			}

			if (!PhoneBookServiceConstants.YES_INDICATOR
					.equalsIgnoreCase(phoneBookClinicianLocation.getPrimaryIndicator())
					&& null != phoneBookClinicianLocation.getContacts()
					&& !phoneBookClinicianLocation.getContacts().isEmpty()) {
				// secondary location - need to add to secondary locations string if has valid
				// contacts
				secLocString = getSecLocStr(facility, secLocString, currFacilityCode, currFacilityName);
			}
		}
		return secLocString;
	}

	private String getSecLocStr(String facility, String secLocString, String currFacilityCode,
			String currFacilityName) {
		if (StringUtils.isNotBlank(facility)) {
			if (null == secLocString || secLocString.length() == 0) {
				return currFacilityName;
			} else {
				return facility.equalsIgnoreCase(currFacilityCode) ? (currFacilityName + ", " + secLocString)
						: (secLocString + ", " + currFacilityName);
			}
		} else {
			return (null == secLocString || secLocString.length() == 0) ? currFacilityName
					: (secLocString + ", " + currFacilityName);

		}
	}

	private boolean validateFileds(String firstName, String lastName, String specialty, String facility, String nuid) {
		boolean isValidName = (vlidateInputString(firstName) || vlidateInputString(lastName)) ? true : false;
		boolean isValidDetails = (vlidateInputString(facility) || vlidateInputString(specialty)
				|| vlidateInputString(nuid)) ? true : false;
		return (isValidName || isValidDetails) ? true : false;
	}

	private boolean vlidateInputString(String str) {
		return StringUtils.isNotBlank(str) ? true : false;
	}

	public void setAppVersionAndStatusCode(SearchDetailInput searchDetailInput,
			PhoneBookSearchDetailEnvelope phoneBookSearchDetailEnvelope,
			PhoneBookSearchDetailOutput phoneBookSearchDetailOutput, Status status) throws CCUtilityServicesException {
		if (phoneBookSearchDetailEnvelope.getAppVersion() != null) {
			if (StringUtils.isBlank(phoneBookSearchDetailEnvelope.getResourceId())
					|| !phoneBookSearchDetailEnvelope.getResourceId().equals(searchDetailInput.getResourceId())) {
				status.setCode(PhoneBookServiceConstants.MSG_CODE_204);
				status.setMessage(PhoneBookServiceConstants.PHONEBOOK_FAV_NO_LONGER);
			} else {
				status.setCode(PhoneBookServiceConstants.SUCCESS_CODE_200);
				status.setMessage(PhoneBookServiceConstants.SUCCESS);
				phoneBookSearchDetailOutput.setAppVersion(commonUtil.getAppVersion(searchDetailInput.getAppVersion()));
			}
		} else {
			status.setCode(PhoneBookServiceConstants.MSG_CODE_204);
			status.setMessage(PhoneBookServiceConstants.NO_CONTENT_FOR_THE_PROVIDED_INPUT_PROBLEM);
		}
	}

	@Override
	public void setBadRequestStatusAndLogError(Status status, Exception e) {
		LOGGER.error(PhoneBookServiceConstants.ERROR_OCURRED, e);
		status.setCode(PhoneBookServiceConstants.BAD_REQUEST);
		status.setMessage(e.getMessage());
	}

	@Override
	public void setPhoneBookSearchStatus(PhoneBookSearchDesktopEnvelope phoneBookSearchDesktopEnvelope,
			PhoneBookSearchDesktopOutput phoneBookSearchDesktopOutput, Status status) {
		if (phoneBookSearchDesktopEnvelope.getAppVersion() != null) {
			status.setCode("200");
			status.setMessage(PhoneBookServiceConstants.SUCCESS);
			phoneBookSearchDesktopOutput.setAppVersion(phoneBookSearchDesktopEnvelope.getAppVersion());
		} else {
			status.setCode("204");
			status.setMessage(PhoneBookServiceConstants.NO_CONTENT_FOR_THE_PROVIDED_INPUT_PROBLEM);
		}
	}

	@Override
	public PhoneBookSearchEnvelope getPhoneBookSearchResult(SearchInput searchInput) {
		LOGGER.debug("Entered into getPhoneBookSearchResult Service in PhoneBookServiceImpl");

		PhoneBookSearchEnvelope phoneBookSearchEnvelope = new PhoneBookSearchEnvelope();
		if (validateSearchInput(searchInput)) {
			String firstName = null;
			String lastName = null;

			String specialty = (searchInput.getSearchSpecialty() != null) ? searchInput.getSearchSpecialty().getName()
					: null;
			String facilityCode = (searchInput.getSearchFacility() != null
					&& !("").equals(searchInput.getSearchFacility().getCode()))
							? searchInput.getSearchFacility().getCode()
							: null;
			String facilityName = (searchInput.getSearchFacility() != null
					&& !("").equals(searchInput.getSearchFacility().getName()))
							? searchInput.getSearchFacility().getName()
							: null;

			if (StringUtils.isNotBlank(searchInput.getPhonebookFirstName())
					|| StringUtils.isNotBlank(searchInput.getPhonebookLastName())) {
				if (StringUtils.isNotBlank(searchInput.getPhonebookFirstName())) {
					firstName = searchInput.getPhonebookFirstName().trim();
					firstName = StringUtil.removeSpChForSearch(firstName);
					firstName = firstName.replace("'", "''");
					firstName = "".equals(firstName) ? null : firstName;
				}
				if (StringUtils.isNotBlank(searchInput.getPhonebookLastName())) {
					lastName = searchInput.getPhonebookLastName().trim();
					lastName = StringUtil.removeSpChForSearch(lastName);
					lastName = lastName.replace("'", "''");
					lastName = "".equals(lastName) ? null : lastName;
				}
			}
			if (firstName != null || lastName != null || facilityCode != null || specialty != null) {

				int srchResultCount = 0;
				int dispayNoOfRecords = 0;
				Map<String, Object> phoneBookSearchResponse = phoneBookDao.getPhoneBookSearchResult(firstName, lastName,
						facilityCode, specialty, searchInput.getSkipCount());
				phoneBookSearchEnvelope = (PhoneBookSearchEnvelope) phoneBookSearchResponse
						.get("phoneBookSearchEnvelope");
				srchResultCount = phoneBookSearchResponse.get("srchResultCount") != null
						? (int) phoneBookSearchResponse.get("srchResultCount")
						: 0;

				dispayNoOfRecords = phoneBookSearchResponse.get("dispayNoOfRecords") != null
						? (int) phoneBookSearchResponse.get("dispayNoOfRecords")
						: 0;
				@SuppressWarnings("unchecked")
				List<String> nuidList = (List<String>) phoneBookSearchResponse.get("nuidList");
				List<PhoneBookClinician> phoneBookClinicians = phoneBookSearchEnvelope != null
						? phoneBookSearchEnvelope.getPhoneBookClinicians()
						: new ArrayList<PhoneBookClinician>();
				if (phoneBookClinicians != null && !phoneBookClinicians.isEmpty() && nuidList != null
						&& !nuidList.isEmpty()) {
					preparePhonBookCliniciansOtherLocations(facilityName, phoneBookClinicians,
							new HashSet<String>(nuidList));
					preparePhonBookCliniciansContacts(phoneBookClinicians, new HashSet<String>(nuidList));
					sortPhoneBookClinicians(phoneBookClinicians);
				}
				// dispayNoOfRecords will always be 21
				// if SrchResultCount and display No of Records are same means
				// enable load more icon
				setHasMoreData(phoneBookSearchEnvelope, phoneBookClinicians, srchResultCount, dispayNoOfRecords);
				setSearchFacility(searchInput, phoneBookSearchEnvelope);
				setSearchSpeciality(searchInput, phoneBookSearchEnvelope);

				logStatements(searchInput, phoneBookClinicians, srchResultCount);
				phoneBookSearchEnvelope.setPhoneBookClinicians(phoneBookClinicians);
			}
		}

		if (searchInput != null && StringUtils.isNotBlank(searchInput.getAppVersion())) {
			AppVersion appversion;
			try {
				appversion = commonUtil.getAppVersion(searchInput.getAppVersion());
				if (appversion != null) {
					phoneBookSearchEnvelope.setAppVersion(appversion);
				}
			} catch (CCUtilityServicesException e) {
				LOGGER.error("ERROR: PhonebookServices: problem in PhoneBookServiceImpl/getAppVersion", e);
			}
		}
		if (searchInput != null && StringUtils.isNotBlank(searchInput.getDeviceType())) {
			try {
				phoneBookSearchEnvelope.setiOSVersion(commonUtil.getIOSVersion(searchInput.getDeviceType(),
						searchInput.getiOSVersion(), "getPhoneBookSearchResult"));
			} catch (CCUtilityServicesException e) {
				LOGGER.error("ERROR: PhonebookServices: problem in PhoneBookServiceImpl/getIOSVersion", e);
			}
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("call has exited the operation getPhoneBookSearchResult in PhoneBookDAOImpl");
		}
		return phoneBookSearchEnvelope;
	}

	private void setSearchSpeciality(SearchInput searchInput, PhoneBookSearchEnvelope phoneBookSearchEnvelope) {
		if (searchInput.getSearchSpecialty() != null
				&& StringUtils.isNotBlank(searchInput.getSearchSpecialty().getName())) {
			SearchSpecialty sp = new SearchSpecialty();
			sp.setName(searchInput.getSearchSpecialty().getName());
			if (StringUtils.isNotBlank(searchInput.getSearchSpecialty().getCode())) {
				sp.setCode(searchInput.getSearchSpecialty().getCode());
			}
			phoneBookSearchEnvelope.setSearchSpecialty(sp);
		}
	}

	private void setSearchFacility(SearchInput searchInput, PhoneBookSearchEnvelope phoneBookSearchEnvelope) {
		if (searchInput.getSearchFacility() != null
				&& StringUtils.isNotBlank(searchInput.getSearchFacility().getCode())) {
			SearchFacility sf = new SearchFacility();
			sf.setCode(searchInput.getSearchFacility().getCode());
			sf.setName(searchInput.getSearchFacility().getName());
			phoneBookSearchEnvelope.setSearchFacility(sf);
		}
	}

	private void logStatements(SearchInput searchInput, List<PhoneBookClinician> phoneBookClinicians,
			int srchResultCount) {
		LOGGER.debug("Total number of search result already displayed on app " + searchInput.getSkipCount());
		LOGGER.debug("Total number of search result count " + srchResultCount);
		LOGGER.debug("Total number of results will be displayed  " + phoneBookClinicians.size());
	}

	private void setHasMoreData(PhoneBookSearchEnvelope phoneBookSearchEnvelope,
			List<PhoneBookClinician> phoneBookClinicians, int srchResultCount, int dispayNoOfRecords) {
		if (srchResultCount == dispayNoOfRecords || srchResultCount > dispayNoOfRecords) {
			// set HasMoredata to true
			phoneBookSearchEnvelope.setHasMoreData(true);
			if (CollectionUtils.isNotEmpty(phoneBookClinicians)) {
				phoneBookClinicians.remove((phoneBookClinicians.size()) - 1);
			}
		} else if (new Integer(srchResultCount) < new Integer(dispayNoOfRecords)) {
			phoneBookSearchEnvelope.setHasMoreData(false);
		}
	}

	/**
	 * Sorts PhoneBookClinicians result with facility name , last name, first name
	 * and speciality name
	 * 
	 * @param phoneBookClinicians
	 */
	private void sortPhoneBookClinicians(List<PhoneBookClinician> phoneBookClinicians) {
		Collections.sort(phoneBookClinicians, new Comparator<PhoneBookClinician>() {
			public int compare(PhoneBookClinician o1, PhoneBookClinician o2) {
				try {

					String s1 = (o1.getFacility().getName() + " " + o1.getLastName() + " " + o1.getFirstName() + " "
							+ o1.getSpecialty().getName()).toLowerCase();
					String s2 = (o2.getFacility().getName() + " " + o2.getLastName() + " " + o2.getFirstName() + " "
							+ o2.getSpecialty().getName()).toLowerCase();
					// Sorting the list
					return s1.compareTo(s2);
				} catch (Exception e) {
					LOGGER.error("Error while executing sortPhoneBookClinicians ", e);
				}
				return 0;
			}
		});
	}

	/**
	 * This method prepares PhoneBookClinicians with primary location contact
	 * information like phone number and email address
	 * 
	 * @phoneBookClinicians
	 * @param nuids
	 */

	private void preparePhonBookCliniciansContacts(List<PhoneBookClinician> phoneBookClinicians,
			HashSet<String> nuIds) {
		Map<String, ClinicianPhoneObject> clnPhoneMap = null;
		clnPhoneMap = commonUtil.getClinicianContacts(nuIds);
		if (!clnPhoneMap.isEmpty()) {
			for (PhoneBookClinician pbc : phoneBookClinicians) {
				if (clnPhoneMap.containsKey(pbc.getNuid())) {
					ClinicianPhoneObject clinicianPhoneObject = clnPhoneMap.get(pbc.getNuid());
					List<ClinicianContact> contacts = new ArrayList<>();
					addToList(clinicianPhoneObject, contacts);
					pbc.setContacts(contacts);
				}
			}
		}
	}

	private void addToList(ClinicianPhoneObject clinicianPhoneObject, List<ClinicianContact> contacts) {
		if (StringUtils.isNotBlank(clinicianPhoneObject.getResourceCellPhoneNumber())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(PhoneBookServiceConstants.MOBILE);
			contact.setNumber(StringUtil.getNumberFormat(clinicianPhoneObject.getResourceCellPhoneNumber().trim()));
			contacts.add(contact);
		}
		if (StringUtils.isNotBlank(clinicianPhoneObject.getResourcePagerPhoneNumber())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(PhoneBookServiceConstants.PAGER);
			contact.setNumber(StringUtil.getNumberFormat(clinicianPhoneObject.getResourcePagerPhoneNumber().trim()));
			contacts.add(contact);
		}
		if (StringUtils.isNotBlank(clinicianPhoneObject.getResourcePrivateExternalPhoneNumber())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(PhoneBookServiceConstants.OFFICE);
			contact.setNumber(
					StringUtil.getNumberFormat(clinicianPhoneObject.getResourcePrivateExternalPhoneNumber().trim()));
			contacts.add(contact);
		}
		if (StringUtils.isNotBlank(clinicianPhoneObject.getResourcePrivateInternalPhoneNumber())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(PhoneBookServiceConstants.TIE_LINE);
			contact.setNumber(StringUtil
					.convertToTieLineNumber(clinicianPhoneObject.getResourcePrivateInternalPhoneNumber().trim()));
			contacts.add(contact);
		}
		if (StringUtils.isNotBlank(clinicianPhoneObject.getEmailAddress())) {
			ClinicianContact contact = new ClinicianContact();
			contact.setType(PhoneBookServiceConstants.EMAIL);
			contact.setNumber(StringUtil.getNumberFormat(clinicianPhoneObject.getEmailAddress().trim().toLowerCase()));
			contacts.add(contact);
		}
	}

	/**
	 * This method prepares PhoneBookClinicians with primary and secondary locations
	 * 
	 * @param facility
	 * 
	 * @param phoneBookClinicians
	 * @param nuIds
	 */
	private void preparePhonBookCliniciansOtherLocations(String searchFacilityName,
			final List<PhoneBookClinician> phoneBookClinicians, final HashSet<String> nuIds) {
		Map<String, List<String>> nuidOtherLocationsMap = new HashMap<>();

		if (phoneBookClinicians != null && !phoneBookClinicians.isEmpty() && nuIds != null && !nuIds.isEmpty()) {

			nuidOtherLocationsMap = phoneBookDao.preparePhonBookCliniciansOtherLocations(nuIds);
			preparePhoneBookCliniciansWithOtherLocations(nuidOtherLocationsMap, phoneBookClinicians,
					searchFacilityName);

		}
	}

	/**
	 * Adding secondary locations to phonebook clinicians
	 * 
	 * @param nuidOtherLocationsMap
	 * @param phoneBookClinicians
	 * @param searchFacilityName
	 */
	private void preparePhoneBookCliniciansWithOtherLocations(Map<String, List<String>> nuidOtherLocationsMap,
			List<PhoneBookClinician> phoneBookClinicians, String searchFacilityName) {
		for (PhoneBookClinician phoneBookClinician : phoneBookClinicians) {
			if (StringUtils.isNotBlank(phoneBookClinician.getNickName())) {
				phoneBookClinician.setName(ClinicianConnectUtilityMethods.displayNickName(
						phoneBookClinician.getFirstName(), phoneBookClinician.getLastName(),
						phoneBookClinician.getNickName(), phoneBookClinician.getProfTitle()));
			} else {
				phoneBookClinician
						.setName(ClinicianConnectUtilityMethods.getDisplayName(phoneBookClinician.getFirstName(),
								phoneBookClinician.getLastName(), phoneBookClinician.getProfTitle()));
			}

			List<String> otherLocations = nuidOtherLocationsMap.get(phoneBookClinician.getNuid());
			if (CollectionUtils.isEmpty(otherLocations)) {
				continue;
			}
			// removing primary facility from other locations if it is present
			otherLocations.remove(phoneBookClinician.getFacility().getName());
			if (CollectionUtils.isNotEmpty(otherLocations)) {
				Collections.sort(otherLocations);
				// Adjusting search facility name to top on the other locations
				if (StringUtils.isNotBlank(searchFacilityName) && otherLocations.contains(searchFacilityName)) {
					otherLocations.remove(searchFacilityName);
					otherLocations.add(0, searchFacilityName);
				}
				phoneBookClinician.setOtherLocations(StringUtils.join(otherLocations, ", "));
			}
		}
	}

	public void setStatusCodeForPhoneBookSearch(PhoneBookSearchEnvelope phoneBookSearchEnvelope,
			PhoneBookSearchOutput phoneBookSearchOutput, Status status) {
		if (phoneBookSearchEnvelope.getAppVersion() != null) {
			status.setCode("200");
			status.setMessage(PhoneBookServiceConstants.SUCCESS);
			phoneBookSearchOutput.setAppVersion(phoneBookSearchEnvelope.getAppVersion());
		} else {
			status.setCode("204");
			status.setMessage(PhoneBookServiceConstants.NO_CONTENT_FOR_THE_PROVIDED_INPUT_PROBLEM);
		}
	}

	@Override
	public PhoneBookSpecialtyEnvelope getPhoneBookSpecialtyList(AppVersionInput input) throws InvalidDataFormatException {
	
		PhoneBookSpecialtyEnvelope phoneBookSpecialtyEnvelope = new PhoneBookSpecialtyEnvelope();
		List<PhoneBookSpecialty> phoneBookspecialtyList = phoneBookDao.getPhoneBookSpecialtyList(input);
		if (!phoneBookspecialtyList.isEmpty()) {
			phoneBookSpecialtyEnvelope.setPhoneBookSpecialties(phoneBookspecialtyList);
		}
		AppVersion appversion = new AppVersion();
		try {
			appversion = commonUtil.getAppVersion(input.getAppVersion());
			if (appversion != null && appversion.getVersion() != null && appversion.getInstructions() != null) {
				phoneBookSpecialtyEnvelope.setAppVersion(appversion);
			}
			phoneBookSpecialtyEnvelope.setiOSVersion(commonUtil.getIOSVersion(input.getDeviceType(),
					input.getiOSVersion(), "getPhoneBookSpecialtyList"));

		} catch (CCUtilityServicesException e) {
			LOGGER.error("ERROR: PhonebookServices: problem in PhoneBookServiceImpl/getPhoneBookSpecialtyList/getAppVersion", e);
		}
		return phoneBookSpecialtyEnvelope;
	}

	@Override
	public PhoneBookFacilityEnvelope getPhoneBookFacilityList(AppVersionInput input) throws InvalidDataFormatException {
		List<PhoneBookFacility> phoneBookfacilityList=phoneBookDao.getPhoneBookFacilityList(input);
		PhoneBookFacilityEnvelope phoneBookFacilityEnvelope = new PhoneBookFacilityEnvelope();
		sortPhoneBookFacilityList(phoneBookfacilityList);
		if (!phoneBookfacilityList.isEmpty()) {
			phoneBookFacilityEnvelope.setPhoneBookFacilities(phoneBookfacilityList);
		}
		AppVersion appversion = new AppVersion();
		try {
			appversion = commonUtil.getAppVersion(input.getAppVersion());
			if (appversion != null && appversion.getVersion() != null && appversion.getInstructions() != null) {
				phoneBookFacilityEnvelope.setAppVersion(appversion);
			}
			phoneBookFacilityEnvelope.setiOSVersion(commonUtil.getIOSVersion(input.getDeviceType(),
					input.getiOSVersion(), "getPhoneBookFacilityList"));
		} catch (CCUtilityServicesException e) {
			LOGGER.error("ERROR: PhonebookServices: problem in PhoneBookServiceImpl/getPhoneBookFacilityList/getAppVersion", e);
		}
		return phoneBookFacilityEnvelope;
	}
	public void sortPhoneBookFacilityList(List<PhoneBookFacility> phoneBookfacilityList) {
		Collections.sort(phoneBookfacilityList, new Comparator<PhoneBookFacility>() {
			@Override
			public int compare(PhoneBookFacility o1, PhoneBookFacility o2) {
				// Sorting the list
				return o1.getName().compareTo(o2.getName());
			}
		});
	}
}
