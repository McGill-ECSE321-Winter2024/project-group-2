package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Schedule;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;

public interface SessionRepository extends CrudRepository<Session, Integer>{
    
    //finding by unique identifiers
    Session findSessionByid(int id);

    //other queries
    List<Session> findByDayOfWeekAndScheduleStartDate(String dayOfWeek, Schedule schedule);
    List<Session> findByScheduleStartDate(Schedule schedule);
}
