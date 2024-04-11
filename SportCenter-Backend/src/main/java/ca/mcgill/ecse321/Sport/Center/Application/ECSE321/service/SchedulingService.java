package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.ClassTypeRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.OwnerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.SessionRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.ClassTypeDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.SessionDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.SessionRegistrationRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.ClassType;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Instructor;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;

/**
 * The SchedulingService class provides methods for creating, updating, and
 * deleting sessions,
 * as well as managing class types and instructors.
 * It interacts with the SessionRepository, InstructorRepository,
 * ClassTypeRepository,
 * SessionRegistrationRepository, and PersonRepository to perform these
 * operations.
 * 
 * @author Pei Yan
 */
@Service
public class SchedulingService {
    // Autowired repositories
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

    /**
     * Creates a new session with the given parameters and saves it to the database.
     * 
     * @param length          The length of the session in minutes
     * @param startTime       The start time of the session
     * @param endTime         The end time of the session
     * @param date            The date of the session
     * @param isRepeating     Indicates if the session is repeating or not
     * @param maxParticipants The maximum number of participants allowed in the
     *                        session
     * @param classType       The class type of the session
     * @param instructorId    The ID of the instructor teaching the session
     * @return The created session as a SessionDTO object
     * @throws IllegalArgumentException If any of the input parameters are invalid
     * @author Pei Yan
     */
    @Transactional
    public SessionDTO createSession(int length, Time startTime, Time endTime, Date date, boolean isRepeating,
            int maxParticipants, ClassType classType, int instructorId) {
        String error = "";
        if (startTime.after(endTime)) {
            error += "Start time must be before end time";
        }
        if (!classType.getIsApproved()) {
            error += "Class type must be approved";
        }
        if (instructorRepository.findById(instructorId) == null) {
            error += "No instructor with given ID";
        }
        if (error != "") {
            throw new IllegalArgumentException(error);
        }

        // Create session and save to repository
        Instructor instructor = instructorRepository.findById(instructorId);
        Session session = new Session(length, startTime, endTime, date, isRepeating, maxParticipants, classType,
                instructor);
        session = sessionRepository.save(session);

        // Return session as SessionDTO object
        return new SessionDTO(session);
    }

    /**
     * Updates an existing session in the database
     * 
     * @param sessionId       The ID of the session to update
     * @param length          The length of the session in minutes
     * @param startTime       The start time of the session
     * @param endTime         The end time of the session
     * @param date            The date of the session
     * @param isRepeating     Indicates if the session is repeating or not
     * @param maxParticipants The maximum number of participants allowed in the
     *                        session
     * @param classType       The class type of the session
     * @param instructor      The instructor teaching the session
     * @author Behrad
     */
    @SuppressWarnings("null")
    @Transactional
    public SessionDTO updateSession(int sessionId, int length, Time startTime, Time endTime, Date date,
            boolean isRepeating, int maxParticipants, ClassType classType, int instructorId) { // maybe
        String error = "";
        if (startTime.after(endTime)) {
            error += "Start time must be before end time";
        }
        if (!classType.getIsApproved()) {
            error += "Class type must be approved";
        }
        if (classTypeRepository.findByClassType(classType.getClassType()) == null) {
            error += "No class type with given name";
        }
        if (instructorRepository.findById(instructorId) == null) {
            error += "No instructor with given ID";
        }

        Session session = sessionRepository.findById(sessionId);
        if (session == null) {
            error += "No session with given ID";
        }
        Instructor targetInstructor = instructorRepository.findById(instructorId);
        if (targetInstructor == null) {
            error += "No instructor with given ID";
        }
        if (error != "") {
            throw new IllegalArgumentException(error);
        }
        try {
            session.setLength(length);
            session.setStartTime(startTime);
            session.setEndTime(endTime);
            session.setDate(date);
            session.setIsRepeating(isRepeating);
            session.setMaxParticipants(maxParticipants);
            session.setClassType(classType);
            session.setInstructor(targetInstructor);
            session = sessionRepository.save(session);

            // Return updated session as SessionDTO object
            return new SessionDTO(session);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Deletes a session from the database
     * 
     * @param sessionId The ID of the session to delete
     * @author Pei Yan
     */
    @Transactional
    public void deleteSession(int sessionId) throws Exception {
        if (!sessionRepository.existsById(sessionId)) {
            throw new Exception("No session with given ID");
        }
        sessionRegistrationRepository.deleteAllBySessionId(sessionId);

        // Delete session from repository
        sessionRepository.deleteById(sessionId);
    }

    /**
     * Approves and saves a suggested classtype
     * 
     * @param classTypeName
     * @author Behrad
     */
    @Transactional
    public void approveClassType(String classTypeName) {
        ClassType classType = classTypeRepository.findByClassType(classTypeName);
        if (classType == null) {
            throw new IllegalArgumentException("No class type with given name");
        }

        classType.setIsApproved(true);
        classTypeRepository.save(classType);
    }

    /**
     * Rejects and deletes a suggested class type
     * 
     * @param classTypeName
     * @author Behrad
     */
    @Transactional
    public void rejectClassType(String classTypeName) {
        ClassType classType = classTypeRepository.findByClassType(classTypeName);
        if (classType == null) {
            throw new IllegalArgumentException("No class type with given name");
        }

        // Delete class type from repository
        classTypeRepository.delete(classType);
    }

    /**
     * Creates a new classtype with approval status false
     * 
     * @param classTypeName
     * @author Behrad
     */
    @Transactional
    public void suggestClassType(String classTypeName) {
        ClassType classType = classTypeRepository.findByClassType(classTypeName);
        if (classType != null) {
            throw new IllegalArgumentException("Class type with given name already suggested");
        }

        // Create class type with approval status set to false and save to repository
        classType = new ClassType(classTypeName, false);
        classTypeRepository.save(classType);
    }

    /**
     * Views all class types based on approval status
     * 
     * @param isApproved
     * @return List<ClassType> targeted classtypes
     * @author Behrad
     */
    @Transactional
    public List<ClassTypeDTO> viewClassTypeByApproval(boolean isApproved){
        List<ClassType> classTypes = classTypeRepository.findByIsApproved(isApproved);
        List<ClassTypeDTO> classTypeDTOs = new ArrayList<>();
        for (ClassType classType : classTypes) {
            classTypeDTOs.add(new ClassTypeDTO(classType));
        }
        return classTypeDTOs;
    }

    /**
     * Registers an instructor by their person's email to teach a session
     * 
     * @param instructorEmail
     * @param sessionId
     * @author Behrad
     */
    @Transactional
    public void registerToTeachSession(String instructorEmail, int sessionId) {
        Session targetSession = sessionRepository.findById(sessionId);
        if (targetSession == null) {
            throw new IllegalArgumentException("No session with given ID");
        }
        if (!personRepository.existsByEmail(instructorEmail)) {
            throw new IllegalArgumentException("No person with given email");
        }
        Instructor targetInstructor = instructorRepository.findByPersonEmail(instructorEmail);
        if (targetInstructor == null) {
            throw new IllegalArgumentException("No instructor with given email");
        }

        // Register instructor for session and save to repository
        targetSession.setInstructor(targetInstructor);
        sessionRepository.save(targetSession);
    }

    /**
     * Finds and retrieves a session with the given ID.
     * 
     * @param id The ID of the session to find
     * @return The session with the specified ID
     * @author Pei Yan
     */
    @Transactional
    public Session findSessionById(int id) {
        return sessionRepository.findById(id);
    }

    /**
     * Finds and retrieves a session with the given ID.
     * 
     * @param id The ID of the session to find
     * @return The session with the specified ID
     * @author Pei Yan
     */
    @Transactional
    public List<Session> findSessionsByInstructor(int id) {

        return sessionRepository.findByinstructorId(id);
    }

    /**
     * Retrieves a list of all sessions in the database.
     * 
     * @return A list of all sessions
     * @author Pei Yan
     */
    @Transactional
    public List<Session> findAllSessions() {
        return (List<Session>) sessionRepository.findAll();
    }

}