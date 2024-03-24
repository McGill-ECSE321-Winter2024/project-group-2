package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Time;

// import org.checkerframework.checker.units.qual.Time;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.*;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.*;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.SessionRegistration;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.ClassType;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Instructor;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SessionRegistrationControllerTest {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private SessionRegistrationRepository sessionRegistrationRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ClassTypeRepository classTypeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        sessionRegistrationRepository.deleteAll();
        sessionRepository.deleteAll();
        instructorRepository.deleteAll();
        customerRepository.deleteAll();
        personRepository.deleteAll();
        classTypeRepository.deleteAll();
    }

    public Session createDummySession(){
        ClassType classType = new ClassType("yoga", true);
        classType = classTypeRepository.save(classType);

        Person person = new Person();
        person.setEmail("valid@email.com");
        person.setName("John Doe");
        person.setPassword("password1234");
        person = personRepository.save(person);

        Instructor instructor = new Instructor();
        instructor.setPerson(person);
        instructor = instructorRepository.save(instructor);

        Session session = new Session(60, Time.valueOf("10:00:00"), Time.valueOf("11:00:00"),
                Date.valueOf("2020-03-12"), false, 10, classType, instructor);
        session = sessionRepository.save(session);
        return session;
    }
    public Customer createDummyCustomer(){
        Person person = new Person();
        person.setEmail("valid@email.com");
        person.setName("John Doe");
        person.setPassword("password1234");
        person = personRepository.save(person);

        Customer customer = new Customer(person);
        customer = customerRepository.save(customer);
        return customer;
    }

    @Test
    public void testViewSpecificSessionRegistrationInvalidID() {
        String invalidID = "invalid";
        
        ResponseEntity<String> responseInvalidId = client.getForEntity("/sessionRegistrations/" + invalidID,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseInvalidId.getStatusCode(),
                "Response status should be BAD_REQUEST");

        // check error message
        assertTrue(responseInvalidId.getBody().toString().contains("Bad integer value"));

    }

    @Test
    public void testViewSpecificSessionRegistrationIDNotFound() {
        int notFound = Integer.MAX_VALUE;
        String urlInvalid = String.format("/sessionRegistrations/" + notFound);

        ResponseEntity<String> responseInvalidId = client.getForEntity(urlInvalid, String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseInvalidId.getStatusCode());

        // check error message
        assertTrue(responseInvalidId.getBody().toString().contains("No registration with given ID"));

    }

    @Test
    public void testRegisterForSessionSuccess(){
        Session session = createDummySession();
        Customer customer = createDummyCustomer();

        SessionRegistrationRequestDTO request = new SessionRegistrationRequestDTO();
        request.setCustomerId(customer.getId()+"");
        request.setSessionId(""+session.getId());;

        ResponseEntity<SessionRegistration> response = client.postForEntity("/sessionRegistrations", request, SessionRegistration.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testRegisterForSessionFailure(){
        Session session = createDummySession();
        Customer customer = createDummyCustomer();

        //Fail scenario: customer ID doesnt exist
        SessionRegistrationRequestDTO request = new SessionRegistrationRequestDTO();
        request.setCustomerId(String.valueOf(customer.getId()+1));
        request.setSessionId(""+session.getId());;

        ResponseEntity<String> response = client.postForEntity("/sessionRegistrations", request, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("No customer with given ID"));

        //Fail scenario: customer ID doesnt exist
        SessionRegistrationRequestDTO request2 = new SessionRegistrationRequestDTO();
        request2.setCustomerId(customer.getId()+"");
        int badSessionId = session.getId()+1;
        request2.setSessionId(String.valueOf(badSessionId));;

        ResponseEntity<String> response2 = client.postForEntity("/sessionRegistrations", request2, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
        assertTrue(response2.getBody().contains("No session with given ID"));

        //Fail Scenario: non-integer customer ID
        SessionRegistrationRequestDTO request3 = new SessionRegistrationRequestDTO();
        request3.setCustomerId("invalid");
        request3.setSessionId(""+session.getId());;

        ResponseEntity<String> response3 = client.postForEntity("/sessionRegistrations", request3, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response3.getStatusCode());

        //Fail Scenario: non-integer session ID
        SessionRegistrationRequestDTO request4 = new SessionRegistrationRequestDTO();
        request4.setCustomerId(customer.getId()+"");
        request4.setSessionId("invalid");

        ResponseEntity<String> response4 = client.postForEntity("/sessionRegistrations", request4, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response4.getStatusCode());
    }

    @Test
    public void testCancelRegistrationInvalidId() {
        String invalidID = "invalid";
        String urlInvalid = String.format("/sessionRegistrations/" + invalidID);

        HttpMethod method = HttpMethod.DELETE;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<?> response2 = client.exchange(urlInvalid, method, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        assertTrue(response2.getBody().toString().contains("Bad integer value"));
    }

    @Test
    public void testCancelRegistrationNotFound() {
        String url = "/sessionRegistrations/404";
        HttpMethod method = HttpMethod.DELETE;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<?> response2 = client.exchange(url, method, entity, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode(), "Response status should be NOT_FOUND");
        assertTrue(response2.getBody().toString().contains("given ID"),
                "error message was'" + response2.getBody().toString());

    }

    @Test
    public void testViewRegistrationsByCustomerInvalidID() {
        String invalidID = "invalid";
        String urlInvalid = String.format("/sessionRegistrations/customers/" + invalidID);

        ResponseEntity<String> responseInvalidId = client.getForEntity(urlInvalid, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseInvalidId.getStatusCode(),
                "Response status should be BAD_REQUEST");
        assertTrue(responseInvalidId.getBody().toString().contains("Bad integer value"));

    }
    @Test
    public void testViewSpecificSessionRegistrationSuccess(){
        Session session = createDummySession();
        Customer customer = createDummyCustomer();

        SessionRegistration sessionRegistration = new SessionRegistration(session, customer);
        sessionRegistration = sessionRegistrationRepository.save(sessionRegistration);

        ResponseEntity<SessionRegistration> response = client.getForEntity("/sessionRegistrations/" + sessionRegistration.getId(), SessionRegistration.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionRegistration.getId(), response.getBody().getId());
    }
   
    @Test
    public void testCancelRegistrationValid(){
        Session session = createDummySession();
        Customer customer = createDummyCustomer();

        SessionRegistration sessionRegistration = new SessionRegistration(session, customer);
        sessionRegistration = sessionRegistrationRepository.save(sessionRegistration);

        String url = "/sessionRegistrations/" + sessionRegistration.getId();
        HttpMethod method = HttpMethod.DELETE;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<?> response = client.exchange(url, method, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(!sessionRegistrationRepository.existsById(sessionRegistration.getId()));
    }
    
    @Test
    public void testViewRegistrationByCustomer(){
        Session session = createDummySession();
        Customer customer = createDummyCustomer();

        SessionRegistration sessionRegistration = new SessionRegistration(session, customer);
        sessionRegistration = sessionRegistrationRepository.save(sessionRegistration);

        ResponseEntity<SessionRegistration[]> response = client.getForEntity("/sessionRegistrations/customers/" + customer.getId(), SessionRegistration[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody()[0].getId() == sessionRegistration.getId());
    }
    
    @Test
    public void testViewRegistrationBySession(){
        Session session = createDummySession();
        Customer customer = createDummyCustomer();

        SessionRegistration sessionRegistration = new SessionRegistration(session, customer);
        sessionRegistration = sessionRegistrationRepository.save(sessionRegistration);

        ResponseEntity<SessionRegistration[]> response = client.getForEntity("/sessionRegistrations/sessions/" + session.getId(), SessionRegistration[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody()[0].getId() == sessionRegistration.getId());
    }
    
    @Test
    public void testViewRegistrationsByCustomerNotFound() {
        int notFound = Integer.MAX_VALUE;
        String urlInvalid = String.format("/sessionRegistrations/customers/" + notFound);

        ResponseEntity<String> responseInvalidId = client.getForEntity(urlInvalid, String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseInvalidId.getStatusCode(),
                "Response status should be NOT_FOUND");

        // check error message
        assertTrue(responseInvalidId.getBody().toString().contains("No customer with given ID"));

    }

    @Test
    public void testViewRegistrationsBySessionInvalidID() {
        String invalidID = "invalid";
        String urlInvalid = String.format("/sessionRegistrations/sessions/" + invalidID);

        ResponseEntity<String> responseInvalidId = client.getForEntity(urlInvalid, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseInvalidId.getStatusCode(),
                "Response status should be BAD_REQUEST");

        // check error message
        assertTrue(responseInvalidId.getBody().toString().contains("Bad integer value"));

    }

    @Test
    public void testViewRegistrationsBySessionNotFound() {
        int notFound = Integer.MAX_VALUE;
        String urlInvalid = String.format("/sessionRegistrations/sessions/" + notFound);

        ResponseEntity<String> responseInvalidId = client.getForEntity(urlInvalid, String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseInvalidId.getStatusCode(),
                "Response status should be NOT_FOUND");

        // check error message
        assertTrue(responseInvalidId.getBody().toString().contains("No session with given ID"));

    }
}
