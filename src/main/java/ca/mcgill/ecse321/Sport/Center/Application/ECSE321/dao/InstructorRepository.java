package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Instructor;

public interface InstructorRepository extends CrudRepository<Instructor, Integer>{
    Instructor findByPersonId(int id);
    Instructor findByPersonEmail(String email);
    Instructor findByEmployeeId(int employeeId);
    
}
