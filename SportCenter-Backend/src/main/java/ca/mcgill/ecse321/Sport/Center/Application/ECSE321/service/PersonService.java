package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;
import jakarta.transaction.Transactional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepo;

    @Transactional
    public Iterable<Person> findAllPeople() {
        return personRepo.findAll();
    }

    @Transactional
    public Person findPersonById(int pid) throws Exception {
        Person p = personRepo.getPersonByPersonId(pid); // this is written as findPersonById in the tutorial
        if (p == null) {
            // need to make this a SportCenterApplicationException
            throw new Exception("There is no person with this ID");
        }
        return p;
    }
}
