package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.ArrayList;

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

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.CustomerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.CustomerDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.PersonDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;

@SuppressWarnings("null")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {
    
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        customerRepository.deleteAll();
        personRepository.deleteAll();
    }

    
    @Test
    public void testCreateCustomerAccountValid() {
        PersonDTO person = new PersonDTO(0, "aValidPassword2024", "valid@email.com", "Good Name");
        ResponseEntity<CustomerDTO> response = client.postForEntity("/customers", person, CustomerDTO.class);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        Customer customerRole = customerRepository.findById(response.getBody().getId());
        assertEquals(person.getName(), customerRole.getPerson().getName());
        assertEquals(person.getEmail(), customerRole.getPerson().getEmail());
        assertEquals(person.getPassword(), customerRole.getPerson().getPassword());
    }

    
    @Test
    public void testCreateCustomerAccountInvalid(){
        //Testing for null personDTO object
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<?> response = client.postForEntity("/customers", entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "PersonDTO object cannot be null.");

        //Testing for invalid email
        PersonDTO person = new PersonDTO(0, "aValidPassword2024", "badEmail", "Good Name");
        ResponseEntity<?> response2 = client.postForEntity("/customers", person, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode(), "Invalid email test failed");
        assertTrue(response2.getBody().toString().contains("Email must be in a valid format."));

        //Testing for invalid password
        person.setEmail("valid@email.com");
        person.setPassword("invalid");
        ResponseEntity<?> response3 = client.postForEntity("/customers", person, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response3.getStatusCode(), "Invalid password test failed");
        assertTrue(response3.getBody().toString().contains("Password"));
    
        //Testing for duplicate customer creation
        person.setPassword("aValidPassword2024");
        client.postForEntity("/customers", person, CustomerDTO.class);
        ResponseEntity<?> response4 = client.postForEntity("/customers", person, String.class);
        assertEquals(HttpStatus.CONFLICT, response4.getStatusCode(),"Customer account already exists");
    
    }

    
    @Test
    public void testCreatePersonValid(){
        PersonDTO person = new PersonDTO(0, "aValidPassword2024", "valid@email.com","good name");
        ResponseEntity<PersonDTO> response = client.postForEntity("/persons", person, PersonDTO.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        
        Person personInDB = personRepository.findById(response.getBody().getPersonId());
        assertEquals(person.getName(), personInDB.getName());
        assertEquals(person.getEmail(), personInDB.getEmail());
        assertEquals(person.getPassword(), personInDB.getPassword());
    }

    
    @Test
    public void testCreatePersonInvalid(){
        //Testing for null personDTO object
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<?> response = client.postForEntity("/persons", entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "PersonDTO object cannot be null.");

        //Testing for invalid email
        PersonDTO person = new PersonDTO(0, "aValidPassword2024", "badEmail", "Good Name");
        ResponseEntity<?> response2 = client.postForEntity("/persons", person, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode(), "Invalid email test failed");
        assertTrue(response2.getBody().toString().contains("Email must be in a valid format."));

        //Testing for invalid password
        person.setEmail("valid@email.com");
        person.setPassword("invalidpass");
        ResponseEntity<?> response3 = client.postForEntity("/persons", person, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response3.getStatusCode(), "Invalid password test failed");
        assertTrue(response3.getBody().toString().contains("Password"));

        //Testing for duplicate person creation
        person.setPassword("aValidPassword2024");
        client.postForEntity("/persons", person, PersonDTO.class);
        ResponseEntity<?> response4 = client.postForEntity("/persons", person, String.class);
        assertEquals(HttpStatus.CONFLICT, response4.getStatusCode(),"Account with this email already exists");
    }

    
    @Test
    public void testLoginValid() {
        PersonDTO person = new PersonDTO(0, "aValidPassword2024", "valid@email.com", "Good Name");
        client.postForEntity("/persons", person, PersonDTO.class);

        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("email", person.getEmail());
        credentials.put("password", person.getPassword());
        ResponseEntity<?> response = client.postForEntity("/login", credentials, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("true"));
    }

    
    @Test
    public void testLoginInvalidPassword() {
        PersonDTO person = new PersonDTO(0, "aValidPassword2024", "valid@email.com", "Good Name");
        client.postForEntity("/persons", person, PersonDTO.class);

        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("email", person.getEmail());
        credentials.put("password", "wrongPassword2024");
        ResponseEntity<?> response = client.postForEntity("/login", credentials, String.class);
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("false"));
    }

    @Test
    public void testLoginInvalidEmail() {
        PersonDTO person = new PersonDTO(0, "aValidPassword2024", "valid@email.com", "Good Name");
        client.postForEntity("/persons", person, PersonDTO.class);

        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("email", "wrong@email.com");
        credentials.put("password", person.getPassword());
        ResponseEntity<?> response = client.postForEntity("/login", credentials, String.class);
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("false"));
    }

    @Test
    public void testGetPersonByIdValid(){
        PersonDTO person = new PersonDTO(0, "aValidPassword2024", "valid@email.com", "Good Name");
        ResponseEntity<PersonDTO> response = client.postForEntity("/persons", person, PersonDTO.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        int personId = response.getBody().getPersonId();

        ResponseEntity<PersonDTO> response2 = client.getForEntity("/persons/" + personId, PersonDTO.class);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(person.getName(), response2.getBody().getName());
        assertEquals(person.getEmail(), response2.getBody().getEmail());
    }
    
    @Test
    public void testGetPersonByIdInvalid(){
        PersonDTO person = new PersonDTO(0, "aValidPassword2024", "valid@email.com", "Good Name");
        ResponseEntity<PersonDTO> response = client.postForEntity("/persons", person, PersonDTO.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        
        int wrongPersonId = response.getBody().getPersonId()+1;
        ResponseEntity<?> response2 = client.getForEntity("/persons/"+wrongPersonId, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
        assertTrue(response2.getBody().toString().contains("There is no person with this ID"));

        String wrongPersonIdString = "notAnInt";
        ResponseEntity<?> response3 = client.getForEntity("/persons/"+wrongPersonIdString, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response3.getStatusCode());
    }

    @Test
    public void testGetAllPersonsValid(){
        PersonDTO person = new PersonDTO(0, "aValidPassword2024", "valid@email.com", "Good Name");
        PersonDTO person2 = new PersonDTO(1, "aValidPassword2024", "another@validEmail.com", "anotherGood Name");
        client.postForEntity("/persons", person, PersonDTO.class);
        client.postForEntity("/persons", person2, PersonDTO.class);

        @SuppressWarnings("rawtypes")
        ResponseEntity<ArrayList> response = client.getForEntity("/persons", ArrayList.class);
        assertEquals(2,response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteCustomerValid(){
        Person person = new Person();
        person.setName("Good Name");
        person.setEmail("valid@email.com");
        person.setPassword("aValidPassword2024");
        person = personRepository.save(person);

        Customer customer = new Customer();
        customer.setPerson(person);
        customer = customerRepository.save(customer);
        
        String url = "/customers/" + customer.getId();
        HttpMethod method = HttpMethod.DELETE;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<?> response2 = client.exchange(url, method, entity, String.class);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertTrue(!customerRepository.existsById(customer.getId()));
    }

    @Test
    public void testDeleteCustomerInvalid(){   
        String url = "/customers/1234";
        HttpMethod method = HttpMethod.DELETE;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<?> response2 = client.exchange(url, method, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
    }

    @Test
    public void testDeletePersonValid(){
        Person person = new Person();
        person.setName("Good Name");
        person.setEmail("valid@email.com");
        person.setPassword("ValidPass12324");
        person = personRepository.save(person);

        String url = "/persons/" + person.getId();
        HttpMethod method = HttpMethod.DELETE;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<?> response = client.exchange(url, method, entity, String.class);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test 
    public void testDeletePersonInvalid(){
        String url = "/persons/1234" ;
        HttpMethod method = HttpMethod.DELETE;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<?> response = client.exchange(url, method, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
