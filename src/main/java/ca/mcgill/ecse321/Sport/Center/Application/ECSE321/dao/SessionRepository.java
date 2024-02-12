package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Schedule;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;

public interface SessionRepository extends CrudRepository<Session, Integer>{
    
    //finding by unique identifiers
    Session findSessionByid(int id); //automatic query generated

    //other queries
    @Query("SELECT s FROM Session s WHERE s.dayOfWeek = ?1 AND s.schedule.startDate = ?2")
    List<Session> findByDayOfWeekAndScheduleStartDate(String dayOfWeek, String startDate);

    @Query("SELECT s FROM Session s WHERE s.schedule.startDate = ?1")
    List<Session> findByScheduleStartDate(String startDate);

    List<Session> findByStartDate(String startDate);  //automatic

    
}
