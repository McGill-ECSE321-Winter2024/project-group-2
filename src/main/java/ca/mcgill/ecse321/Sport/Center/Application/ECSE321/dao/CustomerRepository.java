package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Role;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

    Customer findByPersonId(int id);
    Customer findByPersonEmail(String email);
    boolean existsByPersonEmail(String email);

}
