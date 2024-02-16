package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{
    public Customer getCustomerById(int id);
}
