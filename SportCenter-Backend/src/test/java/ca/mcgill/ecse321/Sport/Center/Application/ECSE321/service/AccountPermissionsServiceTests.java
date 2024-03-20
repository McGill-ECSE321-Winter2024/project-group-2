package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;


import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;
import java.util.List;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class AccountPermissionsServiceTests {
    @Mock
    private PersonRepository personDao;
    @Mock
    private InstructorRepository instructorDao;
    @InjectMocks
    private AccountPermissionsService accountPermissionsService;
    
    private static final String PERSON_NAME = "TestPerson";
    private static final String PERSON_EMAIL = "TestEmail";
    private static final String PERSON_PASSWORD = "TestPassword";
    private static final int PERSON_ID = 1;
    
    private List<String> instructorEmails = new ArrayList<String>();
    private List<Integer> instructorIds = new ArrayList<Integer>();
    private List<Person> people = new ArrayList<>();
    
    @BeforeEach
    public void setMockOutput() {
        /*lenient().when(personDao.findByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(PERSON_EMAIL)) {
                Person person = new Person();
                person.setName(PERSON_NAME);
                person.setEmail(PERSON_EMAIL);
                person.setPassword(PERSON_PASSWORD);
                return person;
            } else {
                return null;
            }
        });*/
        /*lenient().when(personDao.existsByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(PERSON_EMAIL)) {
                return true;
            } else {
                return false;
            }
        });*/
        lenient().when(personDao.existsById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(PERSON_ID)) {
                return true;
            } else {
                return false;
            }
        });
        lenient().when(personDao.findById(PERSON_ID)).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(PERSON_ID)) {
                Person person = new Person();
                person.setName(PERSON_NAME);
                person.setEmail(PERSON_EMAIL);
                person.setPassword(PERSON_PASSWORD);
                return person;
            } else {
                return null;
            }
        });

        lenient().when(instructorDao.existsByPersonEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
            return instructorEmails.contains(invocation.getArgument(0));
        });
        lenient().when(instructorDao.existsById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            return instructorIds.contains(invocation.getArgument(0));
        });
        lenient().when(instructorDao.save(any(Instructor.class))).thenAnswer( (InvocationOnMock invocation) -> {
            instructorEmails.add(((Instructor)invocation.getArgument(0)).getPerson().getEmail());
            instructorIds.addLast(((Instructor)invocation.getArgument(0)).getId());
            return invocation.getArgument(0);
        });

        
        lenient().when(personDao.save(any(Person.class))).thenAnswer((InvocationOnMock invocation) -> {
            people.add(invocation.getArgument(0));
            return invocation.getArgument(0);
        });
    }

    @Test
    public void testPromoteValidPerson(){
        int personId = PERSON_ID;

        String error = null;
        try {
            accountPermissionsService.grantInstructorPermissions(personId);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(error);
    }

    @Test
    public void testPromotePersonDoesNotExist(){
        int personId = 2;
        String error = null;
        try {
            accountPermissionsService.grantInstructorPermissions(personId);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertEquals(error, "Person does not exist");
    }
    @Test
    public void testPromotePersonAlreadyInstructor(){
        String name = "FakePerson";
        String email = "FakeEmail";
        String password = "FakePassword";
        
        Person person = new Person();
        person.setName(name);
        person.setEmail(email);
        person.setPassword(password);
        person.setId(PERSON_ID);
        person = personDao.save(person);
        
        Instructor instructor = new Instructor();
        instructor.setPerson(person);
        instructor = instructorDao.save(instructor);
        
        when(personDao.existsById(person.getId())).thenReturn(true);
        when(instructorDao.existsByPersonEmail(email)).thenReturn(instructorEmails.contains(email));
        String error = null;
        try {
            accountPermissionsService.grantInstructorPermissions(person.getId());
        } catch (Exception e) {
            error = e.getMessage();
            
        }
        assertEquals("Person already has instructor permissions", error);
    }

    /*@Test
    public void failPromoteNull(){
        String email = null;
        String error = null;
        try {
            accountPermissionsService.grantInstructorPermissions(email);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(error, "Person email cannot be empty");
    }*/
    @Test
    public void demoteValidInstructor(){
        Person person = new Person();
        person.setName(PERSON_NAME);
        person.setEmail(PERSON_EMAIL);
        person.setPassword(PERSON_PASSWORD);

        Instructor instructor = new Instructor();
        instructor.setPerson(person);
        instructor.setId(PERSON_ID);
        instructor = instructorDao.save(instructor);

        String error = null;
        try {
            accountPermissionsService.revokeInstructorPermissions(instructor.getId());
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(error);
    }
    
    /*@Test
    public void failDemoteNull(){
        String email = null;
        String error = null;
        try {
            accountPermissionsService.revokeInstructorPermissions(email);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertEquals(error, "Person email cannot be empty");
    }*/

    
    public void failDemoteInstructorDoesNotExist(){
        String email = "FakeEmail";
        String error = null;
        try {
            accountPermissionsService.revokeInstructorPermissions(PERSON_ID+1);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertEquals(error, "Person does not have instructor permissions");
    }

}
