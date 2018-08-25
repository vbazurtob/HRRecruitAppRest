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
import org.vbazurtob.hrrecruitapp.rest.model.service.ApplicantService;

import javax.validation.Valid;
import java.net.URI;

import static org.vbazurtob.hrrecruitapp.rest.conf.ControllerEndpoints.*;

@RestController
@RequestMapping(ROOT_API + REST_APPLICANT_CNTROLLER)
public class ApplicantRestController {

    private Logger logger = LoggerFactory.getLogger(ApplicantRestController.class);

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ApplicantService applicantService;


    @GetMapping(APPLICANT_GET_BY_USERNAME)
    public ResponseEntity<ApplicantWithoutPassword>  retrieveApplicantById(
            @PathVariable("username") String username
    ){

        ApplicantWithPassword applicant = applicantRepository.findOneByUsername(username);
        if( applicant != null){
            return ResponseEntity.ok().body( applicantService.getApplicantInfoWithoutPassword(applicant) );
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping(APPLICANT_NEW)
    public ResponseEntity<?> createApplicant(
            @Valid @RequestBody NewApplicantForm postApplicant

    ){

        if ( applicantService.usernameExists(postApplicant.getUsername()) ){
            throw new RecordAlreadyExists("Username " + postApplicant.getUsername() + " already exists in db. " );
        }


        ApplicantWithPassword newApplicant = applicantService.createNewApplicantInDB(postApplicant.getUsername(), postApplicant.getPassword(), postApplicant.getEmail());
        return ResponseEntity.created(URI.create( ROOT_API + REST_APPLICANT_CNTROLLER  + "/" + newApplicant.getUsername() ) ).build();
    }


    @PutMapping(APPLICANT_UPDATE)
    public ApplicantBaseClass editApplicant(
            @Valid @RequestBody ApplicantWithoutPassword postApplicant,
            @PathVariable("username") String username

    ){

        postApplicant.setUsername(username);
        ApplicantWithPassword applicant = applicantRepository.findOneByUsername(username);
        if ( applicant == null){
            throw new ResourceNotFoundException("Username " + username + " not found in db. " );
        }

        ApplicantWithPassword updatedApplicant = (ApplicantWithPassword) applicantService.updateApplicantProfile(postApplicant);
        return applicantService.getApplicantInfoWithoutPassword(updatedApplicant);

    }

    @DeleteMapping(APPLICANT_DELETE)
    public ResponseEntity<?> deleteApplicant(
            @PathVariable("username") String username
    ){

        ApplicantWithPassword applicant = applicantRepository.findOneByUsername(username);
        if ( applicant == null){
            throw new ResourceNotFoundException("Username " + username + " not found in db. " );
        }

        applicantRepository.delete(applicant);

        return ResponseEntity.ok().build();

    }

    @PutMapping(APPLICANT_UPDATE_PASSWORD)
    public ResponseEntity<?> applicantUpdatePwd(

            @PathVariable("username") String username,
            @Valid @RequestBody ApplicantChangePasswordForm chgPwdForm

    ){
        if(username == null ){
            throw new RuntimeException("An applicant user was not provided!");
        }

        chgPwdForm.setUsernameChangePwdForm(username);

        if ( applicantService.currentApplicantPasswordMatch(chgPwdForm.getUsernameChangePwdForm(),  chgPwdForm.getCurrentPassword()) == false ) {
            throw new RuntimeException("Failed to specify current password! Operation denied!");
        }

        if ( applicantService.updatePassword(chgPwdForm.getUsernameChangePwdForm(), chgPwdForm.getPassword(), chgPwdForm.getPasswordConfirmation()) ) {
            return ResponseEntity.ok().build();
        }else {
            throw new RuntimeException("Failed to update password");
        }

    }

}
