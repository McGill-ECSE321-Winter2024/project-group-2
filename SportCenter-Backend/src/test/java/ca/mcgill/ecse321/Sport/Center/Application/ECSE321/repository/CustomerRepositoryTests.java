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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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
    public void testCreateAndReadCustomerByEmailSuccessful() {
        //Create Customer
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Customer customer = new Customer(person);

        //Save in database
        customer = customerRepository.save(customer);

        //Read back from database
        Customer customerFromDB = customerRepository.findByPersonEmail(email);

        //Assertions
        assertNotNull(customerFromDB);
        assertEquals(customer.getPerson().getId(), customerFromDB.getPerson().getId());
        assertEquals(customer.getPerson().getEmail(), customerFromDB.getPerson().getEmail());
        assertEquals(customer.getPerson().getName(), customerFromDB.getPerson().getName());
        assertEquals(customer.getPerson().getPassword(), customerFromDB.getPerson().getPassword());
    }

    @Test
    public void testCreateAndReadCustomerByEmailUnsuccessful() {
        //Create Customer
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Customer customer = new Customer(person);

        //Save in database
        customer = customerRepository.save(customer);

        //Read back from database
        Customer customerFromDB = customerRepository.findByPersonEmail("nonExistentEmail");

        //Assertions
        assertNull(customerFromDB);
    }

    @Test
    public void testCreateAndReadCustomerByIDSuccessful() {
        //Create Customer
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Customer customer = new Customer(person);

        //Save in database
        customer = customerRepository.save(customer);

        //Read back from database
        Customer customerFromDB = customerRepository.findById(id);

        //Assertions
        assertNotNull(customerFromDB);
        assertEquals(customer.getPerson().getId(), customerFromDB.getPerson().getId());
        assertEquals(customer.getPerson().getEmail(), customerFromDB.getPerson().getEmail());
        assertEquals(customer.getPerson().getName(), customerFromDB.getPerson().getName());
        assertEquals(customer.getPerson().getPassword(), customerFromDB.getPerson().getPassword());
    }

    @Test
    public void testCreateAndReadCustomerByIdUnsuccessful() {
        //Create Customer
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Customer customer = new Customer(person);

        //Save in database
        customer = customerRepository.save(customer);

        //Read back from database
        Customer customerFromDB = customerRepository.findById(2);

        //Assertions
        assertNull(customerFromDB);
    }

    @Test
    public void testCreateAndReadCustomerByNameSuccessful() {
        //Create Customer
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Customer customer = new Customer(person);

        //Save in database
        customer = customerRepository.save(customer);

        //Read back from database
        Customer customerFromDB = customerRepository.findByPersonName(name);

        //Assertions
        assertNotNull(customerFromDB);
        assertEquals(customer.getPerson().getId(), customerFromDB.getPerson().getId());
        assertEquals(customer.getPerson().getEmail(), customerFromDB.getPerson().getEmail());
        assertEquals(customer.getPerson().getName(), customerFromDB.getPerson().getName());
        assertEquals(customer.getPerson().getPassword(), customerFromDB.getPerson().getPassword());
    }

    @Test
    public void testCreateAndReadCustomerByNameUnsuccessful() {
        //Create Customer
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Customer customer = new Customer(person);

        //Save in database
        customer = customerRepository.save(customer);

        //Read back from database
        Customer customerFromDB = customerRepository.findByPersonName("nonExistentName");

        //Assertions
        assertNull(customerFromDB);
    }

    @Test
    public void testCreateAndReadCustomers() {
        //Create Customer
        int id = 1;
        String name = "person";
        String email = "email";
        String password = "password";
        Person person = new Person(id, name, email, password);
        person = personRepository.save(person);
        Customer customer = new Customer(person);

        //Save in database
        customer = customerRepository.save(customer);

        int id2 = 2;
        String name2 = "person2";
        String email2 = "email2";
        String password2 = "password2";
        Person person2 = new Person(id2, name2, email2, password2);
        person2 = personRepository.save(person2);
        Customer customer2 = new Customer(person2);

        //Save in database
        customer2 = customerRepository.save(customer2);

        //Read back from database
        List<Customer> customersFromDB = customerRepository.findAllCustomers();

        //Assertions
        assertEquals(2, customersFromDB.size());
        assertEquals(customer.getPerson().getId(), customersFromDB.get(0).getPerson().getId());
        assertEquals(customer.getPerson().getEmail(), customersFromDB.get(0).getPerson().getEmail());
        assertEquals(customer.getPerson().getName(), customersFromDB.get(0).getPerson().getName());
        assertEquals(customer.getPerson().getPassword(), customersFromDB.get(0).getPerson().getPassword());
        assertEquals(customer2.getPerson().getId(), customersFromDB.get(1).getPerson().getId());
        assertEquals(customer2.getPerson().getEmail(), customersFromDB.get(1).getPerson().getEmail());
        assertEquals(customer2.getPerson().getName(), customersFromDB.get(1).getPerson().getName());
        assertEquals(customer2.getPerson().getPassword(), customersFromDB.get(1).getPerson().getPassword());
    }
}
