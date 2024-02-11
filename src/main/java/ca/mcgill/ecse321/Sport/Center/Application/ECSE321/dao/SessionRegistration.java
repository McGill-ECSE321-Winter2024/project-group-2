package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Instructor;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;

public interface SessionRegistration extends CrudRepository<SessionRegistration, Integer>{
    //this will return only 1 registration if there is only 1 instructor max per registration
    //otherwise might bug
    SessionRegistration findByCustomerAndSession(Customer customer, Session session);
    
    List<SessionRegistration> findBySession(Session session);
    List<SessionRegistration> findByCustomer(Customer customer);
    List<SessionRegistration> findByInstructor(Instructor instructor);

    List<SessionRegistration> findByInstructorAndSession(Instructor instructor, Session session);
}
