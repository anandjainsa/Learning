package org.kp.tpmg.ttg.clinicianconnect.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.ClinicianContact;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookFacility;
import org.kp.tpmg.clinicianconnect.cclib.model.mdoprovider.PhoneBookSpecialty;
import org.kp.tpmg.clinicianconnect.ccutilityservices.common.AppProperties;
import org.kp.tpmg.clinicianconnect.ccutilityservices.common.ClinicianConnectLogger;
import org.kp.tpmg.clinicianconnect.ccutilityservices.common.StringUtil;
import org.kp.tpmg.clinicianconnect.ccutilityservices.exception.InvalidDataFormatException;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.AppVersionInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.ClinicianPhoneObject;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchFacility;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchInput;
import org.kp.tpmg.clinicianconnect.ccutilityservices.model.SearchSpecialty;
import org.kp.tpmg.ttg.clinicianconnect.common.util.PhoneBookServiceConstants;
import org.kp.tpmg.ttg.clinicianconnect.common.util.QueryConstants;
import org.kp.tpmg.ttg.clinicianconnect.dao.PhoneBookDao;
import org.kp.tpmg.ttg.clinicianconnect.model.ClinicianContactDetails;
import org.kp.tpmg.ttg.clinicianconnect.model.ClinicianDetails;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookClinician;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookClinicianDesktop;
import org.kp.tpmg.ttg.clinicianconnect.model.PhoneBookSearchEnvelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PhoneBookDaoImpl implements PhoneBookDao {

	private static final Logger LOGGER = Logger.getLogger(PhoneBookDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Value("${MDO_DB_NAME}")
	private String mdoDbName;

	@SuppressWarnings("rawtypes")
	@Override
	public ClinicianDetails getClinicianDetailsForNuid(String nuid) {
		ClinicianDetails clinicianDetails = new ClinicianDetails();
		if (StringUtils.isNotBlank(nuid)) {
			try {
				List<Map<String, Object>> rows = jdbcTemplate.queryForList(
						QueryConstants.GET_CLINICIAN_DETAILS_FOR_NUID
								.replace(PhoneBookServiceConstants.MDO_DB_NAME_PROPERTY_KEY, mdoDbName),
						new Object[] { nuid, nuid });
				for (Map row : rows) {
					if (null != row.get(PhoneBookServiceConstants.NUID_CAMELCASE)) {
						clinicianDetails.setNuid(row.get(PhoneBookServiceConstants.NUID_CAMELCASE).toString());
					}
					if (null != row.get(PhoneBookServiceConstants.FACILITY_NAME)) {
						clinicianDetails.setPrimaryFacility(StringUtil
								.getNormalFacilityString(row.get(PhoneBookServiceConstants.FACILITY_NAME).toString()));
					}
					if (null != row.get(PhoneBookServiceConstants.SPECIALTY_NAME)) {
						clinicianDetails
								.setPrimarySpecialty(row.get(PhoneBookServiceConstants.SPECIALTY_NAME).toString());
					}
					if (null != row.get(PhoneBookServiceConstants.SOURCE_TYPE)) {
						clinicianDetails.setSourceType(row.get(PhoneBookServiceConstants.SOURCE_TYPE).toString());
					}
					if (null != row.get(PhoneBookServiceConstants.ROLE)) {
						clinicianDetails.setRole(row.get(PhoneBookServiceConstants.ROLE).toString());
					}
					if (null != row.get(PhoneBookServiceConstants.ACTIVE_IND)) {
						clinicianDetails.setActiveInd(row.get(PhoneBookServiceConstants.ACTIVE_IND).toString());
					}
					if (null != row.get(PhoneBookServiceConstants.MED_CENTER_CONTACTS_MANUAL_OVERRIDE_IND)) {
						clinicianDetails.setMedCenterContactsManualOverRideIndicator(
								row.get(PhoneBookServiceConstants.MED_CENTER_CONTACTS_MANUAL_OVERRIDE_IND).toString());
					}
					if (null != row.get(PhoneBookServiceConstants.FIRSTNAME)) {
						clinicianDetails.setFirstName(row.get(PhoneBookServiceConstants.FIRSTNAME).toString());
					}
					if (null != row.get(PhoneBookServiceConstants.LASTNAME)) {
						clinicianDetails.setLastName(row.get(PhoneBookServiceConstants.LASTNAME).toString());
					}
					if (null != row.get(PhoneBookServiceConstants.CC_USERS_ID)) {
						String sourceType = row.get(PhoneBookServiceConstants.SOURCE_TYPE).toString();
						if (StringUtils.isNotBlank(sourceType)
								&& sourceType.equalsIgnoreCase(PhoneBookServiceConstants.CC_BLOCK_LIST)) {
							clinicianDetails.setHasAccessToCC("N");
						} else {
							clinicianDetails.setHasAccessToCC("Y");
						}
					} else {
						clinicianDetails.setHasAccessToCC("N");
					}
				}
				clinicianDetails.setAvailable(PhoneBookServiceConstants.YES_INDICATOR);
				if (StringUtils.isBlank(clinicianDetails.getAvailable())
						|| !PhoneBookServiceConstants.YES_INDICATOR.equalsIgnoreCase(clinicianDetails.getAvailable())) {
					clinicianDetails.setAvailable(PhoneBookServiceConstants.NO_INDICATOR);
				}
				clinicianDetails.setIsUserExists(clinicianDetails.getAvailable());
			} catch (Exception e) {
				LOGGER.error(
						"ERROR: PhonebookSevices/PhonebookDaoImpl: problem in getting Clinician Details in the action getClinicianDetailsForNuid",
						e);
			}
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("call has exited the operation getClinicianDetailsForNuid in PhonebookDaoImpl");
		}
		return clinicianDetails;
	}

	@Override
	public String getNickNameUsingResourceId(String resourceId) {
		LOGGER.info("call has entered the operation getNickNameUsingResourceId in PhoneBookDAOImpl");
		//String mdoDbName = ClinicianConnectProperties.getProperty(PhoneBookServiceConstants.MDO_DB_NAME_PROPERTY_KEY);
		String nickname = null;
		try {
			nickname = jdbcTemplate.queryForObject(
					QueryConstants.GET_NICK_NAME_FROM_CCUSERS_BASED_ON_RESOURCEID
							.replace(PhoneBookServiceConstants.MDO_DB_NAME_PROPERTY_KEY, mdoDbName),
					new Object[] { resourceId }, String.class);
		} catch (Exception e) {
			LOGGER.error("ERROR: PhonebookService/PhonebookDaoImpl: problem in getNickNameUsingNuid", e);
		}
		LOGGER.info("call has returned the operation getNickNameUsingResourceId in PhoneBookDAOImpl");
		return nickname;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ClinicianPhoneObject> getClinicianContacts(String resourceId) {
		LOGGER.info("call has entered the operation getClinicianContacts in PhoneBookDAOImpl");

		List<ClinicianPhoneObject> clinicianPhoneObject = new ArrayList<>();
		try {
			clinicianPhoneObject = jdbcTemplate.query(QueryConstants.GET_CONTACTS,
					new Object[] { resourceId }, new BeanPropertyRowMapper(ClinicianPhoneObject.class));
		} catch (Exception e) {
			LOGGER.error("ERROR: PhonebookService/PhonebookDaoImpl: problem in getClinicianContacts", e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("call has returned the operation getClinicianContacts in PhonebookDaoImpl");
		}
		LOGGER.info("call has returned the operation getClinicianContacts in PhoneBookDAOImpl");
		return clinicianPhoneObject;
	}

	private HashMap<String, Boolean> getFavMap(SearchInput searchInput, Calendar cal1) {
		HashMap<String, Boolean> favMap = new HashMap<>();
		List<String> nuidList = new ArrayList<>();
		try {
			if (null != searchInput.getNuid()) {
				nuidList = jdbcTemplate.queryForList(QueryConstants.FIND_FAV,
						new Object[] { searchInput.getNuid() }, String.class);
				Calendar cal2 = Calendar.getInstance();
				if (LOGGER.isDebugEnabled()) {
					ClinicianConnectLogger.logPerformanceTime(LOGGER, "Time taken for phoneBookfavStatement DB Call : ",
							cal1, cal2);
				}
				for (String nuid : nuidList) {
					if (null != nuid)
						favMap.put(nuid, true);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("SearchPhoneBookDesktopFavorites: Number of Favorites: " + favMap.size());
				}
			}
		} catch (Exception e) {
			LOGGER.error("ERROR: PhonebookService/PhonebookDaoImpl: problem in getClinicianContacts", e);
		}
		return favMap;
	}

	@Override
	public Map<String, Object> getPhoneBookSearchDesktop(final String firstName, final String lastName,
			final String speciality, final String facility, final SearchInput searchInput) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("call has entered the operation getPhoneBookSearchDesktop in PhoneBookDAOImpl");
		}

		final Calendar startTime = Calendar.getInstance();
		Map<String, Object> response = null;
		try {
			response = jdbcTemplate.execute(new CallableStatementCreator() {
				@Override
				public CallableStatement createCallableStatement(Connection connection) throws SQLException {

					CallableStatement callStmt = connection.prepareCall(QueryConstants.SEARCH_PHONEBOOK_DESKTOP,
							ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					callStmt.setString(1, firstName);
					callStmt.setString(2, lastName);
					callStmt.setString(3, facility);
					callStmt.setString(4, speciality);
					callStmt.setInt(5, searchInput.getSkipCount());
					callStmt.registerOutParameter(6, java.sql.Types.INTEGER);
					callStmt.registerOutParameter(7, java.sql.Types.INTEGER);
					return callStmt;
				}
			}, new CallableStatementCallback<Map<String, Object>>() {
				@Override
				public Map<String, Object> doInCallableStatement(CallableStatement statement) throws SQLException {

					ResultSet conatactsResultSet = null;
					List<String> nuidList = new ArrayList<>();
					List<PhoneBookClinicianDesktop> phoneBookClinicians = new ArrayList<>();
					Map<String, Object> response = new HashMap<>();

					int srchResultCount = 0;
					int dispayNoOfRecords = 0;

					statement.execute();
					ResultSet searchResultSet = statement.getResultSet();

					Calendar cal1 = Calendar.getInstance();
					if (LOGGER.isDebugEnabled()) {
						ClinicianConnectLogger.logPerformanceTime(LOGGER,
								"Time taken to Call SearchPhoneBookDesktop Procedure : ", startTime, cal1);
					}
					HashMap<String, Boolean> favMap = null;
					if (searchResultSet.next()) {
						favMap = getFavMap(searchInput, cal1);
						Calendar cal3 = Calendar.getInstance();
						do {
							PhoneBookClinicianDesktop phoneBookClinician = new PhoneBookClinicianDesktop();
							List<ClinicianContact> contacts = new ArrayList<>();
							if (StringUtils
									.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.NUID_CAMELCASE)))
								phoneBookClinician
										.setNuid(searchResultSet.getString(PhoneBookServiceConstants.NUID_CAMELCASE));
							if (null != favMap && favMap.size() > 0 && null != favMap.get(phoneBookClinician.getNuid())
									&& favMap.get(phoneBookClinician.getNuid())) {
								phoneBookClinician.setFavorite(true);
							}
							if (StringUtils
									.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.RESOURCE_ID)))
								phoneBookClinician.setResourceId(
										searchResultSet.getString(PhoneBookServiceConstants.RESOURCE_ID));
							if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.FIRSTNAME)))
								phoneBookClinician
										.setFirstName(searchResultSet.getString(PhoneBookServiceConstants.FIRSTNAME));
							if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.LASTNAME)))
								phoneBookClinician
										.setLastName(searchResultSet.getString(PhoneBookServiceConstants.LASTNAME));
							if (StringUtils
									.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.PROVIDER_TYPE))) {
								phoneBookClinician.setProviderType(
										searchResultSet.getString(PhoneBookServiceConstants.PROVIDER_TYPE));
							}
							if (StringUtils
									.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.NICK_NAME))) {
								phoneBookClinician
										.setNickName(searchResultSet.getString(PhoneBookServiceConstants.NICK_NAME));
							}
							phoneBookClinician.setPrimaryLocInd(PhoneBookServiceConstants.YES_INDICATOR);
							if (StringUtils
									.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.FACILITY_CODE))
									&& StringUtils.isNotBlank(searchResultSet
											.getString(PhoneBookServiceConstants.FACILITY_NAME_WITH_CAMEL_CASE))) {
								SearchFacility sfa = new SearchFacility();
								sfa.setCode(searchResultSet.getString(PhoneBookServiceConstants.FACILITY_CODE));
								sfa.setName(StringUtil.getNormalFacilityString(searchResultSet
										.getString(PhoneBookServiceConstants.FACILITY_NAME_WITH_CAMEL_CASE)));
								phoneBookClinician.setFacility(sfa);
							}
							if (StringUtils.isNotBlank(
									searchResultSet.getString(PhoneBookServiceConstants.SPECIALTY_CODE_CAMEL_CASE))
									&& StringUtils.isNotBlank(searchResultSet
											.getString(PhoneBookServiceConstants.SPECIALTY_NAME_CAMEL_CASE))) {
								SearchSpecialty sp = new SearchSpecialty();
								sp.setCode(
										searchResultSet.getString(PhoneBookServiceConstants.SPECIALTY_CODE_CAMEL_CASE));
								sp.setName(StringUtil.getNormalSpecialty(searchResultSet
										.getString(PhoneBookServiceConstants.SPECIALTY_NAME_CAMEL_CASE)));
								phoneBookClinician.setSpecialty(sp);
							}
							if (StringUtils.isNotBlank(
									searchResultSet.getString(PhoneBookServiceConstants.HOME_PAGE_URL_CAMEL_CASE)))
								phoneBookClinician.setHomePageURL(StringUtils.normalizeSpace(
										searchResultSet.getString(PhoneBookServiceConstants.HOME_PAGE_URL_CAMEL_CASE)));
							if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.PHOTO_WEB))
									&& searchResultSet.getString(PhoneBookServiceConstants.PHOTO_WEB)
											.contains(PhoneBookServiceConstants.PHOTO_WEB_))
								phoneBookClinician.setPhotoUrlString(PhoneBookServiceConstants.PHOTO_URL
										+ searchResultSet.getString(PhoneBookServiceConstants.PHOTO_WEB).replaceAll(
												PhoneBookServiceConstants.PHOTO_WEB_,
												PhoneBookServiceConstants.THUMBNAIL1_));
							if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.FIRSTNAME))
									&& StringUtils
											.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.LASTNAME))
									&& StringUtils.isNotBlank(
											searchResultSet.getString(PhoneBookServiceConstants.PROF_TITLE))) {
								phoneBookClinician.setName(StringUtils.normalizeSpace(
										searchResultSet.getString(PhoneBookServiceConstants.FIRSTNAME) + " "
												+ searchResultSet.getString(PhoneBookServiceConstants.LASTNAME) + ", "
												+ searchResultSet.getString(PhoneBookServiceConstants.PROF_TITLE)));
							} else if (StringUtils
									.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.FIRSTNAME))
									&& StringUtils.isNotBlank(
											searchResultSet.getString(PhoneBookServiceConstants.LASTNAME))) {
								phoneBookClinician.setName(StringUtils
										.normalizeSpace(searchResultSet.getString(PhoneBookServiceConstants.FIRSTNAME)
												+ " " + searchResultSet.getString(PhoneBookServiceConstants.LASTNAME)));
							}
							if (null == searchResultSet.getString(PhoneBookServiceConstants.FACILITY_CODE)
									&& null == searchResultSet.getString(PhoneBookServiceConstants.PAGER_DESKP)
									&& null == searchResultSet.getString(PhoneBookServiceConstants.TIELINE_DESKP)
									&& null == searchResultSet.getString(PhoneBookServiceConstants.OFFICE_DESKP)
									&& null != searchResultSet.getString(PhoneBookServiceConstants.MOBILE_DESKP)) {
								ClinicianContact contact = new ClinicianContact();
								contact.setType(PhoneBookServiceConstants.MOBILE);
								contact.setNumber(StringUtil.getNumberFormat(
										searchResultSet.getString(PhoneBookServiceConstants.MOBILE_DESKP)));
								LOGGER.debug(
										"MobileNumber is avaliable from clinicianphone where facilitycode is null");
								contacts.add(contact);
							} else {
								if (StringUtils.isNotBlank(
										searchResultSet.getString(PhoneBookServiceConstants.MOBILE_DESKP))) {
									ClinicianContact contact = new ClinicianContact();
									contact.setType(PhoneBookServiceConstants.MOBILE);
									contact.setNumber(StringUtil.getNumberFormat(
											searchResultSet.getString(PhoneBookServiceConstants.MOBILE_DESKP)));
									LOGGER.debug(PhoneBookServiceConstants.MOBILE_NUMBER_IS_AVALIBLE);
									contacts.add(contact);
								}
								if (StringUtils
										.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.PAGER_DESKP))) {
									ClinicianContact contact = new ClinicianContact();
									contact.setType(PhoneBookServiceConstants.PAGER);
									contact.setNumber(StringUtil.getNumberFormat(
											searchResultSet.getString(PhoneBookServiceConstants.PAGER_DESKP)));
									LOGGER.debug(PhoneBookServiceConstants.PAGER_NUMBER_IS_AVALIBLE);
									contacts.add(contact);
								}
								if (StringUtils.isNotBlank(
										searchResultSet.getString(PhoneBookServiceConstants.OFFICE_DESKP))) {
									ClinicianContact contact = new ClinicianContact();
									contact.setType(PhoneBookServiceConstants.OFFICE);
									contact.setNumber(StringUtil.getNumberFormat(
											searchResultSet.getString(PhoneBookServiceConstants.OFFICE_DESKP)));
									LOGGER.debug(PhoneBookServiceConstants.OFFICE_NUMBER_IS_AVALIBLE);
									contacts.add(contact);
								}
								if (StringUtils.isNotBlank(
										searchResultSet.getString(PhoneBookServiceConstants.TIELINE_DESKP))) {
									ClinicianContact contact = new ClinicianContact();
									contact.setType(PhoneBookServiceConstants.TIE_LINE);
									contact.setNumber(StringUtil.convertToTieLineNumber(
											searchResultSet.getString(PhoneBookServiceConstants.TIELINE_DESKP)));
									LOGGER.debug(PhoneBookServiceConstants.TIE_LINE_IS_AVALIABLE);
									contacts.add(contact);
								}
							}

							phoneBookClinician.setContacts(contacts);
							nuidList.add(searchResultSet.getString(PhoneBookServiceConstants.NUID_CAMELCASE));
							phoneBookClinicians.add(phoneBookClinician);
							response.put("phoneBookClinicians", phoneBookClinicians);

						} while (searchResultSet.next());

						Calendar cal4 = Calendar.getInstance();
						ClinicianConnectLogger.logPerformanceTime(LOGGER,
								"Time taken to Populate Phone Book Desktop DB Details List : ", cal3, cal4);
					}
					srchResultCount = statement.getInt(6);
					dispayNoOfRecords = statement.getInt(7);
					List<Integer> records = new ArrayList<>();
					records.add(srchResultCount);
					records.add(dispayNoOfRecords);
					response.put("records", records);

					// Add logic to get the second result set
					Calendar cal7 = Calendar.getInstance();

					statement.execute();
					statement.getMoreResults();
					conatactsResultSet = statement.getResultSet();

					List<ClinicianContactDetails> clinicianPhoneList = getclinicianContactList(conatactsResultSet);
					Calendar cal8 = Calendar.getInstance();
					ClinicianConnectLogger.logPerformanceTime(LOGGER,
							"Time taken to execute getclinicianContactList() from Database : ", cal7, cal8);

					response.put("clinicianPhoneList", clinicianPhoneList);
					response.put("nuidList", nuidList);
					return response;
				}
			});
		} catch (Exception e) {
			LOGGER.error("Error while fetching desktop deatils in PhoneBookDAOImpl", e);
		}
		return response;
	}

	private List<ClinicianContactDetails> getclinicianContactList(ResultSet searchResultSet) {

		List<ClinicianContactDetails> clnPhoneList = new ArrayList<>();
		LOGGER.info("call has entered the operation getclinicianContactList in PhoneBookDAOImpl");
		if (null == searchResultSet) {
			return null;
		}
		try {
			while (searchResultSet.next()) {
				ClinicianContactDetails clinicianContactDetails = new ClinicianContactDetails();
				if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.NUID_UPPERCASE)))
					clinicianContactDetails
							.setNuid(searchResultSet.getString(PhoneBookServiceConstants.NUID_UPPERCASE).trim());
				if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.RESOURCE_ID)))
					clinicianContactDetails
							.setResourceId(searchResultSet.getString(PhoneBookServiceConstants.RESOURCE_ID).trim());
				if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.FACILITY_CODE)))
					clinicianContactDetails
							.setFacilityCode(searchResultSet.getString(PhoneBookServiceConstants.FACILITY_CODE).trim());
				if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.FACILITY_NAME)))
					clinicianContactDetails.setFacilityName(StringUtil.getNormalFacilityString(
							searchResultSet.getString(PhoneBookServiceConstants.FACILITY_NAME).trim()));
				if (StringUtils.isNotBlank(
						searchResultSet.getString(PhoneBookServiceConstants.RESOURCE_PAGER_PHONE_NUMBER_CAMEL_CASE)))
					clinicianContactDetails.setResourcePagerPhoneNumber(searchResultSet
							.getString(PhoneBookServiceConstants.RESOURCE_PAGER_PHONE_NUMBER_CAMEL_CASE));
				if (StringUtils.isNotBlank(
						searchResultSet.getString(PhoneBookServiceConstants.RESOURCE_CELL_PHONE_NUMBER_CAMEL_CASE)))
					clinicianContactDetails.setResourceCellPhoneNumber(
							searchResultSet.getString(PhoneBookServiceConstants.RESOURCE_CELL_PHONE_NUMBER_CAMEL_CASE));
				if (StringUtils.isNotBlank(searchResultSet
						.getString(PhoneBookServiceConstants.RESOURCE_PRIVATE_EXTERNAL_PHONE_NUMBER_CAMEL_CASE)))
					clinicianContactDetails.setResourcePrivateExternalPhoneNumber(searchResultSet
							.getString(PhoneBookServiceConstants.RESOURCE_PRIVATE_EXTERNAL_PHONE_NUMBER_CAMEL_CASE));
				if (StringUtils.isNotBlank(searchResultSet
						.getString(PhoneBookServiceConstants.RESOURCE_PRIVATE_INTERNAL_PHONE_NUMBER_CAMEL_CASE)))
					clinicianContactDetails.setResourcePrivateInternalPhoneNumber(searchResultSet
							.getString(PhoneBookServiceConstants.RESOURCE_PRIVATE_INTERNAL_PHONE_NUMBER_CAMEL_CASE));
				clnPhoneList.add(clinicianContactDetails);
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured while executing getclinicianContactList in PhoneBookDAOImpl for : ", e);
		}
		LOGGER.info("call has exited the operation getclinicianContactList in PhoneBookDAOImpl");
		return clnPhoneList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, List<String>> preparePhonBookCliniciansOtherLocations(HashSet<String> nuIds) {

		final Map<String, List<String>> nuidOtherLocationsMap = new HashMap<>();
		LOGGER.debug("fetching phone book clinicians secondary facility details : ");
		try {
			String nuid = null;
			String facilityName = null;
			String query = buildQuery(nuIds);
			String mdoDbName = AppProperties.getProperty(PhoneBookServiceConstants.MDO_DB_NAME_PROPERTY_KEY);
			List<Map<String, Object>> rows = jdbcTemplate
					.queryForList(query.replace(PhoneBookServiceConstants.MDO_DB_NAME_PROPERTY_KEY, mdoDbName));

			for (Map row : rows) {
				if (StringUtils.isNotBlank(row.get(PhoneBookServiceConstants.NUID_CAMELCASE).toString())) {
					nuid = row.get(PhoneBookServiceConstants.NUID_CAMELCASE).toString();
				}
				if (StringUtils.isNotBlank(row.get(PhoneBookServiceConstants.FACILITY_NAME).toString())) {
					facilityName = StringUtil
							.getNormalFacilityString(row.get(PhoneBookServiceConstants.FACILITY_NAME).toString());
				}
				// if nuid contains multiple secondary locations,add this
				// secondary location to the corresponding nuid value
				addOtherLocationsToMap(nuidOtherLocationsMap, nuid, facilityName);
			}
		} catch (Exception e) {
			LOGGER.error(
					"ERROR: ClinicianConnect: problem in PhoneBookDaoImpl class method preparePhonBookCliniciansOtherLocations",
					e);
		}
		return nuidOtherLocationsMap;
	}

	/**
	 * building sql query for multiple nuids with IN clause
	 * 
	 * @param nuIds
	 * @return
	 */
	private String buildQuery(HashSet<String> nuIds) {
		StringBuilder query = new StringBuilder(QueryConstants.GET_FACILITY_DETAILS);
		String delimiter = "";
		for (String nuid : nuIds) {
			query.append(delimiter).append("'" + nuid + "'");
			delimiter = ",";
		}
		query.append(")");
		return query.toString();
	}

	private void extractPhoneBookClinicians(List<PhoneBookClinician> phoneBookClinicians, ResultSet searchResultSet,
			List<String> nuidList) throws SQLException {
		String nickName;
		while (searchResultSet.next()) {

			PhoneBookClinician phoneBookClinician = new PhoneBookClinician();
			setNuidAndResourceId(searchResultSet, phoneBookClinician);
			if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.PROVIDER_TYPE))) {
				phoneBookClinician.setProviderType(searchResultSet.getString(PhoneBookServiceConstants.PROVIDER_TYPE));
			}
			setNames(searchResultSet, phoneBookClinician);
			if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.PROF_TITLE))) {
				phoneBookClinician.setProfTitle(searchResultSet.getString(PhoneBookServiceConstants.PROF_TITLE).trim());
			}
			nickName = searchResultSet.getString(PhoneBookServiceConstants.NICK_NAME);
			if (StringUtils.isNotBlank(nickName)) {
				phoneBookClinician.setNickName(nickName.trim());
			}
			setFacilitySpeciality(searchResultSet, phoneBookClinician);
			if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.HOME_PAGE_URL_CAMEL_CASE))) {
				phoneBookClinician.setHomePageUrl(StringUtils
						.normalizeSpace(searchResultSet.getString(PhoneBookServiceConstants.HOME_PAGE_URL_CAMEL_CASE)));
			}
			if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.PHOTO_WEB))
					&& searchResultSet.getString(PhoneBookServiceConstants.PHOTO_WEB)
							.contains(PhoneBookServiceConstants.PHOTO_WEB_)) {
				phoneBookClinician.setPhotoUrlString(PhoneBookServiceConstants.PHOTO_URL
						+ searchResultSet.getString(PhoneBookServiceConstants.PHOTO_WEB).replaceAll(
								PhoneBookServiceConstants.PHOTO_WEB_, PhoneBookServiceConstants.THUMBNAIL1_));
			}
			nuidList.add(searchResultSet.getString(PhoneBookServiceConstants.NUID_CAMELCASE));
			phoneBookClinicians.add(phoneBookClinician);
		}
	}

	private void setNuidAndResourceId(ResultSet searchResultSet, PhoneBookClinician phoneBookClinician)
			throws SQLException {
		if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.NUID_CAMELCASE))) {
			phoneBookClinician.setNuid(searchResultSet.getString(PhoneBookServiceConstants.NUID_CAMELCASE));
		}
		if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.RESOURCE_ID))) {
			phoneBookClinician.setResourceId(searchResultSet.getString(PhoneBookServiceConstants.RESOURCE_ID));
		}
	}

	private void setFacilitySpeciality(ResultSet searchResultSet, PhoneBookClinician phoneBookClinician)
			throws SQLException {
		if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.FACILITY_CODE)) && StringUtils
				.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.FACILITY_NAME_WITH_CAMEL_CASE))) {
			SearchFacility sfa = new SearchFacility();
			sfa.setCode(searchResultSet.getString(PhoneBookServiceConstants.FACILITY_CODE));
			sfa.setName(StringUtil.getNormalFacilityString(
					searchResultSet.getString(PhoneBookServiceConstants.FACILITY_NAME_WITH_CAMEL_CASE)));
			phoneBookClinician.setFacility(sfa);

		}
		if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.SPECIALTY_CODE_CAMEL_CASE))
				&& StringUtils
						.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.SPECIALTY_NAME_CAMEL_CASE))) {
			SearchSpecialty sp = new SearchSpecialty();
			sp.setCode(searchResultSet.getString(PhoneBookServiceConstants.SPECIALTY_CODE_CAMEL_CASE));
			sp.setName(StringUtil.getNormalSpecialty(
					searchResultSet.getString(PhoneBookServiceConstants.SPECIALTY_NAME_CAMEL_CASE)));
			phoneBookClinician.setSpecialty(sp);
		}
	}

	private void addOtherLocationsToMap(final Map<String, List<String>> nuidOtherLocationsMap, String nuid,
			final String facilityName) {
		if (nuidOtherLocationsMap.containsKey(nuid)) {
			nuidOtherLocationsMap.get(nuid).add(facilityName);
		} else {
			// adding entry with nuid and other locations
			List<String> otherLocations = new ArrayList<>();
			otherLocations.add(facilityName);
			nuidOtherLocationsMap.put(nuid, otherLocations);
		}
	}

	private void setNames(ResultSet searchResultSet, PhoneBookClinician phoneBookClinician) throws SQLException {
		if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.FIRSTNAME))) {
			phoneBookClinician.setFirstName(searchResultSet.getString(PhoneBookServiceConstants.FIRSTNAME).trim());
		}
		if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.LASTNAME))) {
			phoneBookClinician.setLastName(searchResultSet.getString(PhoneBookServiceConstants.LASTNAME).trim());
		}
		if (StringUtils.isNotBlank(searchResultSet.getString(PhoneBookServiceConstants.MIDDLE_NAME))) {
			phoneBookClinician.setMiddleName(searchResultSet.getString(PhoneBookServiceConstants.MIDDLE_NAME).trim());
		}
	}

	@Override
	public Map<String, Object> getPhoneBookSearchResult(final String firstName, final String lastName,
			final String facilityCode, final String specialty, final int skipCount) {

		final List<String> nuidList = new ArrayList<>();
		final List<PhoneBookClinician> phoneBookClinicians = new ArrayList<>();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("call has entered the operation getPhoneBookSearchResult in PhoneBookDAOImpl");

		}
		Map<String, Object> response = jdbcTemplate.execute(new CallableStatementCreator() {

			@Override
			public CallableStatement createCallableStatement(Connection connection) throws SQLException {
				CallableStatement callStmt = connection.prepareCall(QueryConstants.SEARCH_PHONEBOOK);
				callStmt.setString(1, firstName);
				callStmt.setString(2, lastName);
				callStmt.setString(3, facilityCode);
				callStmt.setString(4, specialty);
				callStmt.setInt(5, skipCount);
				callStmt.registerOutParameter(6, java.sql.Types.INTEGER);
				callStmt.registerOutParameter(7, java.sql.Types.INTEGER);
				return callStmt;
			}
		}, new CallableStatementCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInCallableStatement(CallableStatement statement) throws SQLException {
				Map<String, Object> response = new LinkedHashMap<>();
				statement.execute();
				ResultSet phoneBookResultSet = statement.getResultSet();
				PhoneBookSearchEnvelope phoneBookSearchEnvelope = new PhoneBookSearchEnvelope();
				extractPhoneBookClinicians(phoneBookClinicians, phoneBookResultSet, nuidList);
				int srchResultCount = statement.getInt(6);
				int dispayNoOfRecords = statement.getInt(7);
				phoneBookSearchEnvelope.setPhoneBookClinicians(phoneBookClinicians);
				response.put("phoneBookSearchEnvelope", phoneBookSearchEnvelope);
				response.put("srchResultCount", srchResultCount);
				response.put("dispayNoOfRecords", dispayNoOfRecords);
				response.put("nuidList", nuidList);
				return response;
			}
		});
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("call has returned the operation getPhoneBookSearchResult in PhoneBookDaoImpl");
		}
		return response;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<PhoneBookSpecialty> getPhoneBookSpecialtyList(AppVersionInput input) throws InvalidDataFormatException {
		LOGGER.info("call has entered the operation getPhoneBookSpecialtyList in PhoneBookDAOImpl");
		List<PhoneBookSpecialty> phoneBookspecialtyList = new ArrayList<>();
		try {

			List<Map<String, Object>> rows = jdbcTemplate.queryForList(QueryConstants.FIND_SPECIALTYLIST);

			for (Map row : rows) {
				PhoneBookSpecialty phoneBookSpecialty = new PhoneBookSpecialty();
				if (null != PhoneBookServiceConstants.DISPLAY_SPECIALITY) {
					phoneBookSpecialty.setCode(StringUtil
							.removeSpChPhoneBook(row.get(PhoneBookServiceConstants.DISPLAY_SPECIALITY).toString()));
					phoneBookSpecialty.setName(StringUtil
							.removeSpChPhoneBook(row.get(PhoneBookServiceConstants.DISPLAY_SPECIALITY).toString()));
				}
				phoneBookspecialtyList.add(phoneBookSpecialty);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR: ClinicianConnect: problem in PhoneBookDAOImpl getPhoneBookSpecialtyList", e);
			throw new InvalidDataFormatException("ERROR: ClinicianConnect: problem in PhoneBookDAOImpl getFacilityList");
		}
		LOGGER.info("call has exited the operation getPhoneBookSpecialtyList in PhoneBookDAOImpl");
		return phoneBookspecialtyList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<PhoneBookFacility> getPhoneBookFacilityList(AppVersionInput input) throws InvalidDataFormatException {
		LOGGER.info("call has entered the operation getPhoneBookFacilityList in PhoneBookDAOImpl");
		List<PhoneBookFacility> phoneBookfacilityList = new ArrayList<>();
		try {
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(QueryConstants.FIND_FACILITYLIST
					.replace(PhoneBookServiceConstants.MDO_DB_NAME_PROPERTY_KEY, mdoDbName));
			for (Map row : rows) {
				PhoneBookFacility phoneBookFacility = new PhoneBookFacility();
				if (null != PhoneBookServiceConstants.FACILITY_CODE_CAMEL_CASE) {
					phoneBookFacility.setCode(row.get(PhoneBookServiceConstants.FACILITY_CODE_CAMEL_CASE).toString());
				}
				String name = "";
				if (null != row.get(PhoneBookServiceConstants.FACILTY_NAME)) {
					name = StringUtil.getNormalFacilityString(
							StringUtil.removePhoneBook(row.get(PhoneBookServiceConstants.FACILTY_NAME).toString()));
				}
				if (StringUtils.isNotBlank(name)) {
					phoneBookFacility.setName(name);
					phoneBookfacilityList.add(phoneBookFacility);
				}
			}
		} catch (Exception e) {
			LOGGER.error("ERROR: ClinicianConnect: problem in PhoneBookDAOImpl getFacilityList", e);
			throw new InvalidDataFormatException("ERROR: ClinicianConnect: problem in PhoneBookDAOImpl getFacilityList");
		}
		LOGGER.info("call has exited the operation getPhoneBookFacilityList in PhoneBookDAOImpl");
		return phoneBookfacilityList;
	}
}
