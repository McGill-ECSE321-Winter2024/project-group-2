package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.CrudRepository;

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
        String error = "";
        if(startTime.after(endTime)){
            error += "Start time must be before end time";
        }
        if(!classType.getIsApproved()){
            error += "Class type must be approved";
        }
        if(error!=""){
            throw new IllegalArgumentException(error);
        }
        
        // I think this needs checks for validity + exceptions thrown
        Session session = new Session(id, length, startTime, endTime, date, isRepeating, maxParticipants, classType, instructor);
        return sessionRepository.save(session);
    }

    /**
     *Updates an existing session in the database

     * @param sessionId
     * @param length
     * @param startTime
     * @param endtime
     * @param date
     * @param isRepeating
     * @param maxParticipants
     * @param classType
     * @param instructor
     * @author Behrad
     */
    @Transactional
    public void updateSession(int sessionId, int length, Time startTime, Time endTime, Date date, boolean isRepeating, int maxParticipants, ClassType classType, Instructor instructor){ //maybe
        String error = "";
        if(startTime.after(endTime)){
            error += "Start time must be before end time";
        }
        if(!classType.getIsApproved()){
            error += "Class type must be approved";
        }
        

        Session session = sessionRepository.findById(sessionId);
        if(session==null){
            error+="No session with given ID";
        }
        if(error!=""){
            throw new IllegalArgumentException(error);
        }
        
        session.setLength(length);
        session.setStartTime(startTime);
        session.setEndTime(endTime);
        session.setDate(date);
        session.setIsRepeating(isRepeating);
        session.setMaxParticipants(maxParticipants);
        session.setClassType(classType);
        session.setInstructor(instructor);
        sessionRepository.save(session);
        return;
    }
    @Transactional
    public void deleteSession(int sessionId){
        if(!sessionRepository.existsById(sessionId)){
            throw new IllegalArgumentException("No session with given ID");
        }
        
        sessionRegistrationRepository.deleteBySessionId(sessionId);
        sessionRepository.deleteById(sessionId);
    }

    /**
     * Approves and saves a suggested classtype
     * @param classTypeName
     * @author Behrad
     */
    @Transactional
    public void approveClassType(String classTypeName){
        ClassType classType = classTypeRepository.findByClassType(classTypeName);
        if(classType == null){
            throw new IllegalArgumentException("No class type with given name");
        }
        
        classType.setIsApproved(true);
        classTypeRepository.save(classType);
        return;
    }
    /**
     * Rejects and deletes a suggested class type
     * @param classTypeName
     * @author Behrad
     */
    @Transactional
    public void rejectClassType(String classTypeName){
        ClassType classType = classTypeRepository.findByClassType(classTypeName);
        if(classType == null){
            throw new IllegalArgumentException("No class type with given name");
        }
        classTypeRepository.delete(classType);
        return;
    }
    /**
     * Creates a new classtype with approval status false
     * @param classTypeName
     * @author Behrad
     */
    @Transactional
    public void suggestClassType(String classTypeName){
        ClassType classType = classTypeRepository.findByClassType(classTypeName);
        if(classType!=null){
            throw new IllegalArgumentException("Class type with given name already suggested");
        }
        classType = new ClassType(classTypeName, false);
        classTypeRepository.save(classType);
        return;
    }
    /**
     * Views all class types based on approval status
     * @param isApproved
     * @return List<ClassType> targeted classtypes
     * @author Behrad
     */
    @Transactional
    public List<ClassType> viewClassTypeByApproval(boolean isApproved){
        List<ClassType> classTypes = classTypeRepository.findByIsApproved(isApproved);
        return classTypes;
    }
    /**
     * Registers an instructor by their person's email to teach a session
     * @param instructorEmail
     * @param sessionId
     * @author Behrad
     */
    @Transactional
    public void registerToTeachSession(String instructorEmail, int sessionId){
        Session targetSession = sessionRepository.findById(sessionId);
        if(targetSession == null){
            throw new IllegalArgumentException("No session with given ID");
        }
        if(!personRepository.existsByEmail(instructorEmail)){
            throw new IllegalArgumentException("No person with given email");
        }
        Instructor targetInstructor = instructorRepository.findByPersonEmail(instructorEmail);
        if(targetInstructor == null){
            throw new IllegalArgumentException("No instructor with given email");
        }
        targetSession.setInstructor(targetInstructor);
        sessionRepository.save(targetSession);
        return;
    }

    @Transactional
    public Session findSessionById(int id) {
        return sessionRepository.findById(id);
    }

    @Transactional
    public List<Session> findAllSessions() {
        return (List<Session>) sessionRepository.findAll();
    }
}
