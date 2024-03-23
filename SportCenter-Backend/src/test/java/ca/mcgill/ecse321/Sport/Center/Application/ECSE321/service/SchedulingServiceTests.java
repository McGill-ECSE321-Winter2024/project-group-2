package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.ClassTypeRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.SessionRegistrationRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.SessionRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.ClassType;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Instructor;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;

import java.util.List;
import java.util.ArrayList;
import java.sql.Time;
import java.sql.Date;

@ExtendWith(MockitoExtension.class)
public class SchedulingServiceTests {
    @Mock
    private PersonRepository personDao;
    @Mock
    private InstructorRepository instructorDao;
    @Mock
    private ClassTypeRepository classTypeDao;
    @Mock
    private SessionRepository sessionDao;
    @Mock
    private SessionRegistrationRepository sessionRegistrationDao;
    @InjectMocks
    private SchedulingService schedulingService;
    
    private static final String PERSON_NAME = "TestPerson";
    private static final String PERSON_EMAIL = "TestEmail";
    private static final String PERSON_PASSWORD = "TestPassword";
    
    private static final Time START_TIME = Time.valueOf("12:00:00");
    private static final Time END_TIME = Time.valueOf("13:00:00");
    private static final Date DATE = Date.valueOf("2021-03-03");
    private static final ClassType CLASS_TYPE = new ClassType("running", true);
    
    private List<String> personEmails = new ArrayList<String>();
    private List<String> instructorEmails = new ArrayList<String>();
    private List<String> approvedClassTypes = new ArrayList<String>();
    private List<String> suggestedClassTypes = new ArrayList<String>();
    private List<Session> sessions = new ArrayList<Session>();

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
        
        lenient().when(sessionDao.save(any(Session.class))).thenAnswer( (InvocationOnMock invocation) -> {
            for (Session session : sessions) {
                if(session.getId() == ((Session)invocation.getArgument(0)).getId()) {
                    sessions.remove(session);
                    break;
                }
            }
            
            sessions.add((Session)invocation.getArgument(0));
            return invocation.getArgument(0);
        });
        lenient().when(sessionDao.findById(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
            for (Session session : sessions) {
                if(session.getId() == (int) invocation.getArgument(0)) {
                    return session;
                }
            }
            return null;
        });
        lenient().when(sessionDao.existsById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            for (Session session : sessions) {
                if(session.getId() == (int) invocation.getArgument(0)) {
                    return true;
                }
            }
            return false;
        });
        
        lenient().when(instructorDao.existsByPersonEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
            return instructorEmails.contains(invocation.getArgument(0));
        });

        lenient().when(personDao.save(any(Person.class))).thenAnswer( (InvocationOnMock invocation) -> {
            personEmails.add(((Person)invocation.getArgument(0)).getEmail());
            return invocation.getArgument(0);
        });
        lenient().when(personDao.existsByEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
            return personEmails.contains(invocation.getArgument(0));
        });
        lenient().when(instructorDao.save(any(Instructor.class))).thenAnswer( (InvocationOnMock invocation) -> {
            instructorEmails.add(((Instructor)invocation.getArgument(0)).getPerson().getEmail());
            return invocation.getArgument(0);
        });
        lenient().when(instructorDao.findByPersonEmail(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
            for (String email : instructorEmails) {
                if(email.equals(invocation.getArgument(0))) {
                    return new Instructor(new Person(1,PERSON_NAME, PERSON_EMAIL, PERSON_PASSWORD));
                }
            }
            return null;
        });
        lenient().when(classTypeDao.save(any(ClassType.class))).thenAnswer( (InvocationOnMock invocation) -> {
            ClassType type = (ClassType)invocation.getArgument(0);
            if(type.getIsApproved() ){
                approvedClassTypes.add(type.getClassType());
                suggestedClassTypes.remove(type.getClassType());
            } else {
                suggestedClassTypes.add(type.getClassType());
                approvedClassTypes.remove(type.getClassType());
            }
            return type;});
        lenient().when(classTypeDao.findByClassType(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            for (String type : approvedClassTypes) {
                if(type.equals(invocation.getArgument(0))) {
                    return new ClassType(type, true);
                }
            }
            for (String type : suggestedClassTypes) {
                if(type.equals(invocation.getArgument(0))) {
                    return new ClassType(type, false);
                }
            }
            return null;
        });
        lenient().when(classTypeDao.findByIsApproved(anyBoolean())).thenAnswer((InvocationOnMock invocation)->{
            boolean isApproved = (boolean) invocation.getArgument(0);
            if (isApproved) {
                return approvedClassTypes;
            }
            else {
                return suggestedClassTypes;
            }
        });
        
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };
    }

    
    @Test
    public void testCreateSession(){
        Person instructor = new Person(1,PERSON_NAME, PERSON_EMAIL, PERSON_PASSWORD);
        personDao.save(instructor);
        Instructor instructor1 = new Instructor(instructor);
        instructorDao.save(instructor1);
        Session session = null;
        String error = null;
        
        //Success scenario
        try {
            session = schedulingService.createSession(1,10, START_TIME, END_TIME,DATE, false, 100, CLASS_TYPE, instructor1);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(error);
        assertEquals(START_TIME, session.getStartTime());

        //Fail scenario: Start time after end time
        try {
            session = schedulingService.createSession(1,10, END_TIME, START_TIME,DATE, false, 100, CLASS_TYPE, instructor1);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNotNull(error);
        assertTrue(error.contains("Start time must be before end time"));

        //Fail scenario: Class type not approved
        ClassType invalidType = new ClassType("fake", false);
        try {
            session = schedulingService.createSession(1,10, START_TIME, END_TIME,DATE, false, 100, invalidType, instructor1);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNotNull(error);
        assertTrue(error.contains("Class type must be approved"));
    }

    @Test
    public void testUpdateSession(){
        Person instructor = new Person(1,PERSON_NAME, PERSON_EMAIL, PERSON_PASSWORD);
        Instructor instructor1 = new Instructor(instructor);
        Session session = new Session(1, 10, START_TIME, END_TIME, DATE, false, 100, CLASS_TYPE, instructor1);
        personDao.save(instructor);
        instructorDao.save(instructor1);
        sessionDao.save(session);

        String error = null;
        //Success Scenario
        try {
            schedulingService.updateSession(1,10, START_TIME, END_TIME,DATE, false, 10000, CLASS_TYPE, instructor1);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNull(error);
        assertEquals(10000, sessions.get(0).getMaxParticipants());

        //Fail scenario: Bad inputs -> Start time after end time, class type not approved
        ClassType unapprovedClassType = new ClassType("fake", false);
        try {
            schedulingService.updateSession(1,10, END_TIME, START_TIME,DATE, false, 10000, unapprovedClassType, instructor1);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNotNull(error);
        assertTrue(error.contains("Start time must be before end time"));
        assertTrue(error.contains("Class type must be approved"));
    } 
    
     
    @Test
    public void testDeleteSession(){
        Person instructorPerson = new Person(1,PERSON_NAME, PERSON_EMAIL, PERSON_PASSWORD);
        Instructor instructor = new Instructor(instructorPerson);
        Session session = new Session(1, 10, START_TIME, END_TIME, DATE, false, 100, CLASS_TYPE, instructor);
        personDao.save(instructorPerson);
        instructorDao.save(instructor);
        session = sessionDao.save(session);
        
        String error = null;

        //Success scenario (no errors)
        try{
            schedulingService.deleteSession(session.getId());
        }catch(Exception e){
            error = e.getMessage();
        }
        assertNull(error);
        
        //Fail scenario: Bad ID
        try{
            schedulingService.deleteSession(session.getId()+1);
        }catch(Exception e){
            error = e.getMessage();
        }
        assertEquals("No session with given ID", error);
    }


    @Test
    public void testApproveClassType(){
        String error = null;

        //Success scenario: No error thrown
        ClassType classType = new ClassType("Real", false);
        classTypeDao.save(classType);
        try{
            schedulingService.approveClassType("Real");
        }catch(Exception e){
            error=e.getMessage();
        }
        assertNull(error);
        assertTrue(approvedClassTypes.contains("Real"));

        //Fail scenario: Name doesnt exist
        try {
            schedulingService.approveClassType("fake");
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNotNull(error);
        assertEquals("No class type with given name", error);
    }

    @Test
    public void testRejectClassType(){
        String error = null;
        
        //Success scenario: No error thrown
        ClassType classType = new ClassType("Real", false);
        classTypeDao.save(classType);
        try{
            schedulingService.rejectClassType("Real");
        }catch(Exception e){
            error = e.getMessage();
        }
        assertNull(error);

        //Fail scenario: Name doesnt exist
        try {
            schedulingService.rejectClassType("fake");
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNotNull(error);
        assertEquals("No class type with given name", error);
    }

    @Test
    public void suggestClassTypeSuccess(){
        String error = null;
        
        //Success scenario: No error thrown
        try{
        schedulingService.suggestClassType("fake");
        }catch(Exception e){
            error = e.getMessage();
        }
        assertTrue(suggestedClassTypes.contains("fake"));
        assertNull(error);

        //Fail scenario: ClassType already suggested
        try {
            schedulingService.suggestClassType("fake");
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNotNull(error);
        assertEquals("Class type with given name already suggested", error);
    }

    @Test
    public void viewClassTypeByApproval(){
        ClassType classType = new ClassType("fake", false);
        classTypeDao.save(classType);
        List<ClassType> suggestedTypes = schedulingService.viewClassTypeByApproval(false);
        List<ClassType> approvedTypes = schedulingService.viewClassTypeByApproval(true);
        assertEquals(1, suggestedTypes.size());
        assertEquals(0, approvedTypes.size());
    }

    @Test
    public void registerToTeachSessionSuccess(){
        
        Person placeholder = new Person(10, "placeholder", "placeholder", "placeholder");
        personDao.save(placeholder);
        Instructor instructor2 = new Instructor(placeholder);
        instructorDao.save(instructor2);
        
        Person person = new Person(1,PERSON_NAME, PERSON_EMAIL, PERSON_PASSWORD);
        personDao.save(person);
        Instructor instructor = new Instructor(person);
        instructorDao.save(instructor);
        Session session = new Session(1, 10, START_TIME, END_TIME, DATE, false, 100, CLASS_TYPE, instructor2);
        sessionDao.save(session);
        schedulingService.registerToTeachSession(PERSON_EMAIL, 1);
        
        assertEquals(PERSON_EMAIL, sessions.get(0).getInstructor().getPerson().getEmail());
    }

    @Test
    public void registerToTeachSessionEmailNotPerson(){
        Person placeholder = new Person(1, "placeholder", "placeholder", "placeholder");
        personDao.save(placeholder);
        Instructor instructor2 = new Instructor(placeholder);
        instructorDao.save(instructor2);
        Session session = new Session(1, 10, START_TIME, END_TIME, DATE, false, 100, CLASS_TYPE, instructor2);
        sessionDao.save(session);
        String error = null;
        try {
            schedulingService.registerToTeachSession("bad email", 1);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNotNull(error);
        assertEquals("No person with given email", error);
    }

    @Test
    public void registerToTeachSessionEmailNotInstructor(){
        Person placeholder = new Person(1, "placeholder", "placeholder", "placeholder");
        personDao.save(placeholder);
        Instructor instructor2 = new Instructor(placeholder);
        instructorDao.save(instructor2);
        Person person = new Person(1,PERSON_NAME, PERSON_EMAIL, PERSON_PASSWORD);
        personDao.save(person);
        Session session = new Session(1, 10, START_TIME, END_TIME, DATE, false, 100, CLASS_TYPE, instructor2);
        sessionDao.save(session);
        String error = null;
        try {
            schedulingService.registerToTeachSession(PERSON_EMAIL, 1);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNotNull(error);
        assertEquals("No instructor with given email", error);
    }

    @Test
    public void registerToTeachSessionBadId(){
        String error = null;
        try {
            schedulingService.registerToTeachSession(PERSON_EMAIL, 1);
        } catch (Exception e) {
            error = e.getMessage();
        }
        assertNotNull(error);
        assertEquals("No session with given ID", error);
    }

}
