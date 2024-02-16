package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer>{
    
    Person findById(int id);
    
    Person findByEmail(String email);

}
