package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Instructor;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;

public interface SessionRegistrationRepository extends CrudRepository<SessionRegistrationRepository, Integer>{
    //this will return only 1 registration if there is only 1 instructor max per registration
    //otherwise might bug
    @Query("SELECT sr FROM SessionRegistration sr WHERE sr.customer.user.id = ?1 AND sr.session.id = ?2")
    SessionRegistrationRepository findByCustomerAndSession(String customerId, String sessionId);
    
    @Query("SELECT sr FROM SessionRegistration sr WHERE sr.session.sessionId = ?1")
    List<SessionRegistrationRepository> findBySession(String sessionId);
    
    @Query("SELECT sr FROM SessionRegistration sr WHERE sr.customer.user.id = ?1")
    List<SessionRegistrationRepository> findByCustomer(String customerId);
    
    @Query("SELECT sr FROM SessionRegistration sr WHERE sr.session.instructor.user.id = ?1")
    List<SessionRegistrationRepository> findByInstructor(String instructorId);

    @Query("SELECT sr FROM SessionRegistration sr WHERE sr.session.instructor.id = ?1 AND sr.session.id = ?2")
    List<SessionRegistrationRepository> findByInstructorAndSession(String instructorId, String sessionId);
}
