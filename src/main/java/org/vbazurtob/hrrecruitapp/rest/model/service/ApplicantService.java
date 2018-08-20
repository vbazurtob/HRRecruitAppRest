package org.vbazurtob.hrrecruitapp.rest.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.vbazurtob.hrrecruitapp.rest.model.ApplicantWithPassword;
import org.vbazurtob.hrrecruitapp.rest.model.ApplicantBaseClass;
import org.vbazurtob.hrrecruitapp.rest.model.ApplicantWithoutPassword;
import org.vbazurtob.hrrecruitapp.rest.model.repository.ApplicantRepository;

@Service
public class ApplicantService {

	@Autowired
	private ApplicantRepository applicantRepository;

	@Autowired
	protected BCryptPasswordEncoder bcryptEncoder;
	

	public void updateApplicantProfile(ApplicantBaseClass applicantFormData) {
		
		ApplicantWithPassword applicantDB = applicantRepository.findOneByUsername(applicantFormData.getUsername());
		
		//Update only selected fields. Password is not updated
		
		applicantDB.setNames(applicantFormData.getNames());
		applicantDB.setLastname(applicantFormData.getLastname());
		applicantDB.setAddress1(applicantFormData.getAddress1());
		applicantDB.setAddress2(applicantFormData.getAddress2());
		
		applicantDB.setCountry(applicantFormData.getCountry());
		applicantDB.setState(applicantFormData.getState());
		applicantDB.setZipcode(applicantFormData.getZipcode());
		applicantDB.setEmail(applicantFormData.getEmail());
		
		applicantRepository.save(applicantDB);
		
	}
	
	public ApplicantWithPassword createNewApplicantInDB( String username, String password, String email ) {
		
		ApplicantWithPassword newApplicantToSave = new ApplicantWithPassword();
		newApplicantToSave.setUsername( username );
		newApplicantToSave.setPassword(bcryptEncoder.encode(password) );
		newApplicantToSave.setNames(username);
		newApplicantToSave.setLastname(".");
		newApplicantToSave.setAddress1(".");
		newApplicantToSave.setAddress2(".");
		newApplicantToSave.setCountry(".");
		newApplicantToSave.setState(".");
		newApplicantToSave.setEmail(email);
		newApplicantToSave.setZipcode(".");
		
		return applicantRepository.save(newApplicantToSave);
	}
	
	
	public boolean updatePassword(String applicantUsername, String password, String passwordConfirmation) {
		
		if(!password.equals(passwordConfirmation) || password.trim().isEmpty() || passwordConfirmation.trim().isEmpty()) {
			return false;
		}else {

			//Encrypt the password
			ApplicantWithPassword applicantDB = applicantRepository.findOneByUsername(applicantUsername);
			
			applicantDB.setPassword(bcryptEncoder.encode(password) );
			applicantRepository.save(applicantDB);
			
			return true;
		}
		
	}
	
	public boolean currentApplicantPasswordMatch(String username, String rawPassword ) {
		
		ApplicantWithPassword applicantWithPassword = applicantRepository.findOneByUsername(username);
		return bcryptEncoder.matches(rawPassword, applicantWithPassword.getPassword());
	}

	public ApplicantWithoutPassword getApplicantInfoWithoutPassword(ApplicantWithPassword applicant){
		ApplicantWithoutPassword applicantNoPwd = new ApplicantWithoutPassword();
		applicantNoPwd.setAddress1(applicant.getAddress1());
		applicantNoPwd.setAddress2(applicant.getAddress2());
		applicantNoPwd.setCountry(applicant.getCountry());
		applicantNoPwd.setEmail(applicant.getEmail());
		applicantNoPwd.setLastname(applicant.getLastname());
		applicantNoPwd.setNames(applicant.getNames());
		applicantNoPwd.setState(applicant.getState());
		applicantNoPwd.setZipcode(applicant.getZipcode());
		applicantNoPwd.setUsername(applicant.getUsername());

		return applicantNoPwd;
	}
	
	public boolean usernameExists(String username) {
		return applicantRepository.countByUsername(username) > 0;
	}
	

}
