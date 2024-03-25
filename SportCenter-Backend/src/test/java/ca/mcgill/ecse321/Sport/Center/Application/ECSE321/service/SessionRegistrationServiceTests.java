package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.*;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
public class SessionRegistrationServiceTests {
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private SessionRegistrationRepository sessionRegistrationRepository;
    
    @InjectMocks
    private SessionRegistrationService SessionRegistrationService;

    private List<Session> sessionList = new ArrayList<>();
    private List<SessionRegistration> SessionRegistrationList = new ArrayList<>();
    private List<Customer> customerList = new ArrayList<>();

    @BeforeEach
    public void setMockOutput(){
        lenient().when(sessionRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            return sessionList;
        });
        lenient().when(sessionRepository.findById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            for(Session session : sessionList){
                if(session.getId() == (int)invocation.getArgument(0)){
                    return session;
                }
            }
            return null;
        });
        lenient().when(customerRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            return customerList;
        });
        lenient().when(customerRepository.findById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            for(Customer customer : customerList){
                if(customer.getId() == (int)invocation.getArgument(0)){
                    return customer;
                }
            }
            return null;
        });
        lenient().when(sessionRegistrationRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            return SessionRegistrationList;
        });
        lenient().when(sessionRegistrationRepository.findBySessionId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            List<SessionRegistration> SessionRegistrations = new ArrayList<>();
            for(SessionRegistration SessionRegistration : SessionRegistrationList){
                if(SessionRegistration.getSession().getId() == (int)invocation.getArgument(0)){
                    SessionRegistrations.add(SessionRegistration);
                }
            }
            return SessionRegistrations;
        });
        lenient().when(sessionRegistrationRepository.findAllByCustomerId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            List<SessionRegistration> SessionRegistrations = new ArrayList<>();
            for(SessionRegistration SessionRegistration : SessionRegistrationList){
                if(SessionRegistration.getCustomer().getId() == (int)invocation.getArgument(0)){
                    SessionRegistrations.add(SessionRegistration);
                }
            }
            return SessionRegistrations;
        });
        lenient().when(sessionRegistrationRepository.findById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            for(SessionRegistration SessionRegistration : SessionRegistrationList){
                if(SessionRegistration.getId() == (int)invocation.getArgument(0)){
                    return SessionRegistration;
                }
            }
            return null;
        });
        lenient().when(sessionRegistrationRepository.save(any(SessionRegistration.class))).thenAnswer((InvocationOnMock invocation) -> {
            SessionRegistrationList.add(invocation.getArgument(0));
            return invocation.getArgument(0);
        });
        lenient().when(sessionRepository.existsById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            for(Session session : sessionList){
                if(session.getId() == (int)invocation.getArgument(0)){
                    return true;
                }
            }
            return false;
        });
        lenient().when(customerRepository.existsById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            for(Customer customer : customerList){
                if(customer.getId() == (int)invocation.getArgument(0)){
                    return true;
                }
            }
            return false;
        });
        lenient().when(sessionRegistrationRepository.existsById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            for(SessionRegistration registration : SessionRegistrationList){
                if(registration.getId() == (int)invocation.getArgument(0)){
                    return true;
                }
            }
            return false;
        });
        lenient().when(sessionRegistrationRepository.save(any(SessionRegistration.class))).thenAnswer((InvocationOnMock invocation) -> {
            SessionRegistrationList.add(invocation.getArgument(0));
            return invocation.getArgument(0);
        });
    }
    @BeforeEach
    public void clearRepos(){
        //Clean repo's memory
        sessionList.clear();
        SessionRegistrationList.clear();
        customerList.clear();

        //Create mock session and customer
        sessionList.add(new Session(10,Time.valueOf("10:00:00"),Time.valueOf("11:00:00"),Date.valueOf("2020-02-02"),false,10,new ClassType("Type",true),new Instructor()));

        Person person = new Person();
        person.setEmail("testEmail");
        person.setName("fullName");
        person.setPassword("passino");
        Customer customer = new Customer(person);
        customerList.add(customer);
    }
    

    @Test
    public void testViewRegistrationBySessionIdValidAndInvalid(){
        int successfulSessionId = sessionList.getFirst().getId();
        int unsuccessfulSessionId = sessionList.getFirst().getId() + 1;

        SessionRegistration exampleRegistration = new SessionRegistration(sessionList.get(0),customerList.get(0));
        sessionRegistrationRepository.save(exampleRegistration);

        String error = "";
        List<SessionRegistration> allRegistrations = new ArrayList<>();
        try{
            allRegistrations = SessionRegistrationService.viewRegistrationsBySession(successfulSessionId);
        }catch(Exception e){
            error+=e.getMessage();
        }
        assertTrue(allRegistrations.size() == 1);
        assertTrue(error.length() == 0);
        allRegistrations.clear();
        try{
            allRegistrations = SessionRegistrationService.viewRegistrationsBySession(unsuccessfulSessionId);
        }catch(Exception e){
            error+=e.getMessage();
        }
        assertTrue(allRegistrations.size() == 0);
        assertTrue(error.contains("No session with given ID"));
        
    }

    @Test
    public void testViewRegistrationByCustomerIdValidAndInvalid(){
        int successfulCustomerId = customerList.getFirst().getId();
        int unsuccessfulCustomerId = customerList.getFirst().getId() + 1;

        SessionRegistration exampleRegistration = new SessionRegistration(sessionList.get(0),customerList.get(0));
        sessionRegistrationRepository.save(exampleRegistration);

        String error = "";
        List<SessionRegistration> allRegistrations = new ArrayList<>();
        try{
            allRegistrations = SessionRegistrationService.viewRegistrationsByCustomer(successfulCustomerId);
        }catch(Exception e){
            error+=e.getMessage();
        }
        assertTrue(allRegistrations.size() == 1);
        assertTrue(error.length()==0);
        allRegistrations.clear();
        try{
            allRegistrations = SessionRegistrationService.viewRegistrationsByCustomer(unsuccessfulCustomerId);
        }catch(Exception e){
            error+=e.getMessage();
        }
        assertTrue(allRegistrations.size()==0);
        assertTrue(error.contains("No customer with given ID"));
    }

    @Test
    public void testCancelRegistrationValidAndInvalid(){
        String error = "";
        
        //Success scenario
        SessionRegistration registration = new SessionRegistration(sessionList.getLast(), customerList.getLast());
        sessionRegistrationRepository.save(registration);
        try{
            SessionRegistrationService.cancelRegistration(registration.getId());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        assertEquals(error, ""); //no error encountered
        
        //Fail scenario: Test for bad id
        int fakeRegistrationId = 100;
        try{
            SessionRegistrationService.cancelRegistration(fakeRegistrationId);
        }catch(Exception e){
            error+=e.getMessage();
        }
        assertTrue(error.contains("No registration with given ID"));
    }

    @Test
    public void testViewSpecificSessionValidAndInvalid(){
        int successfulId = sessionList.getFirst().getId();
        int unsuccessfulId = sessionList.getFirst().getId() + 1;

        SessionRegistration exampleRegistration = new SessionRegistration(sessionList.get(0),customerList.get(0));
        sessionRegistrationRepository.save(exampleRegistration);

        SessionRegistration targetRegistration = null;
        String error = "";
        
        //Success scenario
        try{
            targetRegistration = SessionRegistrationService.viewSpecificSession(successfulId);
        }catch(Exception e){
            error+=e.getMessage();
        }
        assertNotNull(targetRegistration);
        assertEquals(error, ""); // no error occured
        
        //Fail scenario: Test for bad ID
        targetRegistration = null;
        try{
            targetRegistration = SessionRegistrationService.viewSpecificSession(unsuccessfulId);
        }catch(Exception e){
            error+=e.getMessage();
        }
        assertNull(targetRegistration);
        assertTrue(error.contains("No registration with given ID"));
    }
    
    @Test
    public void testRegisterForSessionValidAndInvalid(){
        int successfulSessionId = sessionList.getFirst().getId();
        int successfulCustomerId = customerList.getFirst().getId();
        
        int unsuccessfulSessionId = sessionList.getFirst().getId() + 1;
        int unsuccessfulCustomerId = customerList.getFirst().getId() + 1;


        String error = "";
        SessionRegistration exampleRegistration = null;
        //Success scenario
        try{
            exampleRegistration = SessionRegistrationService.registerForSession(successfulSessionId, successfulCustomerId);
        }catch(Exception e){
            error+=e.getMessage();
        }
        assertNotNull(exampleRegistration);
        assertEquals(error, "");
        
        //Fail scenario: Test for bad session ID
        exampleRegistration = null;
        try{
            exampleRegistration = SessionRegistrationService.registerForSession(unsuccessfulSessionId, successfulCustomerId);
        }catch(Exception e){
            error+=e.getMessage();
        }
        assertNull(exampleRegistration);
        assertTrue(error.contains("No session with given ID"));
        
        //Fail scenario: Test for bad customer id
        exampleRegistration = null;
        try{
            exampleRegistration = SessionRegistrationService.registerForSession(successfulSessionId, unsuccessfulCustomerId);
        }catch(Exception e){
            error+=e.getMessage();
        }
        assertNull(exampleRegistration);
        assertTrue(error.contains("No customer with given ID"));
    }
}
