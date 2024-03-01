package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.CustomerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.SessionRegistrationRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.SessionRepository;

@Service
public class SessionRegistrationService {
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    SessionRegistrationRepository sessionRegistrationRepository;
    
    @Transactional
    public void createAccount(){
        return;
    }
    
    @Transactional
    public void login(){
        return;
    }

    @Transactional
    public void viewSessions(){
        return;
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
