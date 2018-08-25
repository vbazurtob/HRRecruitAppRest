package org.vbazurtob.hrrecruitapp.rest.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.vbazurtob.hrrecruitapp.rest.conf.ControllerEndpoints;
import org.vbazurtob.hrrecruitapp.rest.model.ApplicantSkill;

import java.util.HashMap;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class ApplicantSkillRestEndpointsTest {

    private final static String TEST_APPLICANT_USERNAME = "demoTest10";
    private final static int RECORD_ALREADY_EXISTS_CODE =  HttpStatus.CONFLICT.value();
    private final static int NEW_RECORD_CREATED = HttpStatus.CREATED.value();


    MockMvc mockMvc;

    @Mock
    private ApplicantSkillsRestController applicantSkillsController;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(applicantSkillsController).build();
    }


    @LocalServerPort
    int randomPort;

    @Test
    public void testGetListSkillsForApplicant(){

        HashMap<String, String> params = new HashMap<>();
        params.put("username", TEST_APPLICANT_USERNAME);


        ResponseEntity<?> response = template.getForEntity(ControllerEndpoints.ROOT_API
                + ControllerEndpoints.REST_APPLICANT_SKILLS_CNTROLLER
                + ControllerEndpoints.APPLICANT_SKILLS_GET,
                ApplicantSkill[].class,
                params

        );


        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());


    }


}
