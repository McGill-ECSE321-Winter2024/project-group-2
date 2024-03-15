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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

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
    public void testCreateAndReadInstructorByEmailSuccessful() {
        //Create instructor
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Instructor instructor = new Instructor();
        instructor.setPerson(person);

        //Save in database
        instructor = instructorRepository.save(instructor);

        //Read back from database
        Instructor instructorFromDB = instructorRepository.findByPersonEmail(email);

        //Assertions
        assertNotNull(instructorFromDB);
        assertEquals(instructor.getPerson().getId(), instructorFromDB.getPerson().getId());
        assertEquals(instructor.getPerson().getEmail(), instructorFromDB.getPerson().getEmail());
        assertEquals(instructor.getPerson().getName(), instructorFromDB.getPerson().getName());
        assertEquals(instructor.getPerson().getPassword(), instructorFromDB.getPerson().getPassword());
    }

    @Test
    public void testCreateAndReadInstructorByEmailUnsuccessful() {
        //Create Instructor
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Instructor instructor = new Instructor(person);

        //Save in database
        instructor = instructorRepository.save(instructor);

        //Read back from database
        Instructor instructorFromDB = instructorRepository.findByPersonEmail("nonExistentEmail");

        //Assertions
        assertNull(instructorFromDB);
    }

    @Test
    public void testCreateAndReadInstructorByIDSuccessful() {
        //Create Instructor
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Instructor instructor = new Instructor(person);

        //Save in database
        instructor = instructorRepository.save(instructor);

        //Read back from database
        Instructor instructorFromDB = instructorRepository.findById(id);

        //Assertions
        assertNotNull(instructorFromDB);
        assertEquals(instructor.getPerson().getId(), instructorFromDB.getPerson().getId());
        assertEquals(instructor.getPerson().getEmail(), instructorFromDB.getPerson().getEmail());
        assertEquals(instructor.getPerson().getName(), instructorFromDB.getPerson().getName());
        assertEquals(instructor.getPerson().getPassword(), instructorFromDB.getPerson().getPassword());
    }

    @Test
    public void testCreateAndReadInstructorByIdUnsuccessful() {
        //Create Instructor
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Instructor instructor = new Instructor(person);

        //Save in database
        instructor = instructorRepository.save(instructor);

        //Read back from database
        Instructor instructorFromDB = instructorRepository.findById(2);

        //Assertions
        assertNull(instructorFromDB);
    }

    @Test
    public void testCreateAndReadInstructorByNameSuccessful() {
        //Create Instructor
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Instructor instructor = new Instructor(person);

        //Save in database
        instructor = instructorRepository.save(instructor);

        //Read back from database
        Instructor instructorFromDB = instructorRepository.findByPersonName(name);

        //Assertions
        assertNotNull(instructorFromDB);
        assertEquals(instructor.getPerson().getId(), instructorFromDB.getPerson().getId());
        assertEquals(instructor.getPerson().getEmail(), instructorFromDB.getPerson().getEmail());
        assertEquals(instructor.getPerson().getName(), instructorFromDB.getPerson().getName());
        assertEquals(instructor.getPerson().getPassword(), instructorFromDB.getPerson().getPassword());
    }

    @Test
    public void testCreateAndReadInstructorByNameUnsuccessful() {
        //Create Instructor
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Instructor instructor = new Instructor(person);

        //Save in database
        instructor = instructorRepository.save(instructor);

        //Read back from database
        Instructor instructorFromDB = instructorRepository.findByPersonName("nonExistentName");

        //Assertions
        assertNull(instructorFromDB);
    }

    @Test
    public void testCreateAndReadInstructors() {
        //Create Instructor
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Instructor instructor = new Instructor(person);

        //Save in database
        instructor = instructorRepository.save(instructor);

        int id2 = 2;
        String name2 = "person2";
        String email2 = "email2";
        String password2 = "password2";
        Person person2 = new Person(id2, name2, email2, password2);
        person2 = personRepository.save(person2);
        Instructor instructor2 = new Instructor(person2);

        //Save in database
        instructor2 = instructorRepository.save(instructor2);

        //Read back from database
        List<Instructor> instructorsFromDB = instructorRepository.findAllInstructors();

        //Assertions
        assertEquals(2, instructorsFromDB.size());
        assertEquals(instructor.getPerson().getId(), instructorsFromDB.get(0).getPerson().getId());
        assertEquals(instructor.getPerson().getEmail(), instructorsFromDB.get(0).getPerson().getEmail());
        assertEquals(instructor.getPerson().getName(), instructorsFromDB.get(0).getPerson().getName());
        assertEquals(instructor.getPerson().getPassword(), instructorsFromDB.get(0).getPerson().getPassword());
        assertEquals(instructor2.getPerson().getId(), instructorsFromDB.get(1).getPerson().getId());
        assertEquals(instructor2.getPerson().getEmail(), instructorsFromDB.get(1).getPerson().getEmail());
        assertEquals(instructor2.getPerson().getName(), instructorsFromDB.get(1).getPerson().getName());
        assertEquals(instructor2.getPerson().getPassword(), instructorsFromDB.get(1).getPerson().getPassword());
    }

    @Test
    public void testCreateAndExistsInstructorTrue() {
        //Create instructor
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Instructor instructor = new Instructor();
        instructor.setPerson(person);

        //Save in database
        instructor = instructorRepository.save(instructor);

        //Read back from database
        boolean instructorExists = instructorRepository.existsByPersonEmail(email);

        //Assertions
        assertTrue(instructorExists);
    }

    @Test
    public void testCreateAndExistsInstructorFalse() {
        //Create Instructor
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Instructor instructor = new Instructor(person);

        //Save in database
        instructor = instructorRepository.save(instructor);

        //Read back from database
        boolean instructorExists = instructorRepository.existsByPersonEmail(email);

        //Assertions
        assertFalse(instructorExists);
    }

    @Test
    public void testCreateAndDeleteInstructor(){
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Instructor instructor = new Instructor(person);

        //Save in database
        instructor = instructorRepository.save(instructor);
        instructorRepository.deleteByPersonEmail(email);
        boolean instructorExists = instructorRepository.existsByPersonEmail(email);

        //Assertions
        assertFalse(instructorExists);
    }

}
