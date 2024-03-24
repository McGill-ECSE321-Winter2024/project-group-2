package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.*;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.SessionDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

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

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SessionControllerTest {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private ClassTypeRepository classTypeRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        sessionRepository.deleteAll();
        instructorRepository.deleteAll();
        personRepository.deleteAll();
        classTypeRepository.deleteAll();
    }

    @Test
    public void testCreateSessionSuccess(){
        Person person = new Person("John Doe", "john.doe@mcgill.ca", "password");
        person = personRepository.save(person);

        Instructor instructor = new Instructor(person);
        instructor = instructorRepository.save(instructor);

        ClassType yoga = new ClassType("yoga", true);
        yoga = classTypeRepository.save(yoga);

        Session session = new Session(60, Time.valueOf("10:00:00"), Time.valueOf("11:00:00"),
                Date.valueOf("2020-03-12"), false, 10, yoga, instructor);

        SessionDTO request = new SessionDTO(session);

        ResponseEntity<SessionDTO> response = client.postForEntity("/sessions", request, SessionDTO.class);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        SessionDTO sessionResponse = response.getBody();
        assertNotNull(sessionResponse);
        assertEquals(session.getLength(), sessionResponse.getLength());
        assertEquals(session.getStartTime(), sessionResponse.getStartTime());
        assertEquals(session.getEndTime(), sessionResponse.getEndTime());
        assertEquals(session.getIsRepeating(), sessionResponse.getIsRepeating());
        assertEquals(session.getMaxParticipants(), sessionResponse.getMaxParticipants());
        assertEquals(session.getClassType().getClassType(), sessionResponse.getClassType().getClassType());
    }

    @Test
    public void testCreateSessionFail() {
        Person person = new Person("John Doe", "john.doe@mcgill.ca", "password");
        person = personRepository.save(person);

        Instructor instructor = new Instructor(person);
        instructor = instructorRepository.save(instructor);

        ClassType yoga = new ClassType("yoga", true);
        yoga = classTypeRepository.save(yoga);

        Session session = new Session(-60, Time.valueOf("12:00:00"), Time.valueOf("10:00:00"),
                Date.valueOf("2020-03-12"), false, 10, yoga, instructor);
        session = sessionRepository.save(session);

        SessionDTO request = new SessionDTO(session);

        ResponseEntity<String> response = client.postForEntity("/sessions", request, String.class);

        assertNotNull(response);
        assertTrue(response.getBody().contains("Start time must be before end time"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetSessionByIdSuccess() {
        Person person = new Person("John Doe", "john.doe@mcgill.ca", "password");
        person = personRepository.save(person);

        Instructor instructor = new Instructor(person);
        instructor = instructorRepository.save(instructor);

        ClassType yoga = new ClassType("yoga", true);
        yoga = classTypeRepository.save(yoga);

        Session session = new Session(60, Time.valueOf("10:00:00"), Time.valueOf("11:00:00"),
                Date.valueOf("2020-03-12"), false, 10, yoga, instructor);
        session = sessionRepository.save(session);

        String url = "/sessions/" + session.getId();

        ResponseEntity<SessionDTO> response = client.getForEntity(url, SessionDTO.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        SessionDTO sessionResponse = response.getBody();
        assertNotNull(sessionResponse);
        assertEquals(session.getLength(), sessionResponse.getLength());
        assertEquals(session.getStartTime(), sessionResponse.getStartTime());
        assertEquals(session.getEndTime(), sessionResponse.getEndTime());
        assertEquals(session.getIsRepeating(), sessionResponse.getIsRepeating());
        assertEquals(session.getMaxParticipants(), sessionResponse.getMaxParticipants());
        assertEquals(session.getClassType().getClassType(), sessionResponse.getClassType().getClassType());
        assertEquals(session.getInstructor().getId(), sessionResponse.getInstructorId());
    }

    @Test
    public void testGetSessionByIdFail() {
        Person person = new Person("John Doe", "john.doe@mcgill.ca", "password");
        personRepository.save(person);

        Instructor instructor = new Instructor(person);
        instructorRepository.save(instructor);

        ClassType yoga = new ClassType("yoga", true);
        classTypeRepository.save(yoga);

        Session session = new Session(60, Time.valueOf("10:00:00"), Time.valueOf("11:00:00"),
                Date.valueOf("2020-03-12"), false, 10, yoga, instructor);
        sessionRepository.save(session);

        String url = "/sessions/1234567890";

        ResponseEntity<String> response = client.getForEntity(url, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAllSessionsSuccess() {
        Person person = new Person("John Doe", "john.doe@mcgill.ca", "password");
        personRepository.save(person);

        Instructor instructor = new Instructor(person);
        instructorRepository.save(instructor);

        ClassType yoga = new ClassType("yoga", true);
        classTypeRepository.save(yoga);

        Session session1 = new Session(60, Time.valueOf("10:00:00"), Time.valueOf("11:00:00"),
                Date.valueOf("2020-03-12"), false, 10, yoga, instructor);
        sessionRepository.save(session1);

        Session session2 = new Session(60, Time.valueOf("12:00:00"), Time.valueOf("13:00:00"),
                Date.valueOf("2020-03-13"), false, 10, yoga, instructor);
        sessionRepository.save(session2);

        ResponseEntity<SessionDTO[]> response = client.getForEntity("/sessions", SessionDTO[].class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        SessionDTO[] sessions = response.getBody();
        assertNotNull(sessions);
        assertEquals(2, sessions.length);
    }

    @Test
    public void testUpdateSessionSuccess() {
        Person person = new Person();
        person.setEmail("valid@email.com");
        person.setPassword("password");
        person = personRepository.save(person);

        Instructor instructor = new Instructor(person);
        instructor = instructorRepository.save(instructor);

        ClassType yoga = new ClassType("yoga", true);
        yoga = classTypeRepository.save(yoga);

        Session session = new Session(60, Time.valueOf("10:00:00"), Time.valueOf("11:00:00"),
                Date.valueOf("2020-03-12"), false, 10, yoga, instructor);
        session = sessionRepository.save(session);

        SessionDTO request = new SessionDTO(session);
        request.setLength(120);

        HttpMethod method = HttpMethod.PUT;
        String url = "/sessions/" + session.getId();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SessionDTO> entity = new HttpEntity<>(request, headers);
        
        ResponseEntity<SessionDTO> response = client.exchange(url, method, entity, SessionDTO.class);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(120, response.getBody().getLength());
    }
    
    @Test
    public void testUpdateSessionFail(){
        Person person = new Person();
        person.setEmail("valid@email.com");
        person.setPassword("password");
        person = personRepository.save(person);

        Instructor instructor = new Instructor(person);
        instructor = instructorRepository.save(instructor);

        ClassType yoga = new ClassType("yoga", true);
        yoga = classTypeRepository.save(yoga);

        Session session = new Session(60, Time.valueOf("10:00:00"), Time.valueOf("11:00:00"),
                Date.valueOf("2020-03-12"), false, 10, yoga, instructor);
        session = sessionRepository.save(session);
        
        SessionDTO attemptedUpdate = new SessionDTO(session);
        attemptedUpdate.setEndTime(attemptedUpdate.getStartTime());
        attemptedUpdate.setStartTime(Time.valueOf("11:00:00"));

        ClassType badClassType = new ClassType("badClassType", false);
        badClassType = classTypeRepository.save(badClassType);
        attemptedUpdate.setClassType(badClassType);

        HttpMethod method = HttpMethod.PUT;
        String url = "/sessions/1234";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SessionDTO> entity = new HttpEntity<>(attemptedUpdate, headers);
        
        ResponseEntity<String> response = client.exchange(url, method, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Start time must be before end time"));
        assertTrue(response.getBody().contains("Class type must be approved"));
    }

    @Test
    public void testDeleteSessionSuccess(){
        Session session = createDefaultSession();
        SessionDTO request = new SessionDTO(session);

        HttpMethod method = HttpMethod.DELETE;
        String url = "/sessions/" + session.getId();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SessionDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<SessionDTO> response = client.exchange(url, method, entity, SessionDTO.class);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertTrue(!sessionRepository.existsById(session.getId()));
    }

    public Session createDefaultSession(){
        Person person = new Person();
        person.setEmail("valid@email.com");
        person.setPassword("password");
        person = personRepository.save(person);

        Instructor instructor = new Instructor(person);
        instructor = instructorRepository.save(instructor);

        ClassType yoga = new ClassType("yoga", true);
        yoga = classTypeRepository.save(yoga);

        Session session = new Session(60, Time.valueOf("10:00:00"), Time.valueOf("11:00:00"),
                Date.valueOf("2020-03-12"), false, 10, yoga, instructor);
        session = sessionRepository.save(session);
        return session;
    }
}
