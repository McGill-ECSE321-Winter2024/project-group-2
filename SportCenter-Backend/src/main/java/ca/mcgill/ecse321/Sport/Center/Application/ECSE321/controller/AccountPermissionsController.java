package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.InstructorDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service.AccountPermissionsService;

/**
 * The AccountPermissionsController class is a REST controller that handles requests related to account permissions.
 * It provides endpoints for granting and revoking instructor permissions.
 * 
 * @author Sebastian
 */
@CrossOrigin(origins="http://localhost:8087")
@RestController
public class AccountPermissionsController {
    @Autowired
    AccountPermissionsService service;

    @GetMapping("/instructors")
    public ResponseEntity<?> getAllInstructors(){
        try{
            return new ResponseEntity<>(service.getAllInstructors(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Endpoint for granting instructor permissions to a user.
     * 
     * @param id the ID of the user to grant instructor permissions to
     * @return a ResponseEntity containing the InstructorDTO of the granted instructor and an HTTP status code
     * @throws Exception if an error occurs during the granting of instructor permissions
     * @author Sebastian
     */
    @PostMapping("/instructors")
    public ResponseEntity<?> grantInstructorPermissions(@RequestBody String id) throws Exception{
        int idAsInt = 0;
        InstructorDTO instructor = null;
        try {
            idAsInt = Integer.parseInt(id);
            instructor = service.grantInstructorPermissions(idAsInt);
        }
        catch(NumberFormatException num){
            return new ResponseEntity<>("Bad integer value: "+num, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            if (e.getMessage().contains("Person does not exist")){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }else if (e.getMessage().contains("Person is already an instructor")){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(e.getMessage(), HttpStatus.I_AM_A_TEAPOT);
            }
        }
        return new ResponseEntity<>(instructor, HttpStatus.OK);
    }

    /**
     * Endpoint for revoking instructor permissions from a user.
     * 
     * @param id the ID of the user to revoke instructor permissions from
     * @return a ResponseEntity with an HTTP status code indicating the success of the operation
     * @author Sebastian
     */
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
