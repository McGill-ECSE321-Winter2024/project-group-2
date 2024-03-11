package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;



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
        //int instructorId = 1;
        int id = 0;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        Instructor instructor = new Instructor(person);

        // Save in database
        instructor = repo.save(instructor);

        // Read back from database
        Instructor instructorFromDB = repo.getInstructorByPersonName(name);

        // Assertions
        assertNotNull(instructorFromDB);
        assertEquals(instructor.getPerson().getId(), instructorFromDB.getPerson().getId());
        assertEquals(instructor.getPerson().getEmail(), instructorFromDB.getPerson().getEmail());
        assertEquals(instructor.getPerson().getName(), instructorFromDB.getPerson().getName());
        assertEquals(instructor.getPerson().getPassword(), instructorFromDB.getPerson().getPassword());
    }

}
