package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.SessionRegistration;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service.SessionRegistrationService;

/**
 * The SessionRegistrationController class handles HTTP requests related to
 * session registrations.
 * It provides endpoints for registering sessions, viewing sessions, and
 * managing registrations.
 */
@RestController
public class SessionRegistrationController {
    @Autowired
    private SessionRegistrationService service;

    // /**
    // * Endpoint for retrieving all available sessions.
    // *
    // * @return an iterable list of sessions
    // * @author Aurelia Bouliane
    // */
    // @GetMapping("/sessions")
    // public Iterable<Session> viewSessions() {
    // return service.viewSessions();
    // }

    /**
     * Endpoint for registering a customer to a session.
     * 
     * @param sessionId  the ID of the session
     * @param customerId the ID of the customer
     * @return a ResponseEntity containing the session registration or an error
     *         message
     * @author Aurelia Bouliane
     */
    @PostMapping("/sessionRegistrations")
    public ResponseEntity<?> registerForSession(@RequestBody String sessionId, @RequestBody String customerId) {
        SessionRegistration registration = null;
        try {
            int sessionIdInt = Integer.parseInt(sessionId);
            int customerIdInt = Integer.parseInt(customerId);
            registration = service.registerForSession(sessionIdInt, customerIdInt);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Bad integer value for sessionId or customerId");
        } catch (Exception e) {
            if (e.getMessage().contains("No session with given ID")
                    || e.getMessage().contains("No customer with given ID")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }
        return new ResponseEntity<>(registration, HttpStatus.OK);
    }

    /**
     * Endpoint for viewing a specific session registration by its ID.
     * 
     * @param pid the registration ID
     * @return a ResponseEntity containing the session registration or an error
     *         message
     * @author Aurelia Bouliane
     */
    @GetMapping("/sessionRegistrations/{pid}")
    public ResponseEntity<?> viewSpecificSession(@PathVariable("pid") String pid) {
        SessionRegistration registration = null;
        try {
            int pidInt = Integer.parseInt(pid);
            registration = service.viewSpecificSession(pidInt);
        } catch (NumberFormatException num) {
            return new ResponseEntity<>("Bad integer value for pid", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            if (e.getMessage().contains("No registration with given ID")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(registration, HttpStatus.OK);
    }

    /**
     * Endpoint for cancelling a registration by its ID.
     * 
     * @param id the registration ID
     * @return a ResponseEntity indicating success or failure
     * @author Aurelia Bouliane
     */
    @DeleteMapping("/sessionRegistrations/{id}")
    public ResponseEntity<?> cancelRegistration(@PathVariable("id") String id) {
        try {
            int idInt = Integer.parseInt(id);
            service.cancelRegistration(idInt);
        } catch (NumberFormatException num) {
            return new ResponseEntity<>("Bad integer value for id", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            if (e.getMessage().contains("No registration with given ID")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Endpoint for viewing all registrations for a specific customer.
     * 
     * @param customerId the ID of the customer
     * @return a ResponseEntity containing a list of session registrations or an
     *         error message
     * @author Aurelia Bouliane
     */
    @GetMapping("/sessionRegistrations/customers/{customerId}")
    public ResponseEntity<?> viewRegistrationsByCustomer(@PathVariable("customerId") String customerId) {
        List<SessionRegistration> registrations = new ArrayList<SessionRegistration>();
        try {
            int customerIdInt = Integer.parseInt(customerId);
            registrations = service.viewRegistrationsByCustomer(customerIdInt);
        } catch (NumberFormatException num) {
            return new ResponseEntity<>("Bad integer value for customerId", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            if (e.getMessage().contains("No customer with given ID")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }

    /**
     * Endpoint for viewing all registrations for a specific session.
     * 
     * @param sessionId the ID of the session
     * @return a ResponseEntity containing a list of session registrations or an
     *         error message
     * @author Aurelia Bouliane
     */
    @GetMapping("/sessionRegistrations/sessions/{sessionId}")
    public ResponseEntity<?> viewRegistrationsBySession(@PathVariable("sessionId") String sessionId) {
        List<SessionRegistration> registrations = new ArrayList<SessionRegistration>();
        try {
            int sessionIdInt = Integer.parseInt(sessionId);
            registrations = service.viewRegistrationsBySession(sessionIdInt);
        } catch (NumberFormatException num) {
            return new ResponseEntity<>("Bad integer value for sessionId", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            if (e.getMessage().contains("No session with given ID")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }
}
