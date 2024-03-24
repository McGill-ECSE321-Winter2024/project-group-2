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

        lenient().when(personDao.existsById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if(invocation.getArgument(0).equals(PERSON_ID)) {
                return true;
            } else {
                return false;
            }
        });
        lenient().when(personDao.findById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
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
    public void testGrantInstructorPermissions(){
        Person person = new Person();
        person.setName(PERSON_NAME);
        person.setEmail(PERSON_EMAIL);
        person.setPassword(PERSON_PASSWORD);
        person.setId(PERSON_ID);

        String error = null;
        try{
            accountPermissionsService.grantInstructorPermissions(PERSON_ID);
        }catch(Exception e){
            error = e.getMessage();
        }
        assertNull(error);

        int fakeId = PERSON_ID+1;
        try{
            accountPermissionsService.grantInstructorPermissions(fakeId);
        }catch(Exception e){
            error = e.getMessage();
        }
        assertEquals(error, "Person does not exist");
        error = null;
        
        Instructor instructor = new Instructor(person);
        instructorDao.save(instructor);
        try{
            accountPermissionsService.grantInstructorPermissions(PERSON_ID);
        }catch(Exception e){
            error = e.getMessage();
        }
        assertEquals(error, "Person is already an instructor");
    }

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
    
    @Test
    public void failDemoteInstructorDoesNotExist(){
        String email = "FakeEmail";
        String error = null;
        try {
            accountPermissionsService.revokeInstructorPermissions(PERSON_ID+1);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertEquals( "Instructor does not exist", error);
    }

}
