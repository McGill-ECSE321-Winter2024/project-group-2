package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Owner;

/**
 * This interface defines the repository for the CRUD functionalities relating
 * to the Owner.
 * 
 */

public interface OwnerRepository extends CrudRepository<Owner, Integer> {

    /**
     * This method gets an owner based on its id
     *
     * @param id id of the owner (primary key)
     * @return Owner with the specified id
     */
    public Owner getById(int id);
    public Owner getByPersonName(String name);
    //TODO dont know if we need

    public Owner findById(int id);
    public Owner findByPersonName(String name);
}
