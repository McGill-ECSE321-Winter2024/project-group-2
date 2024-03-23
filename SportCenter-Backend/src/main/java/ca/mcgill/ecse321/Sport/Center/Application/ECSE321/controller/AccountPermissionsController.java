package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/instructors")
    public ResponseEntity<?> grantInstructorPermissions(@RequestBody String id) throws Exception{
        int idAsInt = 0;
        InstructorDTO instructor = null;
        try {
            idAsInt = Integer.parseInt(id);
            instructor = service.grantInstructorPermissions(idAsInt);

        }
        catch(NumberFormatException num){
            return new ResponseEntity<>("Bad integer value", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            if (e.getMessage().contains("Person does not exist")){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }else if (e.getMessage().contains("Person is already an instructor")){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
            }
        }
        return new ResponseEntity<>(instructor, HttpStatus.OK);
    }

    @DeleteMapping("/instructors/{id}")
    public ResponseEntity<?> revokeInstructorPermissions(@PathVariable("id") String id){
        try {
            int idAsString = Integer.parseInt(id);
            service.revokeInstructorPermissions(idAsString);
            return new ResponseEntity<>(HttpStatus.OK);
           }
           catch(NumberFormatException num){
            return new ResponseEntity<>("Bad integer value", HttpStatus.BAD_REQUEST);
           }
           catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
           }
    }
}
