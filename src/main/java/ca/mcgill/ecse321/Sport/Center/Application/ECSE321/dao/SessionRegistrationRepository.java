package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.SessionRegistration;

/**
 * This interface defines the repository for the CRUD functionalities relating
 * to the Session Registration.
 * 
 * @author Sebastian Reinhardt
 */

public interface SessionRegistrationRepository extends CrudRepository<SessionRegistration, Integer> {

    /**
     * This method gets a Session Registration based on its id
     *
     * @param id id of the Session Registration (primary key)
     * @return Session Registration with the specified id
     * @author Sebastian Reinhardt
     */
    public SessionRegistration getSessionRegistrationById(int id);
}
