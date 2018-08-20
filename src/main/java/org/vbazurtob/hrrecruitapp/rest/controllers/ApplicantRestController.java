package org.vbazurtob.hrrecruitapp.rest.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vbazurtob.hrrecruitapp.rest.model.ApplicantWithPassword;
import org.vbazurtob.hrrecruitapp.rest.model.ApplicantWithoutPassword;
import org.vbazurtob.hrrecruitapp.rest.model.NewApplicantForm;
import org.vbazurtob.hrrecruitapp.rest.model.repository.ApplicantRepository;
import org.vbazurtob.hrrecruitapp.rest.model.response.RestServiceStatus;
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

    @GetMapping(APPLICANT_STATUS_SERVICE)
    public RestServiceStatus testService(){
        RestServiceStatus rs = new RestServiceStatus( "up", "Service accepting requests" );
        return rs;
    }

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



        ApplicantWithPassword newApplicant = applicantService.createNewApplicantInDB(postApplicant.getUsername(), postApplicant.getPassword(), postApplicant.getEmail());
        return ResponseEntity.created(URI.create( ROOT_API + REST_APPLICANT_CNTROLLER  + "/" + newApplicant.getUsername() ) ).build();
    }



}
