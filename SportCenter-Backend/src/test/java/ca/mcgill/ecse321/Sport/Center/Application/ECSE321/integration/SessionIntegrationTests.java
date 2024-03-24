package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.integration;

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
public class SessionRegistrationIntegrationTest {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private SessionRegistrationRepository sessionRegistrationRepository;
    @Autowired
    private static SessionRepository sessionRepository;
    @Autowired
    private static ClassTypeRepository classTypeRepository;
    @Autowired
    private static CustomerRepository customerRepository;
    @Autowired
    private static PersonRepository personRepository;
    @Autowired
    private static InstructorRepository instructorRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        sessionRegistrationRepository.deleteAll();
        sessionRepository.deleteAll();
        instructorRepository.deleteAll();
        personRepository.deleteAll();
        classTypeRepository.deleteAll();
    }

    @Test
    public void testViewSessions() {
        ResponseEntity<Session[]> response = client.getForEntity("/sessions", Session[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testRegisterForSessionValid() {

        // customer DTO (can it be empty?)
        CustomerDTO customerDTO = new CustomerDTO(new ArrayList<>());
        customerDTO.setId(1);

        Session session = createSession();
        sessionRepository.save(session);
        SessionDTO sessionDTO = new SessionDTO(session);
        // register
        SessionRegistrationDTO sessionRegistration = new SessionRegistrationDTO(1, customerDTO, sessionDTO);

        int sessionId = sessionDTO.getId();
        int customerId = customerDTO.getId();

        // URL for registration endpoint
        String url = String.format("/register/session/%d/customer/%d", sessionId, customerId);

        // PUT request
        ResponseEntity<SessionRegistrationDTO> response = client.exchange(
                url, HttpMethod.PUT, null, SessionRegistrationDTO.class);

        // response status is OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // response body is not null and contains the expected data
        assertNotNull(response.getBody());

        SessionRegistration registration = sessionRegistrationRepository.findById(response.getBody().getId());

        assertNotNull(registration);

        // Verify the registration details match what was requested
        assertEquals(sessionId, registration.getSession().getId());
        assertEquals(customerId, registration.getCustomer().getId());
    }

    @Test
    public void testRegisterForSessionInvalid() {
        // URL for session registration
        String baseUrl = "/register/session/%d/customer/%d";

        // maybe do one for each

        // Testing with null SessionDTO and CustomerDTO objects (?)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<?> responseNull = client.exchange(
                String.format(baseUrl, "null", "null"), HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseNull.getStatusCode(),
                "Session or Customer DTO objects cannot be null.");

        // sessionDTO
        Session session = createSession();
        SessionDTO sessionDTO = new SessionDTO(session); // customer DTO
        CustomerDTO customerDTO = new CustomerDTO(new ArrayList<>());
        customerDTO.setId(1);

        // invalid session ID (non-numeric)
        ResponseEntity<?> responseInvalidSessionID = client.exchange(
                String.format(baseUrl, 1, "invalid"), HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseInvalidSessionID.getStatusCode(),
                "Invalid session ID format.");
        // check error message
        assertTrue(responseInvalidSessionID.getBody().toString().contains("Bad integer value"));

        // invalid customer ID (non-numeric)
        ResponseEntity<?> responseInvalidCusomerID = client.exchange(
                String.format(baseUrl, "invalid", 1), HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseInvalidCusomerID.getStatusCode(),
                "Invalid customer ID format.");
        // check error message
        assertTrue(responseInvalidCusomerID.getBody().toString().contains("Bad integer value"));

        // Testing for duplicate session registration
        client.exchange(String.format(baseUrl, "1", "1"), HttpMethod.PUT, null, SessionRegistrationDTO.class);

        // attempt to register the same session and customer again
        ResponseEntity<?> responseDuplicate = client.exchange(
                String.format(baseUrl, "1", "1"), HttpMethod.PUT, null, String.class);
        assertEquals(HttpStatus.CONFLICT, responseDuplicate.getStatusCode(),
                "Duplicate session registration should be prevented.");
        // check error message
        assertTrue(responseInvalidCusomerID.getBody().toString().contains("with given ID"));

    }

    @Test
    public void testViewSpecificSessionRegistrationValid() {

        CustomerDTO customerDTO = new CustomerDTO(new ArrayList<>()); // Setup customer details as needed
        ResponseEntity<CustomerDTO> createdCustomerResponse = client.postForEntity("/customers", customerDTO,
                CustomerDTO.class);
        int customerId = createdCustomerResponse.getBody().getId();

        Session session = createSession();
        sessionRepository.save(session);
        SessionDTO sessionDTO = new SessionDTO(session);

        ResponseEntity<SessionDTO> createdSessionResponse = client.postForEntity("/sessions", sessionDTO,
                SessionDTO.class);
        int sessionId = createdSessionResponse.getBody().getId();

        // register the customer to the session
        SessionRegistrationDTO sessionRegistrationDTO = new SessionRegistrationDTO(1, customerDTO, sessionDTO);

        String registrationUrl = String.format("/register/session/%d/customer/%d", sessionId, customerId);
        ResponseEntity<SessionRegistrationDTO> registrationResponse = client.postForEntity(registrationUrl,
                null,
                SessionRegistrationDTO.class);
        int registrationId = registrationResponse.getBody().getId();

        // test action
        String getUrl = String.format("/session/%d", registrationId);
        ResponseEntity<SessionRegistrationDTO> response = client.getForEntity(getUrl,
                SessionRegistrationDTO.class);
        int registrationRegistrationID = response.getBody().getId();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        SessionRegistration registration = sessionRegistrationRepository.findById(response.getBody().getId());

        assertNotNull(registration);

        // verify the registration details match what was requested
        assertEquals(sessionId, registration.getSession().getId());
        assertEquals(customerId, registration.getCustomer().getId());
    }

    @Test
    public void testViewSpecificSessionRegistrationInvalid() {

        int invalidID = -1;

        String urlInvalid = String.format("/session/" + invalidID);
        ResponseEntity<SessionRegistrationDTO> responseInvalidId = client.getForEntity(urlInvalid,
                SessionRegistrationDTO.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseInvalidId.getStatusCode(),
                "invalid view specific session registration test failed");

        // check error message
        assertTrue(responseInvalidId.getBody().toString().contains("Bad integer value "));

        // not found?
        int notFound = Integer.MAX_VALUE;
        String urlNotFound = String.format("/session/" + invalidID);
        ResponseEntity<SessionRegistrationDTO> responseNotFound = client.getForEntity(urlNotFound,
                SessionRegistrationDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode(),
                "invalid view specific session registration test failed");

        // check error message
        assertTrue(responseNotFound.getBody().toString().contains("No registration with this ID"));

    }

    @Test
    public void testCancelRegistrationValid() {

        // setup
        Session session = createSession();
        Customer customer = createCustomer();

        sessionRepository.save(session);
        customerRepository.save(customer);

        SessionRegistration sessionRegistration = new SessionRegistration(session, customer);
        sessionRegistrationRepository.save(sessionRegistration);

        SessionDTO sessionDTO = new SessionDTO(session);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1);

        SessionRegistration registration = new SessionRegistration(session, customer);
        sessionRegistrationRepository.save(registration);

        SessionRegistrationDTO sessionRegistrationDTO = registerSession(customerDTO, sessionDTO);

        int registrationId = sessionRegistrationDTO.getId();

        // test action: Cancel the registration
        ResponseEntity<?> response = client.exchange(
                String.format("/cancel/%d", registrationId),
                HttpMethod.DELETE,
                null, // no request body
                Void.class); // no response body

        // assertions
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK.");

        boolean registrationExists = sessionRegistrationRepository.existsById(registration.getId());
        assertFalse(registrationExists, "Registration should not exist after cancellation.");

    }

    @Test
    public void testCancelRegistrationInvalid() {
        // not found registration ID
        int notFoundRegistrationId = Integer.MAX_VALUE;

        // test not found
        ResponseEntity<String> responseNotFound = client.exchange(
                String.format("/cancel/" + notFoundRegistrationId),
                HttpMethod.DELETE,
                new HttpEntity<>(null), // No request body needed for this operation
                String.class); // Assuming the error response might contain a message

        assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode(),
                "Response status should be NOT_FOUND for an invalid registration ID.");

        assertTrue(responseNotFound.getBody().contains("No registration with given ID"));

        // not found registration ID
        int notValid = -1;

        // test not found
        ResponseEntity<String> responseNotValid = client.exchange(
                String.format("/cancel/" + notFoundRegistrationId),
                HttpMethod.DELETE,
                new HttpEntity<>(null), // No request body needed for this operation
                String.class); // Assuming the error response might contain a message

        assertEquals(HttpStatus.BAD_REQUEST, responseNotValid.getStatusCode(),
                "Response status should be NOT_FOUND for an invalid registration ID.");

        assertTrue(responseNotFound.getBody().contains("Bad integer value for id"));

    }

    @Test
    public void testViewRegistrationsByCustomerValid() {
        CustomerDTO customerDTO = createCustomerDTO();

        Session session = createSession();
        sessionRepository.save(session);
        SessionDTO sessionDTO = new SessionDTO(session);

        registerSession(customerDTO, sessionDTO);

        ResponseEntity<SessionRegistrationDTO[]> response = client.getForEntity(
                "/registrations/customer/" + customerDTO.getId(),
                SessionRegistrationDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK.");

        assertNotNull(response.getBody(), "Response body should not be null.");
        assertTrue(response.getBody().length > 0, "Customer should have at least one registration.");
    }

    @Test
    public void testViewRegistrationsByCustomerInvalid() {
        // not found
        int notFoundSessionId = Integer.MAX_VALUE;

        ResponseEntity<String> responseNotFound = client.getForEntity(
                "/registrations/customer/" + notFoundSessionId,
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode(),
                "Response status should be NOT_FOUND for a non-existing session.");

        assertTrue(responseNotFound.getBody().contains("No customer with given ID"));

        int invalidID = -1;

        ResponseEntity<String> responseInvalid = client.getForEntity(
                "/registrations/customer/" + invalidID,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseNotFound.getStatusCode(),
                "Response status should be NOT_FOUND for a non-existing session.");

        assertTrue(responseInvalid.getBody().contains("Bad integer value for customerId"));

    }

    @Test
    public void testViewRegistrationsBySessionValid() {
        // Step 1: Create a session
        Session session = createSession();
        sessionRepository.save(session);

        SessionDTO sessionDTO = new SessionDTO(session);

        CustomerDTO customerDTO = createCustomerDTO();

        SessionRegistrationDTO sessionRegistrationDTO = registerSession(customerDTO, sessionDTO); // Assume this
                                                                                                  // registers
                                                                                                  // the
                                                                                                  // customer to
                                                                                                  // the session

        ResponseEntity<SessionRegistrationDTO[]> response = client.getForEntity(
                "/registrations/session/" + sessionDTO.getId(),
                SessionRegistrationDTO[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK.");

        assertNotNull(response.getBody(), "Response body should not be null.");
    }

    @Test
    public void testViewRegistrationsBySessionInvalid() {

        // not found
        int notFoundSessionId = Integer.MAX_VALUE;

        ResponseEntity<String> responseNotFound = client.getForEntity(
                "/registrations/session/" + notFoundSessionId,
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode(),
                "Response status should be NOT_FOUND for a non-existing session.");

        assertTrue(responseNotFound.getBody().contains("No session with given ID"));

        int invalidID = -1;

        ResponseEntity<String> responseInvalid = client.getForEntity(
                "/registrations/session/" + invalidID,
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseNotFound.getStatusCode(),
                "Response status should be NOT_FOUND for a non-existing session.");

        assertTrue(responseInvalid.getBody().contains("Bad integer value for sessionId"));

    }

    private Session createSession() {
        Person person = new Person("John Doe", "john.doe@mcgill.ca", "password");
        personRepository.save(person);

        Instructor instructor = new Instructor(person);
        instructorRepository.save(instructor);

        ClassType yoga = new ClassType("yoga", true);
        classTypeRepository.save(yoga);

        Session session = new Session(60, Time.valueOf("10:00:00"), Time.valueOf("11:00:00"),
                Date.valueOf("2020-03-12"), false, 10, yoga, instructor);

        return session;
    }

    private Customer createCustomer() {
        Person person = new Person("Jane Doe", "jane.doe@mcgill.ca", "password");
        personRepository.save(person);

        Customer customer = new Customer(person);

        return customer;
    }

    private SessionRegistrationDTO registerSession(CustomerDTO customerDTO, SessionDTO sessionDTO) {
        int customerId = customerDTO.getId();
        int sessionId = sessionDTO.getId();

        // construct the URL for the registration endpoint
        String url = String.format("/register/session/%d/customer/%d", sessionId, customerId);

        // Use TestRestTemplate to send a PUT request to register the session for the
        // customer
        ResponseEntity<SessionRegistrationDTO> response = client.exchange(
                url,
                HttpMethod.PUT,
                null, // You might need to send a specific HttpEntity if your endpoint requires a
                      // request body
                SessionRegistrationDTO.class);

        // Check the response status and body as needed
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody(); // Successfully registered, return the SessionRegistrationDTO
        } else {
            // Handle error or unexpected response
            throw new RuntimeException("Failed to register session: " + response.getStatusCode());
        }
    }

    private CustomerDTO createCustomerDTO() {
        // Assuming CustomerDTO has a constructor that accepts an ID and possibly other
        // fields
        // If your customer creation endpoint expects a payload, create and populate the
        // request body accordingly
        CustomerDTO requestCustomerDTO = new CustomerDTO();
        // Set any required fields of the CustomerDTO for creation

        // Make a POST request to the customer creation endpoint
        ResponseEntity<CustomerDTO> response = client.postForEntity(
                "/customers",
                new HttpEntity<>(requestCustomerDTO),
                CustomerDTO.class);

        // Verify the response status to ensure the customer was created successfully
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            // Return the CustomerDTO from the response, which should include the generated
            // ID and any other populated fields
            return response.getBody();
        } else {
            // Handle error or unexpected response
            throw new RuntimeException("Failed to create customer: " + response.getStatusCode());
        }
    }

}
