package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import java.util.*;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;

/**
 * This interface defines the repository for the CRUD functionalities relating
 * to the Session.
 * 
 */

public interface SessionRepository extends CrudRepository<Session, Integer> {

    /**
     * This method gets a Session based on its id
     *
     * @param id id of the Session (primary key)
     * @return Session with the specified id
     */
    public Session getById(int id);

    public Session findById(int id);

    public List<Session> findByinstructorId(int id);
}
