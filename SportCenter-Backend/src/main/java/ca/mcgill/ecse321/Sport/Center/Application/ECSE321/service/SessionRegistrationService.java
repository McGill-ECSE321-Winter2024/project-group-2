package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.CustomerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.SessionRegistrationRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.SessionRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;

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
    

    @Transactional
    public Iterable<Session> viewSessions(){
        return sessionRepository.findAll();
    }

    @Transactional
    public void registerForSession(){
        return;
    }
    
    @Transactional
    public void viewSpecificSession(){
        return;
    }

    @Transactional
    public void cancelRegistration(){
        return;
    }
}
