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
        int id = 0;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        Customer customer = new Customer(person);

        //Save in database
        customer = repo.save(customer);

        //Read back from database
        Customer customerFromDB = repo.getCustomerByPersonEmail(email);

        //Assertions
        assertNotNull(customerFromDB);
        assertEquals(customer.getPerson().getId(), customerFromDB.getPerson().getId());
        assertEquals(customer.getPerson().getEmail(), customerFromDB.getPerson().getEmail());
        assertEquals(customer.getPerson().getName(), customerFromDB.getPerson().getName());
        assertEquals(customer.getPerson().getPassword(), customerFromDB.getPerson().getPassword());
    }

}
