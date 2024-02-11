package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Staff;

public interface StaffRepository extends CrudRepository<Staff, Integer>{
    //finding by unique identifiers 
    Staff findByEmployeeId(int employeeId);
    Staff findById(int id);
    Staff findByEmail(String email);

    //other queries
    boolean existsByEmail(String email);
}
