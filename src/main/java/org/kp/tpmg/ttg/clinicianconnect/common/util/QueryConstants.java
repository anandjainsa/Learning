package org.kp.tpmg.ttg.clinicianconnect.common.util;

public class QueryConstants {

	public static final String GET_CLINICIAN_DETAILS_FOR_NUID = "SELECT CCU.CCUsersId, isnull(CCU.NUID,staff.nuid) AS Nuid, isnull(CCU.FirstName,staff.FIRST_NAME) AS FirstName, isnull(CCU.LastName,staff.LAST_NAME) AS LastName, "
			+ " CCU.Source_Type, CCU.ActiveInd, isnull(CCU.MED_CENTER_CONTACTS_MANUAL_OVERRIDE_IND,'N') as MED_CENTER_CONTACTS_MANUAL_OVERRIDE_IND,CCU.Role,FAC.FACILITY_NAME,SPE.SPECIALTY_NAME,'MDO' AS TYPE from "
			+ PhoneBookServiceConstants.MDO_DB_NAME_PROPERTY_KEY + ".dbo.staff staff " + " LEFT OUTER JOIN "
			+ PhoneBookServiceConstants.SCHEMA_NAME + "CCUsers CCU ON staff.nuid = CCU.NUID " + " LEFT OUTER JOIN "
			+ PhoneBookServiceConstants.MDO_DB_NAME_PROPERTY_KEY
			+ ".DBO.PROVIDER_SPECIALTY PROSPEC ON staff.NUID = PROSPEC.NUID  " + " LEFT OUTER JOIN "
			+ PhoneBookServiceConstants.MDO_DB_NAME_PROPERTY_KEY
			+ ".DBO.SPECIALTY SPE ON PROSPEC.SPECIALTY_ID = SPE.SPECIALTY_ID  " + " LEFT OUTER JOIN "
			+ PhoneBookServiceConstants.MDO_DB_NAME_PROPERTY_KEY
			+ ".DBO.FACILITY FAC ON PROSPEC.FACILITY_CODE = FAC.FACILITY_CODE "
			+ " WHERE staff.NUID = ? AND PROSPEC.PRIMARY_INDICATOR = 'Y' " + " UNION "
			+ " SELECT CCU.CCUsersId, isnull(staff.nuid,CCU.NUID) AS Nuid, isnull(staff.FIRST_NAME,CCU.FirstName) AS FirstName, isnull(staff.LAST_NAME,CCU.LastName) AS LastName,"
			+ " CCU.Source_Type, CCU.ActiveInd, CCU.MED_CENTER_CONTACTS_MANUAL_OVERRIDE_IND,CCU.Role,NULL AS FACILITY_NAME,NULL AS SPECIALTY_NAME,'CCUSERS' AS TYPE from "
			+ PhoneBookServiceConstants.SCHEMA_NAME + "CCUsers CCU" + " LEFT OUTER JOIN "
			+ PhoneBookServiceConstants.MDO_DB_NAME_PROPERTY_KEY + ".dbo.staff staff  ON staff.nuid = CCU.NUID "
			+ " WHERE CCU.NUID = ? order by TYPE desc";
	public static final String GET_NICK_NAME_FROM_CCUSERS_BASED_ON_RESOURCEID = "SELECT ccUsers.NICK_NAME FROM "
			+ PhoneBookServiceConstants.SCHEMA_NAME + PhoneBookServiceConstants.CC_DBO_CC_USERS + " ccUsers JOIN "
			+ PhoneBookServiceConstants.MDO_DB_NAME_PROPERTY_KEY
			+ ".dbo.provider provider ON provider.NUID = ccUsers.NUID JOIN "
			+ PhoneBookServiceConstants.MDO_DB_NAME_PROPERTY_KEY
			+ ".dbo.staff s ON provider.nuid = s.nuid WHERE provider.RESOURCE_ID = ? ";
	public static final String GET_CONTACTS = "SELECT ResourceId, FacilityCode, ResourcePagerPhoneNumber, ResourceCellPhoneNumber, ResourcePrivateExternalPhoneNumber, ResourcePrivateInternalPhoneNumber "
			+ "FROM " + PhoneBookServiceConstants.SCHEMA_NAME + "ClinicianPhone " + "WHERE ResourceId = ?";
	public static final String SEARCH_PHONEBOOK_DESKTOP = "{ CALL " + PhoneBookServiceConstants.SCHEMA_NAME
			+ "SearchPhoneBookDesktop(?,?,?,?,?,?,?)}";
	public static final String FIND_FAV = "SELECT NUID FROM [PhoneBookFavorites] where RegNUID = ? ";
	public static final String CC_WEBSERVICE_APP_VERSION = "_7_6";	
	public static final String SEARCH_PHONEBOOK = "{CALL "+PhoneBookServiceConstants.SCHEMA_NAME+"SearchPhoneBook" + CC_WEBSERVICE_APP_VERSION + "(?,?,?,?,?,?,?)}";
	public static final String GET_FACILITY_DETAILS = "SELECT DISTINCT spl.NUID, spl.FACILITY_NAME, spl.FACILITY_CODE FROM "
	+ PhoneBookServiceConstants.CC_DBO_STAFF_PROVIDER_LOCATION + " spl JOIN "
	+ PhoneBookServiceConstants.CC_DBO_CLINICIAN_PHONE + " cph  ON spl.RESOURCE_ID  = cph.RESOURCEID"
	+ " WHERE spl.PRIM_SEARCH_LOC_IND = 'N' AND (cph.ResourcePagerPhoneNumber is not null or cph.ResourceCellPhoneNumber is not null "
	+ " or cph.ResourcePrivateExternalPhoneNumber is not null or cph.ResourcePrivateInternalPhoneNumber is not null) "
	+ " AND spl.NUID IN (";
	public static final String FIND_SPECIALTYLIST = "SELECT distinct DisplaySpecialty as displaySpecialty from "+PhoneBookServiceConstants.SCHEMA_NAME+"SearchSpecialty order by DisplaySpecialty";
	public static final String FIND_FACILITYLIST = "SELECT mdo.FACILITY_CODE as FacilityCode,mdo.FACILITY_NAME as facilityName " + " FROM "
			+ PhoneBookServiceConstants.MDO_DB_NAME_PROPERTY_KEY + ".dbo.FACILITY mdo where mdo.REGION_CODE = 'NCAL' ";
	public QueryConstants() {
	}
}
