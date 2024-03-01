package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;

/**
 * This interface defines the repository for the CRUD functionalities relating
 * to the Instructor: method to find a Person in the system.
 * 
 */

public interface PersonRepository extends CrudRepository<Person, Integer> {

    /**
     * This method gets a Person based on its id
     *
     * @param id id of the person (primary key)
     * @return Person with the specified id
     */
    public Person getPersonByPersonId(int id);
}
