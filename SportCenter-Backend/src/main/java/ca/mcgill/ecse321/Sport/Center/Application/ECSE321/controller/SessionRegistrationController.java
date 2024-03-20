package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.SessionRegistration;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service.SessionRegistrationService;


@RestController
public class SessionRegistrationController {
    @Autowired
    private SessionRegistrationService service;
    
    @GetMapping("/sessions")
    public Iterable<Session> viewSessions() {
        return service.viewSessions();
    }

    @PutMapping("/register/session/{sessionId}/customer/{customerId}")
    public ResponseEntity<?> registerForSession(@PathVariable("sessionId") String sessionId, @PathVariable("customerId") int customerId) {
        try {
            int idAsString = Integer.parseInt(sessionId);
            SessionRegistration registration = service.registerForSession(idAsString, customerId);
            return ResponseEntity.ok(registration);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/session/{pid}")
    public ResponseEntity<?> viewSpecificSession(@PathVariable int pid) {
        try {
            SessionRegistration registration = service.viewSpecificSession(pid);
            return ResponseEntity.ok(registration);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelRegistration(@PathVariable int id) {
        try {
            service.cancelRegistration(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/registrations/customer/{customerId}")
    public List<SessionRegistration> viewRegistrationsByCustomer(@PathVariable int customerId) {
        return service.viewRegistrationsByCustomer(customerId);
    }

    @GetMapping("/registrations/session/{sessionId}")
    public List<SessionRegistration> viewRegistrationsBySession(@PathVariable int sessionId) {
        return service.viewRegistrationsBySession(sessionId);
    }
    
}
