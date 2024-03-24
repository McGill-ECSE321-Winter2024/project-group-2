package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.CustomerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.CustomerDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.PersonDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.SessionRegistrationDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;

@Service
public class AccountService {
    
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CustomerRepository customerRepository;
    
    public boolean isNullOrEmpty(String s){
        return s == null || s.isBlank();
    }
    
    @Transactional
    public CustomerDTO createCustomerAccount(String password, String email, String name) {
        if(isNullOrEmpty(password) || isNullOrEmpty(email) || isNullOrEmpty(name)){
            throw new IllegalArgumentException("Password, email, and name cannot be empty");
        }      
        PersonDTO personDTO;
        Person person = null;
        if(!personRepository.existsByEmail(email)){
            personDTO = createPerson(password, email, name);
        } else {
            person = personRepository.findByEmail(email);
        }
        if(customerRepository.findByPersonEmail(email)!=null){
            throw new IllegalArgumentException("Customer account already exists");
        }
        
        Customer newCustomerRole =  new Customer(person);
        newCustomerRole = customerRepository.save(newCustomerRole);
        
        // Making customer DTO to return
        CustomerDTO newCustomer = new CustomerDTO(); 
        newCustomer.setId(newCustomerRole.getId());
        newCustomer.setSessions(new ArrayList<SessionRegistrationDTO>());

        return newCustomer;
    }

    @Transactional
    public List<PersonDTO> findAllPeople() {
        Iterable<Person> personList = personRepository.findAll();
        List<PersonDTO> personDTOList = new ArrayList<PersonDTO>();
    
        for (Person person : personList) {
            PersonDTO personDTO = new PersonDTO(person);
            personDTOList.add(personDTO);
        }
        return personDTOList;
    }

    @Transactional
    public PersonDTO findPersonById(int pid) throws Exception {
        Person p = personRepository.findById(pid); // this is written as findPersonById in the tutorial

        if (p == null) {
            // need to make this a SportCenterApplicationException
            throw new Exception("There is no person with this ID");
        }
        PersonDTO newPerson = new PersonDTO(p);

        return newPerson;
    }

    @Transactional
    public PersonDTO createPerson(String password, String email, String name) {
          //TODO make all valid checks for password, duplicate email, etc. Remember to throw a SportCenterException

        if(isNullOrEmpty(password) || isNullOrEmpty(email) || isNullOrEmpty(name)){
            throw new IllegalArgumentException("Password, email, and name cannot be empty");
        }

        if(personRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Account with this email already exists");
        }
        
        Person person = new Person();
        person.setEmail(email);
        person.setName(name);
        person.setPassword(password);
        person = personRepository.save(person);

        PersonDTO personDTO = new PersonDTO(person);
        return personDTO;
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

    @Transactional
    public void deleteCustomerAccount(int cid) throws Exception{        

        if (customerRepository.existsById(cid)) {
            customerRepository.deleteById(cid);
            return;
        } else {
            throw new Exception("There is no customer with this ID");
        }
    }

    @Transactional
    public void deletePerson(int personId) throws Exception{
        if (personRepository.existsById(personId)) {
            personRepository.deleteById(personId);
        } else {
            throw new Exception("There is no person with this ID");
        }

    }
}


