package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.integration;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.ClassType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.SessionResponseDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SessionIntegrationTests{
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void testCreateSession() {
        SessionResponseDTO session = new SessionResponseDTO();
        session.setId(1);
        session.setLength(60);
        session.setStartTime(Time.valueOf("10:00:00"));
        session.setEndTime(Time.valueOf("11:00:00"));
        session.setDate(Date.valueOf(LocalDate.now()));
        session.setIsRepeating(false);
        session.setMaxParticipants(10);
        ClassType yoga = new ClassType("Yoga", true);
        session.setClassType(yoga);

        ResponseEntity<SessionResponseDTO> response = restTemplate.postForEntity("/sessions", session, SessionResponseDTO.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        SessionResponseDTO sessionResponse = response.getBody();
        assertNotNull(sessionResponse);
        assertEquals(1, sessionResponse.getId());
        assertEquals(60, sessionResponse.getLength());
        assertEquals(Time.valueOf("10:00:00"), sessionResponse.getStartTime());
        assertEquals(Time.valueOf("11:00:00"), sessionResponse.getEndTime());
        assertEquals(Date.valueOf(LocalDate.now()), sessionResponse.getDate());
        assertFalse(sessionResponse.getIsRepeating());
        assertEquals(10, sessionResponse.getMaxParticipants());
        assertEquals(yoga, sessionResponse.getClassType());
    }

    @Test
    @Order(2)
    public void testUpdateSession() {
        SessionResponseDTO session = new SessionResponseDTO();
        session.setId(1);
        session.setLength(90);
        session.setStartTime(Time.valueOf("09:00:00"));
        session.setEndTime(Time.valueOf("10:30:00"));
        session.setDate(Date.valueOf(LocalDate.now().plusDays(1)));
        session.setIsRepeating(true);
        session.setMaxParticipants(15);
        ClassType yoga = new ClassType("Yoga", true);
        session.setClassType(yoga);

        restTemplate.put("/sessions/1", session);

        ResponseEntity<SessionResponseDTO> response = restTemplate.getForEntity("/sessions/1", SessionResponseDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        SessionResponseDTO sessionResponse = response.getBody();
        assertNotNull(sessionResponse);
        assertEquals(1, sessionResponse.getId());
        assertEquals(90, sessionResponse.getLength());
        assertEquals(Time.valueOf("09:00:00"), sessionResponse.getStartTime());
        assertEquals(Time.valueOf("10:30:00"), sessionResponse.getEndTime());
        assertEquals(Date.valueOf(LocalDate.now().plusDays(1)), sessionResponse.getDate());
        assertTrue(sessionResponse.getIsRepeating());
        assertEquals(15, sessionResponse.getMaxParticipants());
        assertEquals(yoga, sessionResponse.getClassType());
    }

    @Test
    @Order(3)
    public void testDeleteSession() {
        restTemplate.delete("/sessions/1");
        ResponseEntity<SessionResponseDTO> response = restTemplate.getForEntity("/sessions/1", SessionResponseDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}