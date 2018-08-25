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
import org.vbazurtob.hrrecruitapp.rest.model.ApplicantWithoutPassword;
import org.vbazurtob.hrrecruitapp.rest.model.NewApplicantForm;

import java.util.HashMap;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class ApplicantRestEndpointsTest {

    private final static String TEST_APPLICANT_USERNAME = "demoTest10";
    private final static int RECORD_ALREADY_EXISTS_CODE =  HttpStatus.CONFLICT.value();
    private final static int NEW_RECORD_CREATED = HttpStatus.CREATED.value();
    private final static String ORIGINAL_APPLICANT_PASSWORD = "A1a2345345++";
    private final static String NEW_APPLICANT_PASSWORD = "A12132d43+++";


    MockMvc mockMvc;

    @Mock
    private ApplicantRestController applicantController;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(applicantController).build();
    }


    @LocalServerPort
    int randomPort;

    @Test
    public void testNewApplicantShouldBeCreatedOrAlreadyExists(){

        HttpEntity<Object> newApplicantForm = getHttpEntity("{" +
                "\"username\": \""+ TEST_APPLICANT_USERNAME +"\", " +
                "\"password\": \""+ORIGINAL_APPLICANT_PASSWORD+"\", " +
                "\"passwordConfirmation\": \""+ORIGINAL_APPLICANT_PASSWORD+"\", " +
                "\"email\": \"test@demo.ec\" " +
                "}");


        ResponseEntity<?> response = template.postForEntity(ControllerEndpoints.ROOT_API
                + ControllerEndpoints.REST_APPLICANT_CNTROLLER
                + ControllerEndpoints.APPLICANT_NEW,
                newApplicantForm,
                NewApplicantForm.class
        );

        System.out.println("Body " + response.getBody());
        System.out.println("Status code " + response.getStatusCodeValue());

        if(response.getStatusCodeValue() == RECORD_ALREADY_EXISTS_CODE){
            Assert.assertEquals(RECORD_ALREADY_EXISTS_CODE, response.getStatusCodeValue());
        } else {
            Assert.assertEquals(NEW_RECORD_CREATED, response.getStatusCodeValue());
        }


    }


    @Test
    public void getApplicantOrDoesNotExists(){
        HashMap<String, String> params = new HashMap<>();
        params.put("username", TEST_APPLICANT_USERNAME);


        ResponseEntity<ApplicantWithoutPassword> response = template.getForEntity(
                ControllerEndpoints.ROOT_API
                        + ControllerEndpoints.REST_APPLICANT_CNTROLLER
                        + ControllerEndpoints.APPLICANT_GET_BY_USERNAME,

                ApplicantWithoutPassword.class,
                params
        );

        if(response.getStatusCode() == HttpStatus.OK){
            Assert.assertEquals(TEST_APPLICANT_USERNAME, response.getBody().getUsername());
        }else{
            Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }


    }

    @Test
    public void testUpdateApplicantOrApplicantDoesNotExists(){

        HttpEntity<Object> updateApplicantInfo = getHttpEntity("{" +

                "\"username\": \""+ TEST_APPLICANT_USERNAME +"\", " +
                "\"address1\": \"MX\"," +
                "\"address2\": \"St 101\"," +
                "\"country\": \"AU\"," +
                "\"email\": \"cv@mm.com\"," +
                "\"lastname\": \"Test22\"," +
                "\"names\": \"Demo test\"," +
                "\"state\": \"aa\"," +
                "\"zipcode\": \"FXZ345\"" +


        "}");


        HashMap<String, String> params = new HashMap<>();
        params.put("username", TEST_APPLICANT_USERNAME);

        template.put(ControllerEndpoints.ROOT_API
                        + ControllerEndpoints.REST_APPLICANT_CNTROLLER
                        + ControllerEndpoints.APPLICANT_UPDATE,
                updateApplicantInfo,
                params
        );

    }


    @Test
    public void testUpdateApplicantPasswordOrApplicantDoesNotExists(){

        HttpEntity<Object> updatePasswordApplicantInfo = getHttpEntity("{" +

                "\"usernameChangePwdForm\": \""+ TEST_APPLICANT_USERNAME +"\", " +
                "\"currentPassword\": \""+ORIGINAL_APPLICANT_PASSWORD+"\"," +
                "\"password\": \""+NEW_APPLICANT_PASSWORD+"\", " +
                "\"passwordConfirmation\": \""+NEW_APPLICANT_PASSWORD+"\"" +


                "}");


        HashMap<String, String> params = new HashMap<>();
        params.put("username", TEST_APPLICANT_USERNAME);

        template.put(ControllerEndpoints.ROOT_API
                        + ControllerEndpoints.REST_APPLICANT_CNTROLLER
                        + ControllerEndpoints.APPLICANT_UPDATE_PASSWORD,
                updatePasswordApplicantInfo,
                params
        );

    }


    @Test
    public void testDeleteApplicantOrApplicantDoesNotExists(){

        HashMap<String, String> params = new HashMap<>();
        params.put("username", TEST_APPLICANT_USERNAME);

        template.delete(ControllerEndpoints.ROOT_API
                        + ControllerEndpoints.REST_APPLICANT_CNTROLLER
                        + ControllerEndpoints.APPLICANT_DELETE,
                params
        );

    }





    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }


}
