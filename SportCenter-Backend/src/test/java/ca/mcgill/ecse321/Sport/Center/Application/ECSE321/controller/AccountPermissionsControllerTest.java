package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.InstructorDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Instructor;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;
import org.springframework.http.HttpMethod;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccountPermissionsControllerTest {

    @Autowired
    private TestRestTemplate client;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private InstructorRepository instructorRepository;


    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        instructorRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    public void testGrantInstructorPermissionsValidPerson(){
        Person person = new Person();
        person.setEmail("valid@email.com");
        person.setName("valid Name");
        person.setPassword("validPass2024");
        person = personRepository.save(person);

        ResponseEntity<InstructorDTO> response2 = client.postForEntity("/instructors", person.getId(), InstructorDTO.class);

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        Instructor instructorRole = instructorRepository.findById(response2.getBody().getInstructorId());
        assertEquals(person.getName(), instructorRole.getPerson().getName());
        assertEquals(person.getEmail(), instructorRole.getPerson().getEmail());
        assertEquals(person.getPassword(), instructorRole.getPerson().getPassword());
    }

    @Test
    public void testGrantInstructorPermissionsInvalidId(){
       ResponseEntity<String> response2 = client.postForEntity("/instructors", "NOT_AN_INT", String.class);

       assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
       assertEquals("Bad integer value", response2.getBody());
    }

    @Test
    public void testGrantInstructorPermissionsIdNotFound(){ 
        ResponseEntity<?> response2 = client.postForEntity("/instructors" , 404, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
    }

    @Test
    public void testGrantInstructorPermissionsAlreadyAnInstructor(){
        Person person = new Person();
        person.setEmail("valid@email.com");
        person.setName("valid Name");
        person.setPassword("validPassword2024");
        person = personRepository.save(person);

        Instructor instructorRole = new Instructor();
        instructorRole.setPerson(person);
        instructorRole = instructorRepository.save(instructorRole);

        ResponseEntity<String> response2 = client.postForEntity("/instructors", person.getId(), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        assertEquals("Person is already an instructor", response2.getBody());
    }

    @Test
    public void testRevokeInstructorPermissionsValidInstructor(){
        Person person = new Person();
        person.setEmail("valid@email.com");
        person.setPassword("aValidPassword2024");
        person.setName("Good Name");
        person = personRepository.save(person);

        Instructor instructorRole = new Instructor();
        instructorRole.setPerson(person);
        instructorRole = instructorRepository.save(instructorRole);

        String url = "/instructors/" + instructorRole.getId();
        HttpMethod method = HttpMethod.DELETE;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<?> response2 = client.exchange(url, method, entity, String.class);

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertFalse(instructorRepository.existsById(instructorRole.getId()));
    }

    @Test
    public void testRevokeInstructorPermissionsInvalidId(){ 
        String url = "/instructors/NOT_AN_INT";
        HttpMethod method = HttpMethod.DELETE;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<?> response2 = client.exchange(url, method, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        assertEquals("Bad integer value", response2.getBody());
    }

    @Test
    public void testRevokeInstructorPermissionsIdNotFound(){
        String url = "/instructors/404";
        HttpMethod method = HttpMethod.DELETE;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<?> response2 = client.exchange(url, method, entity, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
        assertEquals("Instructor does not exist", response2.getBody().toString());
    }
}
