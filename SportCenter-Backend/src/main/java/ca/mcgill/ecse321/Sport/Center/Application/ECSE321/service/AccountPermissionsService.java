package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service;

import java.util.ArrayList;

import org.hibernate.sql.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.InstructorRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.OwnerRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.CustomerDTO;
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
            throw new Exception("not found");
        }

        Instructor newInstructorRole = new Instructor();
        newInstructorRole.setPerson(personRepository.findById(id));

        return new InstructorDTO(newInstructorRole.getId(), new ArrayList<SessionDTO>());
    }

    @Transactional
    public void revokeInstructorPermissions(int id) throws Exception{
        if(! instructorRepository.existsById(id)){
            throw new Exception("not found");
        }

        instructorRepository.deleteById(id);
        return;
    }
}
