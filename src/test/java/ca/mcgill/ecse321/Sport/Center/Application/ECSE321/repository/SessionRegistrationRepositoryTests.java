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
    private SessionRegistrationRepository registrationRepo;
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
        registrationRepo.deleteAll();
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
        reg = registrationRepo.save(reg);

        // Read back from database
        regId = reg.getId();
        SessionRegistration regFromDB = registrationRepo.getSessionRegistrationById(regId);

        //Base Registration Assertions
        assertNotNull(regFromDB);
        assertEquals(regId, regFromDB.getId());
        //Customer Assertions
        Customer customerFromDB = regFromDB.getCustomer();
        assertNotNull(customerFromDB);
        assertEquals(bob.getId(), customerFromDB.getId());
        //Session Assertions
        Session yogaSessionFromDB = regFromDB.getSession();
        assertNotNull(yogaSessionFromDB);
        assertEquals(yogaSession.getClassType().getClassType(), yogaSessionFromDB.getClassType().getClassType());
        assertEquals(yogaSession.getDate(), yogaSessionFromDB.getDate());
        assertEquals(yogaSession.getEndTime(), yogaSessionFromDB.getEndTime());
        assertEquals(yogaSession.getId(), yogaSessionFromDB.getId());
        assertEquals(yogaSession.getInstructor().getId(), yogaSessionFromDB.getInstructor().getId());
        assertEquals(yogaSession.getIsRepeating(), yogaSessionFromDB.getIsRepeating());
        assertEquals(yogaSession.getLength(), yogaSessionFromDB.getLength());
        assertEquals(yogaSession.getMaxParticipants(), yogaSessionFromDB.getMaxParticipants());
        assertEquals(yogaSession.getStartTime(), yogaSessionFromDB.getStartTime());
        //Instructor Assertions
        ClassType typeFromDB = yogaSessionFromDB.getClassType();
        assertNotNull(typeFromDB);
        assertEquals(yoga.getClassType(), typeFromDB.getClassType());

    }

}
