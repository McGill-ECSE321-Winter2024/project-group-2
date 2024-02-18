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
        int ownerId = 1;
        Owner owner = new Owner(ownerId);

        // Save in database
        owner = repo.save(owner);

        // Read back from database
        ownerId = owner.getId();
        Owner ownerFromDB = repo.getOwnerById(ownerId);

        // Assertions
        assertNotNull(ownerFromDB);
        assertEquals(ownerId, ownerFromDB.getId());
    }

}
