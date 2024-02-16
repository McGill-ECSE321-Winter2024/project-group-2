package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Staff;

public interface StaffRepository extends CrudRepository<Staff, Integer>{
    //idk if we can even query an abstract class 
    Staff findByEmployeeId(int employeeId);
    Staff findByPersonId(int id);
    Staff findByPersonEmail(String email);

    boolean existsByEmail(String email);
}
