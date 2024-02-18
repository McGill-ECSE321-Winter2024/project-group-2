package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Owner;

/**
 * This interface defines the repository for the CRUD functionalities relating
 * to the Owner.
 * 
 * @author Aurelia Bouliane
 */

public interface OwnerRepository extends CrudRepository<Owner, Integer> {

    /**
     * This method gets an owner based on its id
     *
     * @param id id of the owner (primary key)
     * @return Owner with the specified id
     * @author Aurelia Bouliane
     */
    public Owner getOwnerById(int id);
}
