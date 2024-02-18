package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.CustomerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class PersonRepositoryTests {
    
    @Autowired
    private PersonRepository repo;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadPerson() {
        //Create person
        int id = 1;
        String name = "Yuri";
        String password = "password";
        String email = "email";
        Customer role = new Customer();
        Person yuri = new Person(id, name, password, email, role);

        //Save in database
        customerRepository.save(role);
        yuri = repo.save(yuri);

        //Read back from database
        id = yuri.getPersonId();
        Person yuriFromDB = repo.getPersonByPersonId(id);

        //Assertions
        assertNotNull(yuriFromDB);
        assertEquals(yuri.getPersonId(), yuriFromDB.getPersonId());
        assertEquals(yuri.getEmail(), yuriFromDB.getEmail());
        assertEquals(yuri.getPassword(), yuriFromDB.getPassword());
        assertEquals(yuri.getName(), yuriFromDB.getName());
        assertEquals(yuri.getRole().getId(), yuriFromDB.getRole().getId());
    }

}
