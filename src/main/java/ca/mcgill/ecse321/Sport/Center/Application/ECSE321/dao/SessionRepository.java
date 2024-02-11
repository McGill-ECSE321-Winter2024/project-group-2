package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;

public interface SessionRepository extends CrudRepository<Session, Integer>{
    
    Session findSessionById(int id);
}
