package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.ClassTypeRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.OwnerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.SessionRepository;

@Service
public class SchedulingService {
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    ClassTypeRepository classTypeRepository;

    @Transactional
    public void createSession(){
        return;
    }
    @Transactional
    public void updateSession(){ //maybe
        return;
    }
    @Transactional
    public void deleteSession(){
        return;
    }
    @Transactional
    public void approveClassType(){
        return;
    }
    @Transactional
    public void suggestClassType(){
        return;
    }
    @Transactional
    public void registerToTeachSession(){
        return;
    }

}
