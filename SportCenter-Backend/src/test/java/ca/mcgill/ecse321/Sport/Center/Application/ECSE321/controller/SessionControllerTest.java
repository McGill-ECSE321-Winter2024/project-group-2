package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.ArrayList;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.CustomerDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.PersonDTO;


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
        personRepository.save(person);

        Instructor instructor = new Instructor(person);
        instructorRepository.save(instructor);

        ClassType yoga = new ClassType("yoga", true);
        classTypeRepository.save(yoga);

        Session session = new Session(60, Time.valueOf("10:00:00"), Time.valueOf("11:00:00"),
                Date.valueOf("2020-03-12"), false, 10, yoga, instructor);
        sessionRepository.save(session);

        SessionDTO request = new SessionDTO(session);

        ResponseEntity<SessionDTO> response = client.postForEntity("/sessions", request, SessionDTO.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        SessionDTO sessionResponse = response.getBody();
        assertNotNull(sessionResponse);
        assertEquals(session.getLength(), sessionResponse.getLength());
        assertEquals(session.getStartTime(), sessionResponse.getStartTime());
        assertEquals(session.getEndTime(), sessionResponse.getEndTime());
        //assertEquals(session.getDate(), sessionResponse.getDate());
        assertEquals(session.getIsRepeating(), sessionResponse.getIsRepeating());
        assertEquals(session.getMaxParticipants(), sessionResponse.getMaxParticipants());
        assertEquals(session.getClassType().getClassType(), sessionResponse.getClassType().getClassType());
        assertEquals(session.getInstructor().getPerson().getId(), sessionResponse.getInstructor().getPerson().getId());
    }

    @Test
    public void testCreateSessionFail() {
        Person person = new Person("John Doe", "john.doe@mcgill.ca", "password");
        personRepository.save(person);

        Instructor instructor = new Instructor(person);
        instructorRepository.save(instructor);

        ClassType yoga = new ClassType("yoga", true);
        classTypeRepository.save(yoga);

        Session session = new Session(-60, Time.valueOf("12:00:00"), Time.valueOf("10:00:00"),
                Date.valueOf("2020-03-12"), false, 10, yoga, instructor);
        sessionRepository.save(session);

        SessionDTO request = new SessionDTO(session);

        ResponseEntity<SessionDTO> response = client.postForEntity("/sessions", request, SessionDTO.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetSessionByIdSuccess() {
        Person person = new Person("John Doe", "john.doe@mcgill.ca", "password");
        personRepository.save(person);

        Instructor instructor = new Instructor(person);
        instructorRepository.save(instructor);

        ClassType yoga = new ClassType("yoga", true);
        classTypeRepository.save(yoga);

        Session session = new Session(60, Time.valueOf("10:00:00"), Time.valueOf("11:00:00"),
                Date.valueOf("2020-03-12"), false, 10, yoga, instructor);
        sessionRepository.save(session);

        String url = "/sessions/" + session.getId();

        ResponseEntity<SessionDTO> response = client.getForEntity(url, SessionDTO.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        SessionDTO sessionResponse = response.getBody();
        assertNotNull(sessionResponse);
        assertEquals(session.getLength(), sessionResponse.getLength());
        assertEquals(session.getStartTime(), sessionResponse.getStartTime());
        assertEquals(session.getEndTime(), sessionResponse.getEndTime());
        //assertEquals(session.getDate(), sessionResponse.getDate());
        assertEquals(session.getIsRepeating(), sessionResponse.getIsRepeating());
        assertEquals(session.getMaxParticipants(), sessionResponse.getMaxParticipants());
        assertEquals(session.getClassType().getClassType(), sessionResponse.getClassType().getClassType());
        assertEquals(session.getInstructor().getPerson().getId(), sessionResponse.getInstructor().getPerson().getId());
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

        ResponseEntity<SessionDTO> response = client.getForEntity(url, SessionDTO.class);

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
    
}
