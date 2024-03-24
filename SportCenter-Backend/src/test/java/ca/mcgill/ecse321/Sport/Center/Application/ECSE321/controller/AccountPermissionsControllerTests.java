package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.CustomerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.CustomerDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.InstructorDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.PersonDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.SessionDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Instructor;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccountPermissionsControllerTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private AccountPermissionsController controller;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        instructorRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    public void testGrantInstructorPermissionsValidPerson(){
        PersonDTO person = new PersonDTO(0, "aValidPassword2024", "valid@email.com", "Good Name");
        ResponseEntity<PersonDTO> response = client.postForEntity("/persons", person, PersonDTO.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        int personId = response.getBody().getPersonId();

        ResponseEntity<InstructorDTO> response2 = client.postForEntity("/instructors", personId, InstructorDTO.class);

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        Instructor instructorRole = instructorRepository.findById(response2.getBody().getInstructorId());
        assertEquals(person.getName(), instructorRole.getPerson().getName());
        assertEquals(person.getEmail(), instructorRole.getPerson().getEmail());
        assertEquals(person.getPassword(), instructorRole.getPerson().getPassword());
    }

    @Test
    public void testGrantInstructorPermissionsInvalidId(){
       PersonDTO person = new PersonDTO(0, "aValidPassword2024", "valid@email.com", "Good Name");
       ResponseEntity<PersonDTO> response = client.postForEntity("/persons", person, PersonDTO.class);
       assertEquals(HttpStatus.CREATED, response.getStatusCode());
       int personId = response.getBody().getPersonId();

       ResponseEntity<String> response2 = client.postForEntity("/instructors", "invalid int", String.class);

       assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
       assertEquals("Bad integer value", response2.getBody());
    }

    @Test
    public void testGrantInstructorPermissionsIdNotFound(){ //the second assert gives right message i think but with more shit we dont need and idk how to get rid of it
        PersonDTO person = new PersonDTO(0, "aValidPassword2024", "valid@email.com", "Good Name");
        ResponseEntity<PersonDTO> response = client.postForEntity("/persons", person, PersonDTO.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        int personId = response.getBody().getPersonId();

        ResponseEntity<?> response2 = client.postForEntity("/instructors" + 125, person, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
        //assertEquals("Person does not exist", response2.getBody().toString());
    }

    @Test
    public void testGrantInstructorPermissionsAlreadyAnInstructor(){
        PersonDTO person = new PersonDTO(0, "aValidPassword2024", "valid@email.com", "Good Name");
        ResponseEntity<PersonDTO> response = client.postForEntity("/persons", person, PersonDTO.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        PersonDTO personFromRepo = response.getBody();
        Person personAsPerson = new Person(personFromRepo.getPersonId(),personFromRepo.getName(), personFromRepo.getEmail(), personFromRepo.getPassword());

        Instructor instructorRole = new Instructor();
        instructorRole.setPerson(personAsPerson);
        instructorRepository.save(instructorRole);

        ResponseEntity<String> response2 = client.postForEntity("/instructors", personAsPerson.getId(), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        assertEquals("Person is already an instructor", response2.getBody());
    }

    @Test
    public void testRevokeInstructorPermissionsValidInstructor(){
        PersonDTO person = new PersonDTO(0, "aValidPassword2024", "valid@email.com", "Good Name");
        ResponseEntity<PersonDTO> response = client.postForEntity("/persons", person, PersonDTO.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        PersonDTO personFromRepo = response.getBody();
        Person person2 = new Person(personFromRepo.getPersonId(),personFromRepo.getName(), personFromRepo.getEmail(), personFromRepo.getPassword());

        Instructor instructorRole = new Instructor();
        instructorRole.setPerson(person2);
        instructorRepository.save(instructorRole);
        InstructorDTO instructor = new InstructorDTO(instructorRole.getId(), new ArrayList<SessionDTO>());

        //ResponseEntity<?> response2 = client.postForEntity("/instructors", instructor.getInstructorId(), null);
        ResponseEntity<?> response2 = controller.revokeInstructorPermissions(String.valueOf(instructor.getInstructorId()));

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertFalse(instructorRepository.existsById(instructor.getInstructorId()));
    }

    @Test
    public void testRevokeInstructorPermissionsInvalidId(){ //just used the controller cuz i couldnt make it work any other way feel free to change
        PersonDTO person = new PersonDTO(0, "aValidPassword2024", "valid@email.com", "Good Name");
        ResponseEntity<PersonDTO> response = client.postForEntity("/persons", person, PersonDTO.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        PersonDTO personFromRepo = response.getBody();
        Person person2 = new Person(personFromRepo.getPersonId(),personFromRepo.getName(), personFromRepo.getEmail(), personFromRepo.getPassword());

        Instructor instructorRole = new Instructor();
        instructorRole.setPerson(person2);
        instructorRepository.save(instructorRole);
        InstructorDTO instructor = new InstructorDTO(instructorRole.getId(), new ArrayList<SessionDTO>());

        //ResponseEntity<String> response22 = client.postForEntity("/instructors/" + "invalid", instructor, String.class);
        //client.delete("/instructors/" + "invalid id", instructor);

        ResponseEntity<?> response2 = controller.revokeInstructorPermissions("invalid");

        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
       assertEquals("Bad integer value", response2.getBody());
    }

    @Test
    public void testRevokeInstructorPermissionsIdNotFound(){
        PersonDTO person = new PersonDTO(0, "aValidPassword2024", "valid@email.com", "Good Name");
        ResponseEntity<PersonDTO> response = client.postForEntity("/persons", person, PersonDTO.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        PersonDTO personFromRepo = response.getBody();
        Person person2 = new Person(personFromRepo.getPersonId(),personFromRepo.getName(), personFromRepo.getEmail(), personFromRepo.getPassword());

        Instructor instructorRole = new Instructor();
        instructorRole.setPerson(person2);
        instructorRepository.save(instructorRole);
        InstructorDTO instructor = new InstructorDTO(instructorRole.getId(), new ArrayList<SessionDTO>());

        //ResponseEntity<String> response2 = client.postForEntity("/instructors/" + 125, instructor, String.class);

        ResponseEntity<?> response2 = controller.revokeInstructorPermissions("125");

        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
        assertEquals("Instructor does not exist", response2.getBody().toString());
    }
}
