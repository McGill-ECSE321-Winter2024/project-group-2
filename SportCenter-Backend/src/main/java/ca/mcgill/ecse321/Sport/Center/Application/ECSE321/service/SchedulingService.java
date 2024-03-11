package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;


import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.ClassTypeRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.OwnerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.SessionRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.ClassType;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Instructor;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;

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
    public Session createSession(int id, int length, Time startTime, Time endTime, Date date, boolean isRepeating, int maxParticipants, ClassType classType, Instructor instructor){
        // I think this needs checks for validity + exceptions thrown
        Session session = new Session(id, length, startTime, endTime, date, isRepeating, maxParticipants, classType, instructor);
        return sessionRepository.save(session);
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
