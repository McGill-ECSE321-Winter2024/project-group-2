package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
