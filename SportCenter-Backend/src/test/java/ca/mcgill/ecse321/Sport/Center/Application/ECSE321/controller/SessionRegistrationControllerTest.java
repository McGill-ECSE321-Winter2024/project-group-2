package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.*;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.SessionRegistration;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.ClassType;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Instructor;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;

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
    public void testViewSpecificSessionRegistrationInvalidIDNotFound() {

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

    @Test
    public void testCancelRegistrationValid() {

        Session session = createSession();

        session = sessionRepository.save(session);

        Person person = new Person("name", "nameemail@mcgill.ca", "password");
        person = personRepository.save(person);

        Customer customer = new Customer(person);
        customer = customerRepository.save(customer);

        SessionRegistration sessionRegistration = new SessionRegistration(session, customer);
        sessionRegistration = sessionRegistrationRepository.save(sessionRegistration);

        int registrationId = sessionRegistration.getId();

        // REDO THIS
        // test action: Cancel the registration
        ResponseEntity<?> response = client.exchange(
                String.format("sessionRegistrations/" + registrationId),
                HttpMethod.DELETE,
                null, // no request body
                Void.class); // no response body

        // assertions
        assertEquals(HttpStatus.OK, response.getStatusCode(), "response status should be OK.");
        boolean registrationExists = sessionRegistrationRepository.existsById(sessionRegistration.getId());
        assertFalse(registrationExists, "Registration should not exist after cancellation.");

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
    public void testCancelRegistrationInvalidNotFound() {

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
    public void testViewRegistrationsInvalidByCustomerNotFound() {

        int notFound = Integer.MAX_VALUE;

        String urlInvalid = String.format("/sessionRegistrations/customers/" + notFound);

        ResponseEntity<String> responseInvalidId = client.getForEntity(urlInvalid, String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseInvalidId.getStatusCode(),
                "Response status should be NOT_FOUND");

        // check error message
        assertTrue(responseInvalidId.getBody().toString().contains("No customer with given ID"));

    }

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
    public void testViewRegistrationsInvalidBySessionNotFound() {

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

}
