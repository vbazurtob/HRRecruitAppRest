package org.vbazurtob.hrrecruitapp.rest.model.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.vbazurtob.hrrecruitapp.rest.model.ApplicantSkill;
import org.vbazurtob.hrrecruitapp.rest.model.repository.ApplicantRepository;
import org.vbazurtob.hrrecruitapp.rest.model.repository.ApplicantSkillRepository;
import org.vbazurtob.hrrecruitapp.rest.model.response.ApplicantSkillJsonResponse;

@Service
public class ApplicantSkillService {
	
	@Autowired
	private ApplicantSkillRepository appSkillRepository;
	
	@Autowired
	private ApplicantRepository applicantRepository;
	
	
	public ApplicantSkillService() {
	
	}
	
	public ApplicantSkill saveSkill( ApplicantSkill applicantSkillForm, String applicantUsername ) {
			
		applicantSkillForm.setApplicant(applicantRepository.findOneByUsername( applicantUsername ));
		return appSkillRepository.save(applicantSkillForm);
		
	}

	public ApplicantSkill update( ApplicantSkill applicantSkillDB, ApplicantSkill applicantSkillFormData ){
		applicantSkillDB.setName(applicantSkillFormData.getName());
		applicantSkillDB.setProficiency(applicantSkillFormData.getProficiency());
		ApplicantSkill updatedApplicantSkill =  appSkillRepository.save(applicantSkillDB);
		return updatedApplicantSkill;
	}
	
	public boolean recordExists(
			String username ,
			String name,
			Integer proficiency
			
			) {
		
		long count =  appSkillRepository.countByApplicantUsernameAndNameAndProficiency(
				username, name, proficiency);
		return  (count > 0) ;
	}
	
	public Page<ApplicantSkill> getPaginatedRecords(String username, Optional<Integer> page, int recordsPerPage) {
		
		PageRequest pageReqObj = PageRequest.of(page.orElse(Integer.valueOf(0)) , recordsPerPage, Direction.DESC, "proficiency", "name" ); 
		Page<ApplicantSkill> skillPageObj = appSkillRepository.findByApplicantUsername(username, pageReqObj);
		
		return skillPageObj;
	}

	public List<ApplicantSkillJsonResponse> getPaginatedListForJson(String username, Optional<Integer> page, int recordsPerPage) {

		PageRequest pageReqObj = PageRequest.of(page.orElse(Integer.valueOf(0)) , recordsPerPage, Direction.DESC, "proficiency", "name" );
		Page<ApplicantSkill> skillPageObj = appSkillRepository.findByApplicantUsername(username, pageReqObj);

		List<ApplicantSkillJsonResponse> tmpList = skillPageObj.getContent().stream()
				.map( obj ->
					this.convertApplicantSkillJpaToJsonResponse(obj)
				 )
				.collect(Collectors.toList());

		return tmpList;
	}

	public ApplicantSkillJsonResponse convertApplicantSkillJpaToJsonResponse(ApplicantSkill applicantSkill ){
		ApplicantSkillJsonResponse jsonResp = new ApplicantSkillJsonResponse(
			applicantSkill.getId(),
				applicantSkill.getName(),
				applicantSkill.getProficiency()
		);
		return jsonResp;
	}
	
	public long[] getPaginationNumbers(Page<ApplicantSkill> skillPageObj) {
		int previousPageNum = skillPageObj.isFirst() ? 0 : skillPageObj.previousPageable().getPageNumber() ;
		int nextPageNum = skillPageObj.isLast() ? skillPageObj.getTotalPages() - 1 : skillPageObj.nextPageable().getPageNumber() ;
		
		if(nextPageNum < 0) {
			nextPageNum = 0;
		}
		
		
		return  new  long[]{ previousPageNum, nextPageNum };
	}
	
}
