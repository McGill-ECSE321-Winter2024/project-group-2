package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.OwnerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OwnerRepositoryTests {
    
    @Autowired
    private OwnerRepository repo;
    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
        personRepository.deleteAll();;
    }

    @Test
    @Transactional
    public void testCreateAndReadOwner() {
        // Create owner
        int id = 0;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(name, email, password);
        person = personRepository.save(person);
        
        Owner owner = new Owner(person);

        // Save in database
        owner = repo.save(owner);

        // Read back from database
        Owner ownerFromDB = repo.getByPersonName(name);

        // Assertions
        assertNotNull(ownerFromDB);
        assertEquals(owner.getPerson().getId(), ownerFromDB.getPerson().getId());
        assertEquals(owner.getPerson().getEmail(), ownerFromDB.getPerson().getEmail());
        assertEquals(owner.getPerson().getName(), ownerFromDB.getPerson().getName());
        assertEquals(owner.getPerson().getPassword(), ownerFromDB.getPerson().getPassword());
    }

}
