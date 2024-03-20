package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.CustomerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.SessionRegistrationRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.SessionRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.SessionRegistration;

import java.util.List;

@Service
public class SessionRegistrationService {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private SessionRegistrationRepository sessionRegistrationRepository;
    
    /**
     * 
     * @return
     * @author Aurelia Bouliane
     */
    @Transactional
    public Iterable<Session> viewSessions(){
        return sessionRepository.findAll();
    }
    
    /**
     * 
     * @param aId
     * @param aSession
     * @param aCustomer
     * @return
     * @author Alice, Aurelia
     */
    @Transactional
    public SessionRegistration registerForSession(int aId, int sessionId, int customerId){
        if(!sessionRepository.existsById(sessionId)){
            throw new IllegalArgumentException("No session with given ID");
        }
        if(!customerRepository.existsById(customerId)){
            throw new IllegalArgumentException("No customer with given ID");
        }
        Session aSession = sessionRepository.findById(sessionId);
        Customer aCustomer = customerRepository.findById(customerId);
        SessionRegistration sessionRegistration = new SessionRegistration(aSession, aCustomer);
        return sessionRegistrationRepository.save(sessionRegistration);
    }

    /**
     * 
     * @param pid
     * @return
     * @throws Exception
     * @author Alice Godbout
     */
    @Transactional
    public SessionRegistration viewSpecificSession(int pid) throws Exception {
        if(!sessionRegistrationRepository.existsById(pid)){
            throw new Exception("There is no registration with this ID.");
        }
        SessionRegistration s = sessionRegistrationRepository.findById(pid);
        
        return s;
    }
    
    /**
     * 
     * @param id
     * @author Aurelia, Alice
     */
    @Transactional
    public void cancelRegistration(int id){
        if (!sessionRegistrationRepository.existsById(id)) {
            throw new IllegalArgumentException("No registration with given ID");
        }
        sessionRegistrationRepository.deleteById(id);
    }

    /**
     * 
     * @param customerId
     * @return all registrations for a customer
     * @author Behrad
     */
    @Transactional
    public List<SessionRegistration> viewRegistrationsByCustomer(int customerId){
        if(!customerRepository.existsById(customerId)){
            throw new IllegalArgumentException("No customer with given ID");
        }
        List<SessionRegistration> allRegistrations = sessionRegistrationRepository.findAllByCustomerId(customerId);
        return allRegistrations;
    }

    /**
     * 
     * @param sessionId
     * @return all registrations for a session
     * @author Behrad
     */
    @Transactional
    public List<SessionRegistration> viewRegistrationsBySession(int sessionId){
        if(!sessionRepository.existsById(sessionId)){
            throw new IllegalArgumentException("No session with given ID");
        }
        List<SessionRegistration> allRegistrations = sessionRegistrationRepository.findBySessionId(sessionId);
        return allRegistrations;
    }
}
