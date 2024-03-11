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
    public SessionRegistration registerForSession(int aId, Session aSession, Customer aCustomer){
        SessionRegistration sessionRegistration = new SessionRegistration(aId, aSession, aCustomer);
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
        SessionRegistration s = sessionRegistrationRepository.getSessionRegistrationById(pid);
        if (s == null) {
            throw new Exception("There is no person with this ID.");
    } 
        return s;
    }
    
    /**
     * 
     * @param id
     * @author Aurelia, Alice
     */
    @Transactional
    public void cancelRegistration(int id){
        sessionRegistrationRepository.deleteById(id);
    }
}
