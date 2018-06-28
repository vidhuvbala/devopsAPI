/**
 * @Name: UBOAPIConstants.java
 * @Class_Description: This class holds the constants in the UBOAPI application
 *
 * @author: Cognizant Technology Solutions
 * @Created_On: Aug 29, 2016
 *
 *              Confidential and Proprietary. Copyright (C) 2016 D&B. All Rights
 *              Reserved.
 */

package com.dnb.ubo.v3.constants;

public class C {

    public static final String ERRMSG = "";
    public static final String ERROR = "Error:";

    public static final String UB001 = "UB001";
    public static final String UB002 = "UB002";
    public static final String UB003 = "UB003";
    public static final String UB012 = "UB012";
    public static final String UB005 = "UB005";
    public static final String UB015 = "UB015";
    public static final String UB014 = "UB014";
    public static final String UB019 = "UB019";
    public static final String UB017 = "UB017";
    public static final String UB011 = "UB011";
    public static final String UB008 = "UB008";
    public static final String UB016 = "UB016";
    public static final String UB021 = "UB021";
    public static final String UB020 = "UB020";
    public static final String UB022 = "UB022";

    public static final int HTTP200 = 200;
    public static final int HTTP204 = 204;
    public static final int HTTP422 = 422;
    public static final int HTTP503 = 503;
    public static final int HTTP400 = 400;
    public static final int HTTP500 = 500;
    public static final int HTTP504 = 504;

    public static final String HTTPOK = "OK";
    public static final String NOCONTENT = "No Content";
    public static final String UNPROCENTITY = "Unprocessable Entity";
    public static final String SERUNAV = "Service Unavailable";

    public static final String SUCCESS = "Success";
    public static final String FAILURE = "Failure";
    public static final String SUCCESS_TEXT = "Success";
    public static final String NOOWNERSHIPDATA = "Success - No ownership data is present for Target DUNS";
    public static final String SERVICE_UNAVAILABLE_TEXT = "UBO Service Unavailable";

    public static final String FILTERNOOWNERSHIP = "Success - No ownership data meets criteria specified in filters";
    public static final String NOTARGETDUNS = "Success - Target DUNS not found";
    public static final String REQERROR = "Request Error - Request is not well formed";
    public static final String MISSINGPARAMETER = "Request Error - Invalid/missing request parameter";
    public static final String INTERNALERROR = "Server Error - Internal server error";
    public static final String GRAPHTIMEOUT = "Server Error  - Graph database connection timeout";
    public static final String MEDIATYPEERR = "Request Error - Unsupported media type, currently 'application/json' is supported";
    public static final String INVALIDHTTPMETHOD = "Request Error - HTTP method not allowed, currently only 'GET' and 'POST' are supported";
    public static final String GRAPHDBERR = "Server Error - Invalid response from graph database";
    public static final String INVALIDFILTER ="Request Error - Not a valid combination of Filters for Full Ownership Structure";

    public static final String STRUCTURE = "getOwnershipStructure";
    public static final String OWNERSHIPERR = "Target Present with No Data";
    public static final String TARGETABSENT = "Target Not Present";

    public static final String INVALID_DATE = "Unparseable date";
    public static final String INVALID_REQUEST_METHOD = "Request method";
    public static final String INVALID_BOOLEAN_VALUE = "Invalid boolean value";
    public static final String DOUBLE = "Double";
    public static final String INVALID_DATAVALUE = "Could not read document";

    public static final String JSONAPP = "application/json";
    public static final String XMLAPP = "application/xml";

    public static final String TIMESTAMP_GET_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String TIMESTAMP_POST_FORMAT = "E MMM dd HH:mm:ss Z yyyy";
    public static final String TIMESTAMP_GET_SECOND_FORMAT = "yyyy-MM-dd";
    public static final String TRANSACTION_ID_FORMAT = "'UBOTXN'yyyyMMddHHmmssS";
    public static final String DOB_FORMAT1 = "yyyy-MM-dd";
    public static final String DOB_FORMAT2 = "yyyy-MM";
    public static final String DOB3 = "yyyy";

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String BENEF_STRUCTURE = "getOwnershipStructure";
    public static final String BENEF_LIST = "getListOfBeneficiaries";
    // Sample Json Response
    public static final String DUNS_NUMBER = "duns_nbr";
    public static final String PERSON_ID = "pers_id";
    public static final String NAME_TEXT = "nm_txt";
    public static final String TOWN = "town";
    public static final String COUNTRY = "ctry";
    public static final String ADDRESS = "addr";
    public static final String POSTAL_CODE = "post_cd";
    public static final String BUSINESS_ENTITY_TYPE = "lgl_form";
    public static final String PRIMARY_SIC = "sic_cd";
    public static final String ISO_2_COUNTRY_CODE = "iso_2_char_ctry_code";
    public static final String NAME = "nme";
    public static final String OUT_OF_BUSINESS = "oprg_stat";
    public static final String ADDRESS_LINE_1 = "ln_1_adr";
    public static final String ADDRESS_LINE_2 = "ln_2_adr";
    public static final String ADDRESS_LINE_3 = "ln_3_adr";
    public static final String PRIMARY_TOWN = "post_town";
    public static final String PROVINCE_STATE = "terr";
    public static final String OUT_OF_BUSINESS_TRUE_CODE3 = "9076";
    public static final String OUT_OF_BUSINESS_TRUE_CODE2 = "9077";
    public static final String OUT_OF_BUSINESS_TRUE_CODE1 = "403";
    public static final int BUSINESS_CODE = 118;
    public static final int INDIVIDUAL_CODE = 119;
    public static final int ENTITY_CODE = 0;
    public static final String BUSINESS = "Business";
    public static final String INDIVIDUAL = "Individual";
    public static final String ORGANISATION = "Organisation";
    public static final String ENTITY = "Entity";
    // Organisation Address
    public static final String ORG_ADD1 = "Orgaddr1";
    public static final String ORG_ADD2 = "Orgaddr2";
    public static final String ORG_ADD3 = "Orgaddr3";
    public static final String ORG_POSTAL = "Orgpostal";
    public static final String ORG_COUNTRY = "orgctry";
    public static final String ORG_TERRITORY = "Orgterr";
    public static final String ORG_TOWN = "orgpostTown";
    public static final String ORG_OWNER = "orgOwner";
    // Individual Rest
    public static final String IND_ADD1 = "addr1";
    public static final String IND_ADD2 = "addr2";
    public static final String IND_ADD3 = "addr3";
    public static final String IND_POSTAL = "postalCode";
    public static final String IND_COUNTRY = "countryCode";
    public static final String IND_TERRITORY = "territory";
    public static final String NODE_ID = "nodeID";
    public static final String OUT_OF_BUSINESS_LIST_OP = "isOutOfbus";
    
    public static final String OWNERSHIP_UNAVAILABLE_REASON_CODE = "owrp_unav_reas_cd";
    public static final String COUNTY = "cnty";

    // Requested Values
    public static final String DUNS = "duns";
    public static final String PRODUCT_CODE = "productCode";
    public static final String COUNTRY_CODE = "isoAlpha2CountryCode";
    public static final String OWNERSHIP_TYPE = "ownershipType";
    public static final String OWNERSHIP_PERCENTAGE = "ownershipPercentage";
    public static final String INCLUDE_UNKNOWN_HOLDINGS = "includeUnknownHoldings";
    public static final String EXCLUDE_UNDISCLOSED_HOLDINGS = "excludeUndisclosedHoldings";
    public static final String TRANSACTION_ID = "transactionId";
    public static final String TRANSACTION_TIME_STAMP = "transactionTimestamp";
    public static final String REQUESTED_DUNS_FOR_QUERY = "queryDunsNumber";
     public static final String TRANSACTION_ID_CODE = "UBO_TRANS001";

    //Request Map variables
    public static final String DUNSPADDED = "paddedDunsNumber";
    public static final String DUNSNBR = "dunsNumber";
    // Query
    public static final String OWNERSHIP_STRUCTURE_QUERY = "ownershipStructure";
    public static final String LIST_BENEFICIARIES_QUERY = "listOfBeneficiaries";

    public static final String LIST_BENEFICIARIES_OPANME = "beneficialownershiplist";
    public static final String BENEFICIARIES_STRUCT_OPNAME = "beneficialownershipstructure";

    // Ownership Type
    public static final String BENEFICIARIES_OWNERSHIP = "BENF_OWRP";
    public static final String CONTROLLED_OWNERSHIP = "CTLD_OWRP";
    public static final String DIRECT_OWNERSHIP = "DIRC_OWRP";
    public static final String INDIRECT_OWNERSHIP = "IDIR_OWRP";
    public static final String PRODCODE_LIST = "30637";
    public static final String PRODCODE_STRUCT = "30636";
    public static final String PRODCODE_POINT = "30638";

    // GetBeneficiaryList Response
    public static final String NULL_STRING = "null";
    public static final String BIRTH_DATE = "brth_dt";
    public static final String NATIONALITY_FOR_OP = "naty";
    public static final String SHARE_PERCENTAGE = "owrp_pctg";
    public static final int MEBR_TYPE_CODE_BUSINESS = 118;
    public static final int MEBR_TYPE_CODE_INDIVIDUAL = 119;
    public static final int MEBR_TYPE_CODE_ENTITY = 0;
    public static final String NODE_LABEL = "nodeLabel";
    public static final String DETAILS = "details";
    public static final String NODES = "nodes";
    public static final String FIRMOGRAPHICS = "firmographics";
    public static final String HMO_NODES = "hmoNodes";

    // GetOwnershipstructure Response
    public static final String TARGET_DUNS = "targetDUNS";
    public static final String OWNER_NODE = "ownerNode";
    public static final String RELATIONS = "rels";

    // Regex for request validations
    public static final String REGEX_DUNSNUMBER = "[0-9]{9}";
    public static final String REGEX_COUNTRYCODE = "[A-Z]{2}";
    public static final String REGEX_TRUEFALSE = "true|false";
    public static final String REGEX_TIME_STAMP_PATTERN1 = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z";
    public static final String REGEX_TIME_STAMP_PATTERN2 = "\\d{4}-\\d{2}-\\d{2}";
    public static final String REGEX_DATE_OF_BIRTH1 = "\\d{4}-\\d{2}-\\d{2}";
    public static final String REGEX_DATE_OF_BIRTH2 = "\\d{4}-\\d{2}";
    public static final String REGEX_DATE_OF_BIRTH3 = "\\d{4}";

    // Corporate Linkage
    public static final String TYPE = "type(r)";
    public static final String ULTIMATE = "HAS_ULTIMATE_PARENT";
    public static final String DOMESTIC = "HAS_DOMESTIC_ULTIMATE_PARENT";
    public static final String PARENT = "HAS_PARENT";
    public static final String LINKAGE_DUNS = "O2.duns_nbr";
    public static final String LINKAGE_NAME = "O2.nme";

    // Array Positions
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int NINE = 9;
    public static final int TEN = 10;

    public static final int HUNDREDNBR = 100;

    // Percentages
    public static final Double HUNDRED = 100.0;
    public static final Double ZEROPER = 0.0;

    // Names for logging
    public static final String TOTAL_TIME_IN_API = "TotalTimeSpentInApi";
    public static final String TIME_FOR_CALCULATIONS = "TimeTakenByCalculations";
    public static final String TIME_FOR_GRAPHDB = "TimeTakenByGraphDB";
    public static final String USER = "User";
    public static final String MS = "ms";
    public static final String PIPE = "|";
    public static final String COLON = ":";
    public static final String STRUCTURE_OWNERSHIP = "structureOwnership";
    public static final String LIST_OWNERSHIP = "listOwnership";
    public static final String OPERATION = "operation";
    public static final String STATUS_CODE = "statuscode";
    public static final String STATUS_MESSAGE = "statusMessage";

    public static final String EMPTY_STRING = "{}";
    public static final String COMMA_SEPERATOR = ",";
    public static final String OPEN_SQUARE_BRACKET = "[";
    public static final String CLOSE_SQUARE_BRACKET = "]";
    public static final String EMPTY_ARRAY = "[]";
    public static final String FALSE = "false";
    public static final int BRUTEFORCECOUNT = 500000;
    public static final Double NINENTYNINE = 99.99;
    public static final int NINELAKH = 900000;
    public static final String NONE = "None";
    public static final String EMPTY = "";
    public static final int QUICK_SCAN_COUNT = 15;
    public static final String ZERO_DOUBLE_STRING = "0.0";

    public static final String VERSIONV3 = "Version:V3";

    //Security Header Constants
    public static final String X_FRAME_OPTIONS = "X-Frame-Options";
    public static final String DENY = "DENY";
    public static final String CONTENTSECURITY = "Content-Security-Policy";
    public static final String DEFAULTSRC = "default-src 'none'";
    public static final String XSS ="X-XSS-Protection";
    public static final String BLOCK = "1; mode=block";
    public static final String CONTENTTYPE="X-Content-Type-Options";
    public static final String NOSNIFF = "nosniff";
    public static final String TRNASPORTSECURITY = "Strict-Transport-Security";
    public static final String MAXAGE = "max-age=31536000";
    public static final String CACHECONTROL = "Cache-Control";
    public static final String EXPIRES = "Expires";
    public static final String PRGMA = "Pragma";
    public static final String CACHECONTROLVAL = "no-cache, no-store, max-age=0, must-revalidate, private, s-maxage=0";
    public static final String PRAGMAVAL = "non-cache";
    public static final String EXPIRESVAL = "0";

    public static final String SHARES_HELD_BY = "SHARES_HELD_BY";
    public static final String TRANSTIMEOUT = "Transaction timeout";

    //CALCULATION MAPS
    public static final String BENEFECIALSUM = "beneficialSum";
    public static final String DIRECT = "direct";
    public static final String INDIRECT = "indirect";
    public static final String UNDISCLOSED = "unDisclosed";
    public static final String DEPTH = "depth";
    public static final String  ISBENFECIARY = "isBeneficiary";

    private C() {
    }
}