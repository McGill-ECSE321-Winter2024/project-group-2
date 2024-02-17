package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.ClassTypeRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.SessionRepository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SessionRepositoryTests {

    @Autowired
    private InstructorRepository instructorRepo;
    @Autowired
    private ClassTypeRepository classTypeRepo;
    @Autowired
    private SessionRepository sessionRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        instructorRepo.deleteAll();
        classTypeRepo.deleteAll();
        sessionRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadSession() {
        // Create Times
        LocalTime localStartTime = LocalTime.of(11, 0, 0); // 08:30:00
        LocalTime localEndTime = LocalTime.of(12, 0, 0);   // 12:00:00

        // Create instructor
        int yuriId = 1;
        Instructor yuri = new Instructor(yuriId);
        yuri = instructorRepo.save(yuri);

        // Create class type
        ClassType yoga = new ClassType("yoga");
        yoga = classTypeRepo.save(yoga);

        // Create session
        int sessionId = 1;
        int length = 60;
        Time startTime = Time.valueOf(localStartTime);
        Time endTime = Time.valueOf(localEndTime);
        Date date = Date.valueOf(LocalDate.of(2024, 2, 18));
        boolean isRepeating = true;
        int maxParticipants = 50;
        Session yogaSession = new Session(sessionId, length, startTime, endTime, date,
                isRepeating, maxParticipants, yoga, yuri);

        // Save in database
        yogaSession = sessionRepo.save(yogaSession);

        // Read back from database
        Optional<Session> sessionFromDB = sessionRepo.findById(sessionId);

        // Assertions
        assertNotNull(sessionFromDB);
        assertEquals(sessionId, sessionFromDB.get().getId());
        assertEquals(length, sessionFromDB.get().getId());
        assertEquals(startTime, sessionFromDB.get().getStartTime());
        assertEquals(endTime, sessionFromDB.get().getEndTime());
        assertEquals(date, sessionFromDB.get().getDate());
        assertEquals(isRepeating, sessionFromDB.get().getIsRepeating());
        assertEquals(maxParticipants, sessionFromDB.get().getMaxParticipants());
        assertEquals(yoga, sessionFromDB.get().getClassType());
        assertEquals(yuri, sessionFromDB.get().getInstructor());
        assertNotNull(yuri);
        assertEquals(yuriId, yuri.getId());
    }

}
