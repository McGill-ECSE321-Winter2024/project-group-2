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
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
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
    @InjectMocks
    private AccountService accountService;
    
    private static final String PERSON_NAME = "TestPerson";
    private static final String PERSON_EMAIL = "TestEmail";
    private static final String PERSON_PASSWORD = "TestPassword";
    
    private List<String> personEmails = new ArrayList<String>();
    
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
            return invocation.getArgument(0);
        });

        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
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
    public void createNullCustomer(){
        PersonDTO personDTO = null;
        String error = null;
        try {
            personDTO = accountService.createPerson(null, null, null);
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

        boolean loggedIn = accountService.login(PERSON_EMAIL, PERSON_PASSWORD);
        assertEquals(true, loggedIn);
    }
    
    @Test
    public void loginInvalidEmail(){
        boolean loggedIn = accountService.login("fake email", PERSON_PASSWORD);
        assertEquals(false, loggedIn);
    }
    @Test
    public void loginInvalidPassword(){
        boolean loggedIn = accountService.login(PERSON_EMAIL, "fake password");
        assertEquals(false, loggedIn);
    }

}
