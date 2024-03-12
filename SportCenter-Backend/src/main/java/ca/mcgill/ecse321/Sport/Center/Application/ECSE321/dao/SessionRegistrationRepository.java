package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.SessionRegistration;
import java.util.List;

/**
 * This interface defines the repository for the CRUD functionalities relating
 * to the Session Registration.
 * 
 */

public interface SessionRegistrationRepository extends CrudRepository<SessionRegistration, Integer> {

    /**
     * This method gets a Session Registration based on its id
     *
     * @param id id of the Session Registration (primary key)
     * @return Session Registration with the specified id
     */
    public SessionRegistration getById(int id);
    public SessionRegistration findById(int id);
    /**
     * This method gets the list of session registrations by the session id
     * 
     * @param id id of the session
     * @return list of session registrations
     */
    public List<SessionRegistration> findBySessionId(int id);
}
