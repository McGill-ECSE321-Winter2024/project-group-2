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
        if(! personRepository.existsByEmail(email)){
            Person person = new Person();
            person.setName(name);
            person.setEmail(email);
            person.setPassword(password);
            personRepository.save(person);
        }
        Person person = personRepository.findByEmail(email);        
        Customer newCustomerRole =  new Customer(person);
        customerRepository.save(newCustomerRole);
        
    }

    @Transactional
    public Iterable<Person> findAllPeople() {
        return personRepository.findAll();
    }

    @Transactional
    public Person findPersonById(int pid) throws Exception {
        Person p = personRepository.findById(pid); // this is written as findPersonById in the tutorial

        if (p == null) {
            // need to make this a SportCenterApplicationException
            throw new Exception("There is no person with this ID");
        }
        return p;
    }

    @Transactional
    public Person createPerson(int personId, String password, String email, String name) {
        //TODO make all valid checks for password, duplicate email, etc. Remember to throw a SportCenterException
        if(personRepository.existsByEmail(email)){
            return null;
        }
        
        Person person = new Person();
        person.setEmail(email);
        person.setName(name);
        person.setPassword(password);
        return personRepository.save(person);
    }
    
    @Transactional

    public boolean login(String email, String password){
        if (personRepository.existsByEmail(email)) {
            Person toLogin = personRepository.findByEmail(email);
            if (toLogin.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
}
