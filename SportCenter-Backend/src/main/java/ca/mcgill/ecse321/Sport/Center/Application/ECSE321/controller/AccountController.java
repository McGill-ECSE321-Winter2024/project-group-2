package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service.AccountService;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.CustomerDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.PersonDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;


@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/people/{pid}") 
    public PersonDTO findPersonById(@PathVariable int pid) throws Exception {
        PersonDTO person = accountService.findPersonById(pid);
        return person;
    }

    @GetMapping("/people")
    public List<PersonDTO> findAllPeople() {
        List<PersonDTO> people = new ArrayList<PersonDTO>();
            for (Person person : accountService.findAllPeople()) {
                people.add(new PersonDTO(person));
            }
        return people;
    }

    @PutMapping("/customers")
    public ResponseEntity<CustomerDTO> createCustomerAccount(@RequestBody PersonDTO personDTO) {
        CustomerDTO newCustomer = accountService.createCustomerAccount(personDTO.getPassword(), personDTO.getEmail(), personDTO.getName());
        if (newCustomer == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/persons")
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO) {
        Person newPerson = accountService.createPerson(personDTO.getPassword(), personDTO.getEmail(), personDTO.getName());
        if (newPerson == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        PersonDTO newPersonDto = new PersonDTO(newPerson);
        return new ResponseEntity<>(newPersonDto, HttpStatus.CREATED);
    }

    @PutMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        boolean success = accountService.login(email, password);
        if (success) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    }

}
