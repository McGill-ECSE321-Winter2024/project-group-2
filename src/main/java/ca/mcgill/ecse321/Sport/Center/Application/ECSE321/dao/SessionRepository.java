package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;

public interface SessionRepository extends CrudRepository<Session, Integer> {

    List<Session> findByDateGreaterThanEqual(Date startDate);
    
    List<Session> findByDate(String startDate);

    //@Query("SELECT s FROM Session s WHERE s.classType.classType = ?1") // not sure about capitalization
    //gotta test if this works automatically
    List<Session> findByClassTypeClassType(String classType); 

    //@Query("SELECT s xFROM Session s WHERE s.instructor.person.id = ?1")
    List<Session> findByInstructorPersonId(String instructorId); 
    
}
