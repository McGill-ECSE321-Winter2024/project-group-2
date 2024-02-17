package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.CustomerRepository;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerRepositoryTests {
    
    @Autowired
    private CustomerRepository repo;

    public CustomerRepositoryTests(CustomerRepository repo) {
        this.repo = repo;
    }

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadCustomer() {
        //Create Customer
        int bobId = 1;
        Customer bob = new Customer(bobId);

        //Save in database
        bob = repo.save(bob);

        //Read back from database
        Optional<Customer> bobFromDB = repo.findById(bobId);

        //Assertions
        assertNotNull(bobFromDB);
        assertEquals(bobId, bobFromDB.get().getId());
    }

}
