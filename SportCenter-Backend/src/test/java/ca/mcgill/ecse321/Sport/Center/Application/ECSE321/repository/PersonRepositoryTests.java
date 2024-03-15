package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class PersonRepositoryTests {
    
    @Autowired
    private PersonRepository repo;


    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadPerson() {
        //Create person
        String name = "person";
        String password = "password";
        String email = "email";
        Person person = new Person();
        person.setEmail(email);
        person.setName(name);
        person.setPassword(password);

        //Save in database
        person = repo.save(person);

        //Read back from database
        //id = person.getId();
        String dbEmail = person.getEmail();
        String dbPassword = person.getPassword();
        String dbName = person.getName();
        //Person personFromDB = repo.findByName(name);

        //Assertions
        assertNotNull(person);
        //assertEquals(person.getId(), id);
        assertEquals(dbEmail, email);
        assertEquals(dbPassword, password);
        assertEquals(dbName, name);
    }

    @Test
    public void testCreateAndReadPersonByEmailSuccessful() {
        //Create person
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = repo.save(person);

        //Read back from database
        Person personFromDB = repo.findByEmail(email);

        //Assertions
        assertNotNull(personFromDB);
        assertEquals(person.getId(), personFromDB.getId());
        assertEquals(person.getEmail(), personFromDB.getEmail());
        assertEquals(person.getName(), personFromDB.getName());
        assertEquals(person.getPassword(), personFromDB.getPassword());
    }

    @Test
    public void testCreateAndReadPersonByEmailUnsuccessful() {
        //Create person
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = repo.save(person);

        //Read back from database
        Person personFromDB = repo.findByEmail("nonExistentEmail");

        //Assertions
        assertNull(personFromDB);
    }

    @Test
    public void testCreateAndReadPersonByIdSuccessful() {
        //Create person
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = repo.save(person);

        //Read back from database
        Person personFromDB = repo.findById(id);

        //Assertions
        assertNotNull(personFromDB);
        assertEquals(person.getId(), personFromDB.getId());
        assertEquals(person.getEmail(), personFromDB.getEmail());
        assertEquals(person.getName(), personFromDB.getName());
        assertEquals(person.getPassword(), personFromDB.getPassword());
    }

    @Test
    public void testCreateAndReadPersonByIdUnsuccessful() {
        //Create person
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = repo.save(person);

        //Read back from database
        Person personFromDB = repo.findById(id);

        //Assertions
        assertNull(personFromDB);
    }

    @Test
    public void testCreateAndReadPersonByNameSuccessful() {
        //Create person
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = repo.save(person);

        //Read back from database
        Person personFromDB = repo.findByName(name);

        //Assertions
        assertNotNull(personFromDB);
        assertEquals(person.getId(), personFromDB.getId());
        assertEquals(person.getEmail(), personFromDB.getEmail());
        assertEquals(person.getName(), personFromDB.getName());
        assertEquals(person.getPassword(), personFromDB.getPassword());
    }

    @Test
    public void testCreateAndReadPersonByNameUnsuccessful() {
        //Create person
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = repo.save(person);

        //Read back from database
        Person personFromDB = repo.findByName(name);

        //Assertions
        assertNull(personFromDB);
    }

    @Test
    public void testCreateAndExistsPersonByEmailTrue() {
        //Create person
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = repo.save(person);

        //Read back from database
        boolean personFromDB = repo.existsByEmail(email);

        //Assertions
        assertTrue(personFromDB);
    }

    @Test
    public void testCreateAndExistsPersonByEmailFalse() {
        //Create Customer
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = repo.save(person);

        //Read back from database
        boolean personFromDB = repo.existsByEmail("nonExistentEmail");

        //Assertions
        assertFalse(personFromDB);
    }

}
