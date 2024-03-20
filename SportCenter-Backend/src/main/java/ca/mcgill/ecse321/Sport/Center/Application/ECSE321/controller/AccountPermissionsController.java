package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dao.PersonRepository;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.CustomerDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.InstructorDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service.AccountPermissionsService;

@RestController
public class AccountPermissionsController {
    @Autowired
    AccountPermissionsService service;

    @PutMapping("/persons/{id}")
    public ResponseEntity<?> grantInstructorPermissions(@PathVariable("id") int id){
       try {
        InstructorDTO instructor = service.grantInstructorPermissions(id);
        return new ResponseEntity<>(instructor, HttpStatus.OK);
       } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       }
    }

    @PutMapping("/instructors/{id}")
    public ResponseEntity<?> revokeInstructorPermissions(@PathVariable("id") int id){
        try {
            service.revokeInstructorPermissions(id);
            return new ResponseEntity<>(HttpStatus.OK);
           } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
           }
    }
}
