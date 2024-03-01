package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.repository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.CustomerRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerRepositoryTests {
    
    @Autowired
    private CustomerRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateAndReadCustomer() {
        //Create Customer
        int customerId = 1;
        Customer customer = new Customer(customerId);

        //Save in database
        customer = repo.save(customer);

        //Read back from database
        customerId = customer.getId();
        Customer customerFromDB = repo.getCustomerById(customerId);

        //Assertions
        assertNotNull(customerFromDB);
        assertEquals(customerId, customerFromDB.getId());
    }

}
