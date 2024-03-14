package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.OwnerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Instructor;

@Service
public class AccountPermissionsService {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    OwnerRepository ownerRepository;

    @Transactional
    public void grantInstructorPermissions(String personEmail){
        if(! personRepository.existsByEmail(personEmail)){
            return;
        }
        
        Instructor newInstructorRole = new Instructor();
        newInstructorRole.setPerson(personRepository.findByEmail(personEmail));

        return;
    }

    @Transactional
    public void revokeInstructorPermissions(String personEmail){
        if(! personRepository.existsByEmail(personEmail)){
            return;
        }
        if(! instructorRepository.existsByPersonEmail(personEmail)){
            return;
        }
        instructorRepository.deleteByPersonEmail(personEmail);
        return;
    }
}
