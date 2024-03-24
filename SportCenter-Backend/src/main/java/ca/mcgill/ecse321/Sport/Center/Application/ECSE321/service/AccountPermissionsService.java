package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.OwnerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.InstructorDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.SessionDTO;
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
    public InstructorDTO grantInstructorPermissions(int id) throws Exception{
        if(! personRepository.existsById(id)){
            throw new Exception("Person does not exist");
        }

        Person person = personRepository.findById(id);
        if (instructorRepository.existsByPersonEmail(person.getEmail())){
            throw new Exception("Person is already an instructor");
        }

        Instructor newInstructorRole = new Instructor();
        newInstructorRole.setPerson(person);
        newInstructorRole = instructorRepository.save(newInstructorRole);

        return new InstructorDTO(newInstructorRole.getId(), new ArrayList<SessionDTO>());
    }

    @Transactional
    public void revokeInstructorPermissions(int id) throws Exception{
        if(! instructorRepository.existsById(id)){
            throw new Exception("Instructor does not exist");
        }

        instructorRepository.deleteById(id);
        return;
    }
}
