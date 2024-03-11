package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.CustomerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Role;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.OwnerRepository;

@Service
public class AccountService {
    
    @Autowired
    PersonRepository personRepo;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    OwnerRepository ownerRepository;

    @Transactional
    public void createAccount(){
        return;
    }

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

    @Transactional
    public Person createPerson(int personId, String password, String email, String name, Role role) {
        //TODO make all valid checks for password, duplicate email, etc. Remember to throw a SportCenterException
        Person person = new Person(personId, password, email, name, role);

        return personRepo.save(person);
    }
    
    @Transactional
    public void login(){
        return;
    }
}
