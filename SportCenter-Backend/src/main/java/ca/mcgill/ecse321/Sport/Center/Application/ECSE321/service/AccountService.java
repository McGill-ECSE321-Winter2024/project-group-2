package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.CustomerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;

@Service
public class AccountService {
    
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Transactional
    public void createCustomerAccount(int personId, String password, String email, String name){
        Person person = personRepository.getById(personId);        
        Customer newCustomerRole =  new Customer(person);

        customerRepository.save(newCustomerRole);
        personRepository.save(person);
        
    }

    @Transactional
    public Iterable<Person> findAllPeople() {
        return personRepository.findAll();
    }

    @Transactional
    public Person findPersonById(int pid) throws Exception {
        Person p = personRepository.getById(pid); // this is written as findPersonById in the tutorial

        if (p == null) {
            // need to make this a SportCenterApplicationException
            throw new Exception("There is no person with this ID");
        }
        return p;
    }

    @Transactional
    public Person createPerson(int personId, String password, String email, String name) {
        //TODO make all valid checks for password, duplicate email, etc. Remember to throw a SportCenterException
        Person person = new Person(personId, password, email, name);
        return personRepository.save(person);
    }
    
    @Transactional

    public boolean login(String email, String password){
        if (personRepository.existsByEmail(email)) {
        Person toLogin = personRepository.getByEmail(email);
            if (toLogin.getPassword().equals(password)) return true;
        }
        return false;
    }
}
