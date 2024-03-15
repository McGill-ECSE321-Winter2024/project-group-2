package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.OwnerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Instructor;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;

@Service
public class AccountPermissionsService {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    OwnerRepository ownerRepository;

    @Transactional
    public void grantInstructorPermissions(String personEmail) throws IllegalArgumentException{
        if(personEmail==null || personEmail.trim().length()==0){
            throw new IllegalArgumentException("Person email cannot be empty");
        }
        if(! personRepository.existsByEmail(personEmail)){
            throw new IllegalArgumentException("Person does not exist");
        }
        if(instructorRepository.existsByPersonEmail(personEmail)){
            throw new IllegalArgumentException("Person already has instructor permissions");
        }
        
        
        Person person = personRepository.findByEmail(personEmail);
        Instructor newInstructorRole = new Instructor();
        newInstructorRole.setPerson(person);
        
        instructorRepository.save(newInstructorRole);
        return;
    }

    @Transactional
    public void revokeInstructorPermissions(String personEmail){
        if(personEmail==null || personEmail.trim().length()==0){
            throw new IllegalArgumentException("Person email cannot be empty");
        }
        if(! personRepository.existsByEmail(personEmail)){
            throw new IllegalArgumentException("Person does not exist");
        }
        if(! instructorRepository.existsByPersonEmail(personEmail)){
            throw new IllegalArgumentException("Person does not have instructor permissions");
        }

        instructorRepository.deleteByPersonEmail(personEmail);
        return;
    }
}
