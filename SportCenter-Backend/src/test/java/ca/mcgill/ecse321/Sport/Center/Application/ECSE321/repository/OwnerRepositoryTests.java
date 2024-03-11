package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.OwnerRepository;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OwnerRepositoryTests {
    
    @Autowired
    private OwnerRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadOwner() {
        // Create owner
        int id = 0;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        Owner owner = new Owner(person);

        // Save in database
        owner = repo.save(owner);

        // Read back from database
        Owner ownerFromDB = repo.getOwnerByPersonName(name);

        // Assertions
        assertNotNull(ownerFromDB);
        assertEquals(owner.getPerson().getId(), ownerFromDB.getPerson().getId());
        assertEquals(owner.getPerson().getEmail(), ownerFromDB.getPerson().getEmail());
        assertEquals(owner.getPerson().getName(), ownerFromDB.getPerson().getName());
        assertEquals(owner.getPerson().getPassword(), ownerFromDB.getPerson().getPassword());
    }

}
