package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.SessionRegistration;

public interface SessionRegistrationRepository extends CrudRepository<SessionRegistration, Integer>{
    public SessionRegistration getSessionRegistrationById(int id);
}
