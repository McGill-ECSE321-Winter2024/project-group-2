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
    public Customer getCustomerById(int id);
    public Customer getCustomerByPersonEmail(String email);
    public Customer getCustomerByPersonName(String name);
}
