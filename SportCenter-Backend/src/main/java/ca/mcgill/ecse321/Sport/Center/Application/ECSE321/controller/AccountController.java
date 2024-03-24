package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service.AccountService;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.CustomerDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.PersonDTO;;

/**
 * The AccountController class is responsible for handling HTTP requests related to account management.
 * It provides endpoints for creating, retrieving, updating, and deleting accounts.
 * This class is annotated with @RestController to indicate that it is a controller class that handles RESTful requests.
 * 
 * @author Behrad, Yuri
 */
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
     * Retrieves a person by their ID.
     * 
     * @param pid the ID of the person to retrieve
     * @return a ResponseEntity containing the PersonDTO if the person is found, or an error message and HTTP status code if not
     * @throws Exception if the ID is not a valid integer
     * @author Behrad, Yuri
     */
    @GetMapping("/persons/{pid}") 
    public ResponseEntity<?> findPersonById(@PathVariable String pid) throws Exception {
        int id;
        try {
            id = Integer.parseInt(pid);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        PersonDTO person;
        try {
            person = accountService.findPersonById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    /**
     * Retrieves all people.
     * 
     * @return a list of PersonDTO objects representing all people
     * @author Behrad, Yuri
     */
    @GetMapping("/persons")
    public List<PersonDTO> findAllPeople() {
        List<PersonDTO> people = accountService.findAllPeople();
        return people;
    }

    /**
     * Creates a customer account.
     * 
     * @param personDTO the PersonDTO object containing the customer's information
     * @return a ResponseEntity containing the created CustomerDTO if successful, or an error message and HTTP status code if not
     * @author Behrad, Yuri
     */
    @PostMapping("/customers")
    public ResponseEntity<?> createCustomerAccount(@RequestBody PersonDTO personDTO) {
        // null check
        ResponseEntity<?> nullCheckResponse = nullCheck(personDTO);
        if (nullCheckResponse != null) {
            return nullCheckResponse;
        }
        // password validation
        ResponseEntity<?> passwordValidationResponse = passwordValidation(personDTO.getPassword());
        if (passwordValidationResponse != null) {
            return passwordValidationResponse;
        }

        ResponseEntity<?> emailValidationResponse = emailValidation(personDTO.getEmail());
        if (emailValidationResponse != null) {
            return emailValidationResponse;
        }

        CustomerDTO newCustomer=null;
        try {
            newCustomer = accountService.createCustomerAccount(personDTO.getPassword(), personDTO.getEmail(), personDTO.getName());
        } catch (Exception e) {
            if(e.getMessage().contains("Customer account already exists")) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            PersonDTO newPerson = accountService.createPerson(personDTO.getPassword(), personDTO.getEmail(), personDTO.getName());
            newCustomer = accountService.createCustomerAccount(newPerson.getPassword(), newPerson.getEmail(), newPerson.getName());
        }
        if (newCustomer == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    /**
     * Creates a person.
     * 
     * @param personDTO the PersonDTO object containing the person's information
     * @return a ResponseEntity containing the created PersonDTO if successful, or an error message and HTTP status code if not
     * @author Behrad, Yuri
     */
    @PostMapping("/persons")
    public ResponseEntity<?> createPerson(@RequestBody PersonDTO personDTO) {
        // null check
        ResponseEntity<?> nullCheckResponse = nullCheck(personDTO);
        if (nullCheckResponse != null) {
            return nullCheckResponse;
        }
        // password validation
        ResponseEntity<?> passwordValidationResponse = passwordValidation(personDTO.getPassword());
        if (passwordValidationResponse != null) {
            return passwordValidationResponse;
        }
        // email
        ResponseEntity<?> emailValidationResponse = emailValidation(personDTO.getEmail());
        if (emailValidationResponse != null) {
            return emailValidationResponse;
        }

        PersonDTO newPersonDTO = null;
        try{
            newPersonDTO = accountService.createPerson(personDTO.getPassword(), personDTO.getEmail(), personDTO.getName());
        }catch(IllegalArgumentException e){
            if(e.getMessage().contains("Account with this email already exists")){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }

        if (newPersonDTO == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(newPersonDTO, HttpStatus.CREATED);
    }

    /**
     * Logs in a user with the provided credentials.
     * 
     * @param credentials a map containing the email and password of the user
     * @return a ResponseEntity containing true if the login is successful, false if not
     * @author Behrad, Yuri
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        ResponseEntity<?> passwordValidationResponse = passwordValidation(password);
        if (passwordValidationResponse != null) {
            return passwordValidationResponse;
        }

        ResponseEntity<?> emailValidationResponse = emailValidation(email);
        if (emailValidationResponse != null) {
            return emailValidationResponse;
        }

        boolean success = accountService.login(email, password);
        if (success) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.I_AM_A_TEAPOT);
    }

    /**
     * Deletes a customer account.
     * 
     * @param id the ID of the customer account to delete
     * @return a ResponseEntity containing an error message and HTTP status code if the customer account could not be found, HTTP status code if successful
     * @author Behrad, Yuri
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomerAccount(@PathVariable int id) {
        // null check
        try {
            accountService.deleteCustomerAccount(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes a person.
     * 
     * @param pid the ID of the person to delete
     * @return a ResponseEntity containing an error message and HTTP status code if the person could not be found, HTTP status code if successful
     * @author Behrad, Yuri
     */
    @DeleteMapping("/persons/{pid}")
    public ResponseEntity<?> deletePerson(@PathVariable int pid) {
        try {
            accountService.deletePerson(pid);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Helper methods

    /**
     * Checks if the PersonDTO object is null or if any of its fields are null.
     * 
     * @param personDTO the PersonDTO object to check
     * @return a ResponseEntity containing an error message and HTTP status code if the PersonDTO object or any of its fields are null, null if not
     * @author Behrad, Yuri
     */
    private ResponseEntity<?> nullCheck(PersonDTO personDTO) {
        if (personDTO == null) {
            return new ResponseEntity<>("PersonDTO object cannot be null.", HttpStatus.BAD_REQUEST);
        }
        if (personDTO.getPassword() == null) {
            return new ResponseEntity<>("Password cannot be null.", HttpStatus.BAD_REQUEST);
        }
        if (personDTO.getEmail() == null) {
            return new ResponseEntity<>("Email cannot be null.", HttpStatus.BAD_REQUEST);
        }
        if (personDTO.getName() == null) {
            return new ResponseEntity<>("Name cannot be null.", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    /**
     * Validates the password.
     * 
     * @param password the password to validate
     * @return a ResponseEntity containing an error message and HTTP status code if the password is invalid, null if valid
     * @author Behrad, Yuri
     */
    private ResponseEntity<?> passwordValidation(String password) {
        if (password.length() < 8) {
            return new ResponseEntity<>("Password must be at least 8 characters long.", HttpStatus.BAD_REQUEST);
        }
        if (password.equals(password.toLowerCase())) {
            return new ResponseEntity<>("Password must contain at least one uppercase letter.", HttpStatus.BAD_REQUEST);
        }
        if (password.equals(password.toUpperCase())) {
            return new ResponseEntity<>("Password must contain at least one lowercase letter.", HttpStatus.BAD_REQUEST);
        }
        if (!password.matches(".*\\d.*")) {
            return new ResponseEntity<>("Password must contain at least one number.", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    /**
     * Validates the email.
     * 
     * @param email the email to validate
     * @return a ResponseEntity containing an error message and HTTP status code if the email is invalid, null if valid
     * @author Behrad, Yuri
     */
    private ResponseEntity<?> emailValidation(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()) {
            return new ResponseEntity<>("Email must be in a valid format.", HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}


