package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.CustomerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        String name = "person";
        String password = "password";
        String email = "email";
        Customer role = new Customer();
        Person person = new Person(id, name, password, email, role);

        //Save in database
        customerRepository.save(role);
        person = repo.save(person);

        //Read back from database
        id = person.getId();
        Person personFromDB = repo.getPersonByPersonId(id);

        //Assertions
        assertNotNull(personFromDB);
        assertEquals(person.getId(), personFromDB.getId());
        assertEquals(person.getEmail(), personFromDB.getEmail());
        assertEquals(person.getPassword(), personFromDB.getPassword());
        assertEquals(person.getName(), personFromDB.getName());
        assertEquals(person.getRoles().get(0).getId(), personFromDB.getRoles().get(0).getId());
    }

}
