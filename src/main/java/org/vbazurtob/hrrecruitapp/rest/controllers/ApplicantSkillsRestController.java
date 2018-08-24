package org.vbazurtob.hrrecruitapp.rest.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vbazurtob.hrrecruitapp.rest.lib.common.RecordAlreadyExists;
import org.vbazurtob.hrrecruitapp.rest.model.*;
import org.vbazurtob.hrrecruitapp.rest.model.repository.ApplicantRepository;
import org.vbazurtob.hrrecruitapp.rest.model.repository.ApplicantSkillRepository;
import org.vbazurtob.hrrecruitapp.rest.model.response.ApplicantSkillJsonResponse;
import org.vbazurtob.hrrecruitapp.rest.model.service.ApplicantService;
import org.vbazurtob.hrrecruitapp.rest.model.service.ApplicantSkillService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.*;
import static org.vbazurtob.hrrecruitapp.rest.conf.ControllerEndpoints.*;

@RestController
@RequestMapping(ROOT_API + REST_APPLICANT_SKILLS_CNTROLLER)
public class ApplicantSkillsRestController {

    private Logger logger = LoggerFactory.getLogger(ApplicantSkillsRestController.class);

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private ApplicantSkillRepository applicantSkillRepository;

    @Autowired
    private ApplicantSkillService applicantSkillService;


    @GetMapping(value  = APPLICANT_SKILLS_GET , produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> retrieveApplicantById(
            @PathVariable("username") String username,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("rows") Optional<Integer> rowsPerPage

    ){

        // To avoid error 500 trying to do lazy load before sending back. Set references to Null or remap to a class without jpa lazy load references
        List<ApplicantSkillJsonResponse> list = applicantSkillService.getPaginatedListForJson( username, page , rowsPerPage.orElse( DEFAULT_MAX_ROWS_PER_PAGE ) );
        return   ResponseEntity.ok().body(list)  ;

    }




    @PostMapping(APPLICANT_SKILLS_NEW)
    public ResponseEntity<?> createApplicantSkill(
            @PathVariable("username") String username,
            @Valid @RequestBody ApplicantSkill applicantSkill
    ){

        if ( applicantSkillService.recordExists(username, applicantSkill.getName(), applicantSkill.getProficiency() ) ){
            throw new RecordAlreadyExists("Skill " + applicantSkill.getName() + " already exists in db. " );
        }

        ApplicantSkill newApplicantSkill = applicantSkillService.saveSkill(applicantSkill, username);
        return ResponseEntity.created(URI.create( ROOT_API + REST_APPLICANT_SKILLS_CNTROLLER  + "/" + newApplicantSkill.getId() ) ).build();
    }

    @PutMapping(APPLICANT_SKILLS_UPDATE)
    public ResponseEntity<?> editApplicantSkill(
            @PathVariable("id") Long id,
            @Valid @RequestBody ApplicantSkill applicantSkillForm


    ){

        ApplicantSkill applicantSkill = applicantSkillRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Skill with id " + id + " not found in db. " )
        );

        applicantSkillService.update(applicantSkill, applicantSkillForm);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping(APPLICANT_SKILLS_DELETE)
    public ResponseEntity<?> deleteApplicant(
            @PathVariable("id") Long id
    ){


        ApplicantSkill applicantSkill = applicantSkillRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Skill with id " + id + " not found in db. " )
        );

        applicantSkillRepository.delete(applicantSkill);
        return ResponseEntity.ok().build();
    }

}
