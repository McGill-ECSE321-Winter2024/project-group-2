package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.CustomerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.OwnerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.PersonDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;

import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {
    @Mock
    private PersonRepository personDao;
    @Mock
    private InstructorRepository instructorDao;
    @Mock
    private CustomerRepository customerDao;
    @Mock
    private OwnerRepository ownerDao;

    @InjectMocks
    private AccountService accountService;
    
    private static final String PERSON_NAME = "TestPerson";
    private static final String PERSON_EMAIL = "TestEmail";
    private static final String PERSON_PASSWORD = "TestPassword";

    
    private List<String> personEmails = new ArrayList<String>();
    private List<Integer> customerIds = new ArrayList<Integer>();
    private List<Integer> personIds = new ArrayList<Integer>();
    
    @BeforeEach
    public void setMockOutput() {
        lenient().when(personDao.findByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(PERSON_EMAIL)) {
                Person person = new Person();
                person.setName(PERSON_NAME);
                person.setEmail(PERSON_EMAIL);
                person.setPassword(PERSON_PASSWORD);
                return person;
            } else {
                return null;
            }
        });
        lenient().when(personDao.existsByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
            return personEmails.contains(invocation.getArgument(0));
        });
        lenient().when(personDao.existsById(any(Integer.class))).thenAnswer( (InvocationOnMock invocation) -> {
            return personIds.contains(invocation.getArgument(0));
        });

        lenient().when(personDao.save(any(Person.class))).thenAnswer( (InvocationOnMock invocation) -> {
            personEmails.add(((Person)invocation.getArgument(0)).getEmail());
            personIds.add(((Person)invocation.getArgument(0)).getId());
            return invocation.getArgument(0);
        });

        lenient().when(customerDao.save(any(Customer.class))).thenAnswer( (InvocationOnMock invocation) -> {
            customerIds.addLast(((Customer)invocation.getArgument(0)).getId());
            return invocation.getArgument(0);
        });

        lenient().when(customerDao.existsById(any(Integer.class))).thenAnswer( (InvocationOnMock invocation) -> {
            return customerIds.contains(invocation.getArgument(0));
        });
    }

    public void testPerson(PersonDTO person){
        assertEquals(PERSON_NAME, person.getName());
        assertEquals(PERSON_EMAIL, person.getEmail());
        assertEquals(PERSON_PASSWORD, person.getPassword());
    }
    @Test
    public void createValidCustomer(){
        PersonDTO personDTO = null;
        String error = null;
        try {
            personDTO = accountService.createPerson(PERSON_PASSWORD, PERSON_EMAIL, PERSON_NAME);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(error);
        testPerson(personDTO);
    }
    @Test
    public void createInvalidNullCustomer(){
        String error = null;
        try {
            accountService.createPerson(null, null, null);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertEquals("Password, email, and name cannot be empty",error);
    }

    @Test
    public void loginValidPerson(){
        Person person = new Person();
        person.setEmail(PERSON_EMAIL);
        person.setPassword(PERSON_PASSWORD);
        person.setName(PERSON_NAME);
        personDao.save(person);

        int loggedIn = accountService.login(PERSON_EMAIL, PERSON_PASSWORD);
        assertEquals(true, loggedIn != -1);
    }
    
    @Test
    public void loginInvalidEmail(){
        int loggedIn = accountService.login("fake email", PERSON_PASSWORD);
        assertEquals(true, loggedIn == -1);
    }
    @Test
    public void loginInvalidPassword(){
        int loggedIn = accountService.login(PERSON_EMAIL, "fake password");
        assertEquals(true, loggedIn == -1);
    }

    @Test
    public void deletePersonValid() {
        
        Person person = new Person();
        person.setEmail(PERSON_EMAIL);
        person.setPassword(PERSON_PASSWORD);
        person.setName(PERSON_NAME);
        person = personDao.save(person);
        
        String error = null;
        try {
            accountService.deletePerson(person.getId());
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(error);
        
    }

    @Test
    public void deletePersonInvalid() {
        
        try {
            accountService.deletePerson(Integer.MAX_VALUE);
        } catch (Exception e) {
            assertEquals("There is no person with this ID", e.getMessage());
        }
    }

    @Test
    public void deleteCustomerAccountValid() {
        
        Person person = new Person();
        person.setEmail(PERSON_EMAIL);
        person.setPassword(PERSON_PASSWORD);
        person.setName(PERSON_NAME);
        person = personDao.save(person);

        Customer customer = new Customer();
        customer.setPerson(person);
        customer = customerDao.save(customer);
        
        String error = null;
        try {
            accountService.deleteCustomerAccount(customer.getId());
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(error);
        
    }
    @Test
    public void deleteCustomerAccountInvalid() {
        
        try {
            accountService.deleteCustomerAccount(Integer.MAX_VALUE);
        } catch (Exception e) {
            assertEquals("There is no customer with this ID", e.getMessage());
        }
    }

}
