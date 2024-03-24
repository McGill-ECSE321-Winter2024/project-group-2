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

    @Test
    public void testViewSpecificSessionRegistrationInvalidID() {

        String invalidID = "invalid";

        String urlInvalid = String.format("/sessionRegistrations/" + invalidID);

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
    public void testViewSpecificSessionRegistrationValid() {

    Session session = createSession();

    session = sessionRepository.save(session);

    Person person = new Person("name", "nameemail@mcgill.ca", "password");
    person = personRepository.save(person);

    Customer customer = new Customer(person);
    customer = customerRepository.save(customer);

    SessionRegistration sessionRegistration = new SessionRegistration(session,
    customer);
    sessionRegistration = sessionRegistrationRepository.save(sessionRegistration);

    String url = String.format("/sessionRegistrations/" +
    sessionRegistration.getId());

    ResponseEntity<SessionRegistration> response = client.getForEntity(url,
    SessionRegistration.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(sessionRegistration.getId(), response.getBody().getId());
    assertEquals(sessionRegistration.getCustomer().getId(), response.getBody().getCustomer().getId());
    assertEquals(sessionRegistration.getSession().getId(), response.getBody().getSession().getId());

    }

    // @Test
    // public void testRegisterForSessionInvalidSessionID() {
    // ResponseEntity<String> response2 =
    // client.postForEntity("/sessionRegistrations", "NOT_AN_INT", String.class);

    // assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
    // assertEquals("Bad integer value", response2.getBody());
    // }

    // @Test
    // public void testRegisterForSessionValid() {

    // // customer DTO
    // CustomerDTO customerDTO = new CustomerDTO(new ArrayList<>());
    // customerDTO.setId(1);

    // Session session = createSession();
    // sessionRepository.save(session);

    // SessionDTO sessionDTO = new SessionDTO(session);

    // // register
    // SessionRegistrationDTO sessionRegistration = new SessionRegistrationDTO(1,
    // customerDTO, sessionDTO);

    // int sessionId = sessionDTO.getId();
    // int customerId = customerDTO.getId();

    // // URL for registration endpoint
    // String url = String.format("/sessionRegistrations/session/%d/customer/%d",
    // sessionId, customerId);

    // // PUT request
    // ResponseEntity<SessionRegistrationDTO> response = client.exchange(
    // url, HttpMethod.PUT, null, SessionRegistrationDTO.class);

    // // response status is OK
    // assertEquals(HttpStatus.OK, response.getStatusCode());

    // // response body is not null and contains the expected data
    // assertNotNull(response.getBody());

    // SessionRegistration registration =
    // sessionRegistrationRepository.findById(response.getBody().getId());

    // assertNotNull(registration);

    // // Verify the registration details match what was requested
    // assertEquals(sessionId, registration.getSession().getId());
    // assertEquals(customerId, registration.getCustomer().getId());
    // }

    // @Test
    // public void testRegisterForSessionInvalid() {
    // // URL for session registration
    // String baseUrl = "sessionRegistrations/session/%d/customer/%d";

    // // maybe do one for each

    // // Testing with null SessionDTO and CustomerDTO objects (?)
    // HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.APPLICATION_JSON);
    // HttpEntity<String> entity = new HttpEntity<String>(null, headers);
    // ResponseEntity<?> responseNull = client.exchange(
    // String.format(baseUrl, "null", "null"), HttpMethod.PUT, null, String.class);
    // assertEquals(HttpStatus.BAD_REQUEST, responseNull.getStatusCode(),
    // "Session or Customer DTO objects cannot be null.");

    // // sessionDTO
    // Session session = createSession();
    // SessionDTO sessionDTO = new SessionDTO(session); // customer DTO
    // CustomerDTO customerDTO = new CustomerDTO(new ArrayList<>());
    // customerDTO.setId(1);

    // // invalid session ID (non-numeric)
    // ResponseEntity<?> responseInvalidSessionID = client.exchange(
    // String.format(baseUrl, 1, "invalid"), HttpMethod.PUT, null, String.class);
    // assertEquals(HttpStatus.BAD_REQUEST,
    // responseInvalidSessionID.getStatusCode(),
    // "Invalid session ID format.");
    // // check error message
    // assertTrue(responseInvalidSessionID.getBody().toString().contains("Bad
    // integer value"));

    // // invalid customer ID (non-numeric)
    // ResponseEntity<?> responseInvalidCusomerID = client.exchange(
    // String.format(baseUrl, "invalid", 1), HttpMethod.PUT, null, String.class);
    // assertEquals(HttpStatus.BAD_REQUEST,
    // responseInvalidCusomerID.getStatusCode(),
    // "Invalid customer ID format.");
    // // check error message
    // assertTrue(responseInvalidCusomerID.getBody().toString().contains("Bad
    // integer value"));

    // // Testing for duplicate session registration
    // client.exchange(String.format(baseUrl, "1", "1"), HttpMethod.PUT, null,
    // SessionRegistrationDTO.class);

    // // attempt to register the same session and customer again
    // ResponseEntity<?> responseDuplicate = client.exchange(
    // String.format(baseUrl, "1", "1"), HttpMethod.PUT, null, String.class);
    // assertEquals(HttpStatus.CONFLICT, responseDuplicate.getStatusCode(),
    // "Duplicate session registration should be prevented.");
    // // check error message
    // assertTrue(responseInvalidCusomerID.getBody().toString().contains("with given
    // ID"));

    // }

    // @Test
    // public void testViewSpecificSessionRegistrationValid() {

    // CustomerDTO customerDTO = new CustomerDTO(new ArrayList<>()); // Setup
    // customer details as needed
    // ResponseEntity<CustomerDTO> createdCustomerResponse =
    // client.postForEntity("/customers", customerDTO,
    // CustomerDTO.class);
    // int customerId = createdCustomerResponse.getBody().getId();

    // Session session = createSession();
    // sessionRepository.save(session);
    // SessionDTO sessionDTO = new SessionDTO(session);

    // ResponseEntity<SessionDTO> createdSessionResponse =
    // client.postForEntity("/sessions", sessionDTO,
    // SessionDTO.class);
    // int sessionId = createdSessionResponse.getBody().getId();

    // // register the customer to the session
    // SessionRegistrationDTO sessionRegistrationDTO = new SessionRegistrationDTO(1,
    // customerDTO, sessionDTO);

    // String registrationUrl =
    // String.format("/sessionRegistrations/session/%d/customer/%d", sessionId,
    // customerId);
    // ResponseEntity<SessionRegistrationDTO> registrationResponse =
    // client.postForEntity(registrationUrl,
    // null,
    // SessionRegistrationDTO.class);
    // int registrationId = registrationResponse.getBody().getId();

    // // test action
    // String getUrl = String.format("/session/%d", registrationId);
    // ResponseEntity<SessionRegistrationDTO> response = client.getForEntity(getUrl,
    // SessionRegistrationDTO.class);
    // int registrationRegistrationID = response.getBody().getId();

    // assertEquals(HttpStatus.OK, response.getStatusCode());
    // assertNotNull(response.getBody());

    // SessionRegistration registration =
    // sessionRegistrationRepository.findById(response.getBody().getId());

    // assertNotNull(registration);

    // // verify the registration details match what was requested
    // assertEquals(sessionId, registration.getSession().getId());
    // assertEquals(customerId, registration.getCustomer().getId());
    // }

    // @Test
    // public void testCancelRegistrationValid() {

    // // setup
    // Session session = createSession();
    // Customer customer = createCustomer();

    // sessionRepository.save(session);
    // customerRepository.save(customer);

    // SessionRegistration sessionRegistration = new SessionRegistration(session,
    // customer);
    // sessionRegistrationRepository.save(sessionRegistration);

    // SessionDTO sessionDTO = new SessionDTO(session);

    // CustomerDTO customerDTO = new CustomerDTO();
    // customerDTO.setId(1);

    // SessionRegistration registration = new SessionRegistration(session,
    // customer);
    // sessionRegistrationRepository.save(registration);

    // SessionRegistrationDTO sessionRegistrationDTO = registerSession(customerDTO,
    // sessionDTO);

    // int registrationId = sessionRegistrationDTO.getId();

    // // test action: Cancel the registration
    // ResponseEntity<?> response = client.exchange(
    // String.format("sessionRegistrations/cancel/%d", registrationId),
    // HttpMethod.DELETE,
    // null, // no request body
    // Void.class); // no response body

    // // assertions
    // assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should
    // be OK.");

    // boolean registrationExists =
    // sessionRegistrationRepository.existsById(registration.getId());
    // assertFalse(registrationExists, "Registration should not exist after
    // cancellation.");

    // }

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

    // @Test
    // public void testCancelRegistrationInvalid() {
    // // not found registration ID
    // int notFoundRegistrationId = Integer.MAX_VALUE;

    // // test not found
    // ResponseEntity<String> responseNotFound = client.exchange(
    // String.format("sessionRegistrations/cancel/" + notFoundRegistrationId),
    // HttpMethod.DELETE,
    // new HttpEntity<>(null), // No request body needed for this operation
    // String.class); // Assuming the error response might contain a message

    // assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode(),
    // "Response status should be NOT_FOUND for an invalid registration ID.");

    // assertTrue(responseNotFound.getBody().contains("No registration with given
    // ID"));

    // // not found registration ID
    // String notValid = "Invalid";

    // // test not found
    // ResponseEntity<String> responseNotValid = client.exchange(
    // String.format("/cancel/" + notValid),
    // HttpMethod.DELETE,
    // new HttpEntity<>(null), // No request body needed for this operation
    // String.class); // Assuming the error response might contain a message

    // assertEquals(HttpStatus.BAD_REQUEST, responseNotValid.getStatusCode(),
    // "Response status should be NOT_FOUND for an invalid registration ID.");

    // assertTrue(responseNotFound.getBody().contains("Bad integer value for id"));

    // }

    // @Test
    // public void testViewRegistrationsByCustomerValid() {
    // CustomerDTO customerDTO = createCustomerDTO();

    // Session session = createSession();
    // sessionRepository.save(session);
    // SessionDTO sessionDTO = new SessionDTO(session);

    // registerSession(customerDTO, sessionDTO);

    // ResponseEntity<SessionRegistrationDTO[]> response = client.getForEntity(
    // "/sessionRegistrations/customer/" + customerDTO.getId(),
    // SessionRegistrationDTO[].class);

    // assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should
    // be OK.");

    // assertNotNull(response.getBody(), "Response body should not be null.");
    // assertTrue(response.getBody().length > 0, "Customer should have at least one
    // registration.");
    // }

    @Test
    public void testViewRegistrationsByCustomerInvalidID() {

        String invalidID = "invalid";

        String urlInvalid = String.format("/sessionRegistrations/customers/" + invalidID);

        ResponseEntity<String> responseInvalidId = client.getForEntity(urlInvalid, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseInvalidId.getStatusCode(),
                "Response status should be BAD_REQUEST");

        // check error message
        assertTrue(responseInvalidId.getBody().toString().contains("Bad integer value"));

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

    // @Test
    // public void testViewRegistrationsByCustomerNotFound() {

    // int notFound = Integer.MAX_VALUE;

    // String urlInvalid = String.format("/sessionRegistrations/" + notFound);

    // ResponseEntity<String> responseInvalidId = client.getForEntity(urlInvalid,
    // String.class);

    // assertEquals(HttpStatus.NOT_FOUND, responseInvalidId.getStatusCode());

    // // check error message
    // assertTrue(responseInvalidId.getBody().toString().contains("No registration
    // with given ID"));

    // }

    // @Test
    // public void testViewRegistrationsByCustomerInvalid() {
    // // not found
    // int notFoundSessionId = Integer.MAX_VALUE;

    // ResponseEntity<String> responseNotFound = client.getForEntity(
    // "/sessionRegistrations/customer/" + notFoundSessionId,
    // String.class);

    // assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode(),
    // "Response status should be NOT_FOUND for a non-existing session.");

    // assertTrue(responseNotFound.getBody().contains("No customer with given ID"));

    // // invalid
    // String invalidID = "Invalid";

    // ResponseEntity<String> responseInvalid = client.getForEntity(
    // "/sessionRegistrations/customer/" + invalidID,
    // String.class);

    // assertEquals(HttpStatus.BAD_REQUEST, responseNotFound.getStatusCode(),
    // "Response status should be NOT_FOUND for a non-existing session.");

    // assertTrue(responseInvalid.getBody().contains("Bad integer value for
    // customerId"));

    // }

    // @Test
    // public void testViewRegistrationsBySessionValid() {
    // Session session = createSession();
    // sessionRepository.save(session);

    // SessionDTO sessionDTO = new SessionDTO(session);

    // CustomerDTO customerDTO = createCustomerDTO();

    // SessionRegistrationDTO sessionRegistrationDTO = registerSession(customerDTO,
    // sessionDTO);

    // ResponseEntity<SessionRegistrationDTO[]> response = client.getForEntity(
    // "/sessionRegistration/session/" + sessionDTO.getId(),
    // SessionRegistrationDTO[].class);

    // assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should
    // be OK.");

    // assertNotNull(response.getBody(), "Response body should not be null.");
    // }

    // @Test
    // public void testViewRegistrationsBySessionInvalid() {

    // // not found
    // int notFoundSessionId = Integer.MAX_VALUE;

    // ResponseEntity<String> responseNotFound = client.getForEntity(
    // "/sessionRegistration/session/" + notFoundSessionId,
    // String.class);

    // assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode(),
    // "Response status should be NOT_FOUND for a non-existing session.");

    // assertTrue(responseNotFound.getBody().contains("No session with given ID"));

    // // invalid
    // String invalidID = "invalid";

    // ResponseEntity<String> responseInvalid = client.getForEntity(
    // "/sessionRegistration/session/" + invalidID,
    // String.class);

    // assertEquals(HttpStatus.BAD_REQUEST, responseNotFound.getStatusCode(),
    // "Response status should be NOT_FOUND for a non-existing session.");

    // assertTrue(responseInvalid.getBody().contains("Bad integer value for
    // sessionId"));

    // }

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

    private Session createSession() {
        Person person = new Person("John Doe", "john.doe@mcgill.ca", "password");
        person = personRepository.save(person);

        Instructor instructor = new Instructor(person);
        instructor = instructorRepository.save(instructor);

        ClassType yoga = new ClassType("yoga", true);
        yoga = classTypeRepository.save(yoga);

        Session session = new Session(60, Time.valueOf("10:00:00"), Time.valueOf("11:00:00"),
                Date.valueOf("2020-03-12"), false, 10, yoga, instructor);

        return session;
    }

    // private Customer createCustomer() {
    // Person person = new Person("Jane Doe", "jane.doe@mcgill.ca", "password");
    // personRepository.save(person);

    // Customer customer = new Customer(person);

    // return customer;
    // }

    // private SessionRegistrationDTO registerSession(CustomerDTO customerDTO,
    // SessionDTO sessionDTO) {
    // int customerId = customerDTO.getId();
    // int sessionId = sessionDTO.getId();

    // String url = String.format("/sessionRegistration/session/%d/customer/%d",
    // sessionId, customerId);

    // ResponseEntity<SessionRegistrationDTO> response = client.exchange(
    // url,
    // HttpMethod.PUT,
    // null,
    // SessionRegistrationDTO.class);

    // // check the response status and body as needed
    // if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null)
    // {
    // return response.getBody(); // return sessionRegistrationDTO?
    // } else {

    // throw new RuntimeException("Failed to register session: " +
    // response.getStatusCode());
    // }
    // }

    // private CustomerDTO createCustomerDTO() {

    // CustomerDTO requestCustomerDTO = new CustomerDTO();
    // requestCustomerDTO.setId(0);

    // ResponseEntity<CustomerDTO> response = client.postForEntity(
    // "/customers",
    // new HttpEntity<>(requestCustomerDTO),
    // CustomerDTO.class);

    // if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null)
    // {
    // return response.getBody(); // return CustomerDTO?
    // } else {
    // throw new RuntimeException("Failed to create customer: " +
    // response.getStatusCode());
    // }
    // }

}
