package org.vbazurtob.hrrecruitapp.rest.conf;

public interface ControllerEndpoints {


	final static int DEFAULT_MAX_ROWS_PER_PAGE=10;


	// Controllers
	final static String REST_APPLICANT_CNTROLLER = "/applicant";
	final static String REST_APPLICANT_SKILLS_CNTROLLER = "/skills";

	//Applicant endpoints
	final static String APPLICANT_STATUS_SERVICE = "/status-svc";
	final static String APPLICANT_GET_BY_USERNAME = "/{username}";
	final static String APPLICANT_NEW = "/new";
	final static String APPLICANT_UPDATE =  "/{username}";
	final static String APPLICANT_DELETE =  "/{username}";
	final static String APPLICANT_UPDATE_PASSWORD =  "/{username}/update-pwd";

	//Applicant Skills endpoints
	final static String APPLICANT_SKILLS_GET =  "/{username}";
	final static String APPLICANT_SKILLS_NEW =  "/{username}/new";
	final static String APPLICANT_SKILLS_UPDATE =  "/{id}";
	final static String APPLICANT_SKILLS_DELETE =  "/{id}";


	// Pages in public
	final static  String ROOT_SERVER = "/";
	final static  String ROOT_API = ROOT_SERVER + "api";
	final static  String INDEX_PAGE = "/index";

}
