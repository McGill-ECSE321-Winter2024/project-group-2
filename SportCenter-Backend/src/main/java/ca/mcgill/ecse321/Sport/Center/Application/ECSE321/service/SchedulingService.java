package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.util.List;


import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.ClassTypeRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.OwnerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.SessionRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.SessionRegistrationRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.ClassType;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Instructor;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.SessionRegistration;

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
    @Autowired
    SessionRegistrationRepository sessionRegistrationRepository;
    @Autowired
    PersonRepository personRepository;

    @Transactional
    public Session createSession(int id, int length, Time startTime, Time endTime, Date date, boolean isRepeating, int maxParticipants, ClassType classType, Instructor instructor){
        // I think this needs checks for validity + exceptions thrown
        Session session = new Session(id, length, startTime, endTime, date, isRepeating, maxParticipants, classType, instructor);
        return sessionRepository.save(session);
    }

    @Transactional
    public void updateSession(int sessionId, int length, Time startTime, Time endtime, Date date, boolean isRepeating, int maxParticipants, ClassType classType, Instructor instructor){ //maybe
        Session session = sessionRepository.getById(sessionId);
        session.setLength(length);
        session.setStartTime(startTime);
        session.setEndTime(endtime);
        session.setDate(date);
        session.setIsRepeating(isRepeating);
        session.setMaxParticipants(maxParticipants);
        session.setClassType(classType);
        session.setInstructor(instructor);
        sessionRepository.save(session);
        return;
    }
    @Transactional
    public void deleteSession(int id){
        List<SessionRegistration> sessionRegistrationsToDelete= sessionRegistrationRepository.findBySessionId(id);
        for (SessionRegistration registration : sessionRegistrationsToDelete){
            sessionRegistrationRepository.delete(registration);
        }
        sessionRepository.deleteById(id);
    }

    @Transactional
    public void approveClassType(String classTypeName){
        ClassType classType = classTypeRepository.getByClassType(classTypeName);
        classType.setIsApproved(true);
        classTypeRepository.save(classType);
        return;
    }
    @Transactional
    public void suggestClassType(String classTypeName){
        ClassType classType = new ClassType(classTypeName, false);
        classTypeRepository.save(classType);
        return;
    }
    @Transactional
    public void registerToTeachSession(String instructorName, int sessionId){
        Session targetSession = sessionRepository.getById(sessionId);
        
        Instructor targetInstructor = instructorRepository.getByPersonName(instructorName);
        targetSession.setInstructor(targetInstructor);

        sessionRepository.save(targetSession);
        return;
    }

}
