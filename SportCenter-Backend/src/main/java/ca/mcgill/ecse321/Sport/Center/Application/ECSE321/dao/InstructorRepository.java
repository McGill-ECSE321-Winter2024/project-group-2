package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Instructor;

import java.util.List;

/**
 * This interface defines the repository for the CRUD functionalities relating
 * to the Instructor: method to find an Instructor in the system.
 * 
 */
public interface InstructorRepository extends CrudRepository<Instructor, Integer> {

    /**
     * This method gets an instructor based on its id
     *
     * @param id id of the instructor (primary key)
     * @return Instructor with the specified id
     */
    public Instructor getById(int id);
    public Instructor getByPersonName(String name);
    public Instructor getByPersonEmail(String email);
    //TODO not sure if we need

    public Instructor findById(int id);
    public Instructor findByPersonName(String name);
    public Instructor findByPersonEmail(String email);
    public List<Instructor> findAllInstructors();
    


    public void deleteByPersonEmail(String email);

    public boolean existsByPersonEmail(String personEmail);
}
