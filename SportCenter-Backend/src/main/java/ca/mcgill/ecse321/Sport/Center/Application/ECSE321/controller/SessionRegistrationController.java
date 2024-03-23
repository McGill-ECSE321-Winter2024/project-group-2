package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.SessionRegistration;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service.SessionRegistrationService;

/**
 * The SessionRegistrationController class handles HTTP requests related to session registrations.
 * It provides endpoints for registering sessions, viewing sessions, and managing registrations.
 */
@RestController
public class SessionRegistrationController {
    @Autowired
    private SessionRegistrationService service;
    
    /**
     * Endpoint for retrieving all available sessions.
     * 
     * @return an iterable list of sessions
     * @author Aurelia Bouliane
     */
    @GetMapping("/sessions")
    public Iterable<Session> viewSessions() {
        return service.viewSessions();
    }

    /**
     * Endpoint for registering a customer to a session.
     * 
     * @param sessionId the ID of the session
     * @param customerId the ID of the customer
     * @return a ResponseEntity containing the session registration or an error message
     * @author Aurelia Bouliane
     */
    @PutMapping("/register/session/{sessionId}/customer/{customerId}")
    public ResponseEntity<?> registerForSession(@PathVariable("sessionId") String sessionId, @PathVariable("customerId") String customerId) {
        try {
            int sessionIdInt = Integer.parseInt(sessionId);
            int customerIdInt = Integer.parseInt(customerId);
            SessionRegistration registration = service.registerForSession(sessionIdInt, customerIdInt);
            return ResponseEntity.ok(registration);
        } catch (NumberFormatException num) {
           return new ResponseEntity<>("Bad integer value", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Endpoint for viewing a specific session registration by its ID.
     * 
     * @param pid the registration ID
     * @return a ResponseEntity containing the session registration or an error message
     * @author Aurelia Bouliane
     */
    @GetMapping("/session/{pid}")
    public ResponseEntity<?> viewSpecificSession(@PathVariable String pid) {
        try {
            int pidInt = Integer.parseInt(pid);
            SessionRegistration registration = service.viewSpecificSession(pidInt);
            return ResponseEntity.ok(registration);
        } catch (NumberFormatException num) {
            return new ResponseEntity<>("Bad integer value", HttpStatus.BAD_REQUEST);
         }
         catch (Exception e) {
             return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
         }
    }

    /**
     * Endpoint for cancelling a registration by its ID.
     * 
     * @param id the registration ID
     * @return a ResponseEntity indicating success or failure
     * @author Aurelia Bouliane
     */
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelRegistration(@PathVariable String id) {
        try {
            int idInt = Integer.parseInt(id);
            service.cancelRegistration(idInt);
            return ResponseEntity.ok().build();
        } catch (NumberFormatException num) {
            return new ResponseEntity<>("Bad integer value", HttpStatus.BAD_REQUEST);
         }
         catch (Exception e) {
             return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
         }
    }

    /**
     * Endpoint for viewing all registrations for a specific customer.
     * 
     * @param customerId the ID of the customer
     * @return a ResponseEntity containing a list of session registrations or an error message
     * @author Aurelia Bouliane
     */
    @GetMapping("/registrations/customer/{customerId}")
    public ResponseEntity<?> viewRegistrationsByCustomer(@PathVariable String customerId) {
        try {
            int customerIdInt = Integer.parseInt(customerId);
            List<SessionRegistration> registrations = service.viewRegistrationsByCustomer(customerIdInt);
            return ResponseEntity.ok(registrations);
        } catch (NumberFormatException num) {
            return new ResponseEntity<>("Bad integer value", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint for viewing all registrations for a specific session.
     * 
     * @param sessionId the ID of the session
     * @return a ResponseEntity containing a list of session registrations or an error message
     * @author Aurelia Bouliane
     */
    @GetMapping("/registrations/session/{sessionId}")
    public ResponseEntity<?> viewRegistrationsBySession(@PathVariable String sessionId) {
        try {
            int sessionIdInt = Integer.parseInt(sessionId);
            List<SessionRegistration> registrations = service.viewRegistrationsBySession(sessionIdInt);
            return ResponseEntity.ok(registrations);
        } catch (NumberFormatException num) {
            return new ResponseEntity<>("Bad integer value", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
