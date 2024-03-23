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
public class CustomerRepositoryTests {
    
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        customerRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadCustomer() {
        //Create Customer
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(name, email, password);
        person = personRepository.save(person);
        Customer customer = new Customer(person);

        //Save in database
        customer = customerRepository.save(customer);

        //Read back from database
        Customer customerFromDB = customerRepository.getByPersonEmail(email);

        //Assertions
        assertNotNull(customerFromDB);
        assertEquals(customer.getPerson().getId(), customerFromDB.getPerson().getId());
        assertEquals(customer.getPerson().getEmail(), customerFromDB.getPerson().getEmail());
        assertEquals(customer.getPerson().getName(), customerFromDB.getPerson().getName());
        assertEquals(customer.getPerson().getPassword(), customerFromDB.getPerson().getPassword());
    }

}
