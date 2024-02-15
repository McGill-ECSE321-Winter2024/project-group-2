package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Schedule;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;

public interface SessionRepository extends CrudRepository<Session, Integer>{
    
    //finding by unique identifiers
    Session findSessionByid(int id); //automatic query generated

    //other queries
    @Query("SELECT s FROM Session s WHERE s.dayOfWeek = ?1 AND s.date = ?2")
    List<Session> findByDayOfWeekAndDate(String dayOfWeek, String startDate);

    List<Session> findByDateGreaterThanEqual(Date startDate);

    @Query("SELECT s FROM Session s WHERE s.schedule.classType.classType = ?1") //not sure about capitalization
    List<Session> findByClassType(String classType);

    List<Session> findByStartDate(String startDate);  //automatic

    //@Query("SELECT s xFROM Session s WHERE s.instructor.user.id = ?1")
    List<Session> findByInstructorUserId(String instructorId); 
    
}
