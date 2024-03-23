package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.CustomerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.CustomerDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.PersonDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;

import java.util.List;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {
    @Mock
    private PersonRepository personDao;
    @Mock
    private InstructorRepository instructorDao;
    @Mock
    private CustomerRepository customerDao;
    @InjectMocks
    private AccountService accountService;
    
    private static final String PERSON_NAME = "TestPerson";
    private static final String PERSON_EMAIL = "TestEmail";
    private static final String PERSON_PASSWORD = "TestPassword";
    
    private List<String> personEmails = new ArrayList<String>();
    private List<Person> people = new ArrayList<Person>();
    
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

        lenient().when(personDao.save(any(Person.class))).thenAnswer( (InvocationOnMock invocation) -> {
            personEmails.add(((Person)invocation.getArgument(0)).getEmail());
            people.add(invocation.getArgument(0));
            return invocation.getArgument(0);
        });

        lenient().when(personDao.findById(anyInt())).thenAnswer((InvocationOnMock invocation)-> {
            for(Person person: people){
                if(person.getId() == (int) invocation.getArgument(0)){
                    return person;
                }
            }
            return null;
        });
    }
    @BeforeEach
    public void clearRepos(){
        personEmails.clear();
    }

    public void testPerson(Person person){
        assertEquals(PERSON_NAME, person.getName());
        assertEquals(PERSON_EMAIL, person.getEmail());
        assertEquals(PERSON_PASSWORD, person.getPassword());
    }

    @Test
    public void testCreatePerson(){
        Person person = null;
        String error = null;

        //Success scenario
        try {
            person = accountService.createPerson(PERSON_PASSWORD, PERSON_EMAIL, PERSON_NAME);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(error);
        testPerson(person);

        //Fail scenario: null inputs
        try{
            person = accountService.createPerson(null, null, null);
        }catch(Exception e){
            error = e.getMessage();
        }
        assertEquals("Password, email, and name cannot be empty",error);
    }

    @Test
    public void findById(){
        Person person = new Person();
        person.setEmail(PERSON_EMAIL);
        person.setPassword(PERSON_PASSWORD);
        person.setName(PERSON_NAME);
        person.setId(0);
        personDao.save(person);

        String error = null;
        
        //Success scenario
        PersonDTO foundPerson = null;
        try{
            foundPerson = accountService.findPersonById(person.getId());
        }catch(Exception e){
            error = e.getMessage();
        }
        assertNull(error);
        assertEquals(foundPerson.getEmail(),person.getEmail());

        //Fail scenario: Wrong ID
        foundPerson = null;
        try{
            foundPerson = accountService.findPersonById(-1);
        }catch(Exception e){
            error = e.getMessage();
        }
        assertNull(foundPerson);
        assertNotNull(error);
        assertEquals("There is no person with this ID", error);
    }

    @Test
    public void createCustomerAccount(){
        Person person = new Person();
        person.setEmail(PERSON_EMAIL);
        person.setPassword(PERSON_PASSWORD);
        person.setName(PERSON_NAME);
        person.setId(0);
        personDao.save(person);

        String error = null;
        CustomerDTO customer = null;

        //Success scenario
        try{
            customer = accountService.createCustomerAccount(PERSON_PASSWORD, PERSON_EMAIL, PERSON_NAME);
        }catch(Exception e){
            error = e.getMessage();
        }
        assertNull(error);
        assertNotNull(customer);
        assertTrue(personEmails.contains(PERSON_EMAIL));
        

        //Fail scenario: null inputs
        try{
            customer = accountService.createCustomerAccount(null, null, null);
        }catch(Exception e){
            error = e.getMessage();
        }
        assertNotNull(error);
        assertEquals("Password, email, and name cannot be empty", error);
    }

    @Test
    public void testLogin(){
        Person person = new Person();
        person.setEmail(PERSON_EMAIL);
        person.setPassword(PERSON_PASSWORD);
        person.setName(PERSON_NAME);
        personDao.save(person);

        //Success scenario
        boolean loggedIn = accountService.login(PERSON_EMAIL, PERSON_PASSWORD);
        assertEquals(true, loggedIn);

        //Fail scenario: Wrong password
        loggedIn = accountService.login(PERSON_EMAIL, "fake password");
        assertEquals(false, loggedIn);

        //Fail scenario: Wrong email
        loggedIn=true;
        loggedIn = accountService.login("fake email", PERSON_PASSWORD);
        assertEquals(false, loggedIn);
    }
}
