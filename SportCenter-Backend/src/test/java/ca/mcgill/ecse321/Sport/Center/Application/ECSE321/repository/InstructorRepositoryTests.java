package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class InstructorRepositoryTests {
    
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        instructorRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadInstructor() {
        //Create instructor
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(name, email, password);
        person = personRepository.save(person);
        Instructor instructor = new Instructor();
        instructor.setPerson(person);

        //Save in database
        instructor = instructorRepository.save(instructor);

        //Read back from database
        Instructor instructorFromDB = instructorRepository.getByPersonEmail(email);

        //Assertions
        assertNotNull(instructorFromDB);
        assertEquals(instructor.getPerson().getId(), instructorFromDB.getPerson().getId());
        assertEquals(instructor.getPerson().getEmail(), instructorFromDB.getPerson().getEmail());
        assertEquals(instructor.getPerson().getName(), instructorFromDB.getPerson().getName());
        assertEquals(instructor.getPerson().getPassword(), instructorFromDB.getPerson().getPassword());
    }

}
