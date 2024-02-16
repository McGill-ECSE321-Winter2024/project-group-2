package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Instructor;

public interface InstructorRepository extends CrudRepository<Instructor, Integer>{
    public Instructor getInstructorById(int id);
}
