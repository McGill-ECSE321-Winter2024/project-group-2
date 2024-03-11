package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.CustomerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Role;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.OwnerRepository;

@Service
public class AccountService {
    
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @Transactional
    public void createCustomerAccount(int personId, String password, String email, String name){
        Customer newAccountRole =  new Customer();

        Person newCustomer = createPerson(personId, password, email, name, newAccountRole);
        customerRepository.save(newAccountRole);
        personRepository.save(newCustomer);
        
    }

    @Transactional
    public Iterable<Person> findAllPeople() {
        return personRepository.findAll();
    }

    @Transactional
    public Person findPersonById(int pid) throws Exception {
        Person p = personRepository.getPersonByPersonId(pid); // this is written as findPersonById in the tutorial
        if (p == null) {
            // need to make this a SportCenterApplicationException
            throw new Exception("There is no person with this ID");
        }
        return p;
    }

    @Transactional
    public Person createPerson(int personId, String password, String email, String name, Role role) {

        Person person = new Person(personId, password, email, name, role);

        return personRepository.save(person);
    }
    
    @Transactional
    public boolean login(String email, String password){
        if (personRepository.existsByEmail(email)) {
        Person toLogin = personRepository.getPersonByEmail(email);
            if (toLogin.getPassword().equals(password)) return true;
        }
        return false;
        
    }
}
