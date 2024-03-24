package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;

/**
 * This interface defines the repository for the CRUD functionalities relating
 * to the Customer: method to find a customer in the system.
 */

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    /**
     * This method gets a customer based on its id
     *
     * @param id id of the customer (primary key)
     * @return Customer with the specified id
     */
    public Customer getById(int id);
    public Customer getByPersonEmail(String email);
    public Customer getByPersonName(String name);

    public Customer findById(int id);
    public Customer findByPersonEmail(String email);
    public Customer findByPersonName(String name);

    public Boolean existsByPersonEmail(String email);

}
