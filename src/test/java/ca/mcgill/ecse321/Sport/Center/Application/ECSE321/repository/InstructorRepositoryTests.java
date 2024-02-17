package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class InstructorRepositoryTests {
        
    @Autowired
    private InstructorRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadInstructor() {
        // Create instructor
        int yuriId = 1;
        Instructor yuri = new Instructor(yuriId);

        // Save in database
        yuri = repo.save(yuri);

        // Read back from database
        Optional<Instructor> yuriFromDB = repo.findById(yuriId);

        // Assertions
        assertNotNull(yuriFromDB);
        assertEquals(yuriId, yuriFromDB.get().getId());
    }

}
