package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.PersonDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service.AccountService;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;

@RestController
public class PersonController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/people/{pid}")
    public PersonDTO findPersonbyId(@PathVariable int pid) throws Exception {
        Person person = accountService.findPersonById(pid);
        return new PersonDTO(person);
    }
}
