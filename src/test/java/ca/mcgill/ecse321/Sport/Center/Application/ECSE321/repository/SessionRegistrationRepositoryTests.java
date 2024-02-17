package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.*;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SessionRegistrationRepositoryTests {

    @Autowired
    private SessionRegistrationRepository regRepo;
    @Autowired
    private SessionRepository sessionRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private InstructorRepository instructorRepo;
    @Autowired
    private ClassTypeRepository classTypeRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        regRepo.deleteAll();
        sessionRepo.deleteAll();
        customerRepo.deleteAll();
        instructorRepo.deleteAll();
        classTypeRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadRegistration() {
        // Create Times
        LocalTime localStartTime = LocalTime.of(11, 0, 0); // 08:30:00
        LocalTime localEndTime = LocalTime.of(12, 0, 0);   // 12:00:00

        //Create Customer
        int bobId = 1;
        Customer bob = new Customer(bobId);
        bob = customerRepo.save(bob);

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
        yogaSession = sessionRepo.save(yogaSession);

        // Create registration
        int regId = 1;
        SessionRegistration reg = new SessionRegistration(regId, yogaSession, bob);

        // Save in database
        reg = regRepo.save(reg);

        // Read back from database
        regId = reg.getId();
        SessionRegistration regFromDB = regRepo.getSessionRegistrationById(regId);

        // Assertions
        assertNotNull(regFromDB);
        assertEquals(regId, regFromDB.getId());
        //assertEquals(bob.toString(), regFromDB.getCustomer().toString());
        assertEquals(yogaSession, regFromDB.getSession());
        assertNotNull(yogaSession);
        assertEquals(yogaSession.getId(), regFromDB.getSession().getId());
        assertEquals(yogaSession.getLength(), regFromDB.getSession().getLength());
        assertEquals(yogaSession.getStartTime(), regFromDB.getSession().getStartTime());
        assertEquals(yogaSession.getEndTime(), regFromDB.getSession().getEndTime());
        assertEquals(yogaSession.getDate(), regFromDB.getSession().getDate());
        assertEquals(yogaSession.getIsRepeating(), regFromDB.getSession().getIsRepeating());
        assertEquals(yogaSession.getMaxParticipants(), regFromDB.getSession().getMaxParticipants());
        assertEquals(yogaSession.getClassType(), regFromDB.getSession().getClassType());
        assertEquals(yogaSession.getInstructor(), regFromDB.getSession().getInstructor());
        assertNotNull(yoga);
        assertEquals(yoga.getClassType(), regFromDB.getSession().getClassType().getClassType());
        assertNotNull(yuri);
        assertEquals(yuri.getId(), regFromDB.getSession().getInstructor().getId());
        assertNotNull(bob);
        assertEquals(bob.getId(), regFromDB.getCustomer().getId());
        clearDatabase();
    }

}
