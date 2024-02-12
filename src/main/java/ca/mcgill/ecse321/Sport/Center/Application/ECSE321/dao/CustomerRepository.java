package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Role;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{
    //finding by unique identifiers

    Customer findById(int id);
    
    Customer findByEmail(String email);

}
