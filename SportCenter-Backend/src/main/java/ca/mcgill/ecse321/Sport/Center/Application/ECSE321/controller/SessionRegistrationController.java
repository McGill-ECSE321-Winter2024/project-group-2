package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Session;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.SessionRegistration;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service.SessionRegistrationService;


@RestController
public class SessionRegistrationController {
    @Autowired
    private SessionRegistrationService service;
    
    @GetMapping("/session")
    public Iterable<Session> viewSessions() {
        return service.viewSessions();
    }

    @PutMapping("/register/{aId}")
    public SessionRegistration registerForSession(@PathVariable int aId, @RequestBody Session aSession, @RequestBody Customer aCustomer) {
        return service.registerForSession(aId, aSession, aCustomer);
    }


    @GetMapping("/session/{pid}")
    public SessionRegistration viewSpecificSession(@PathVariable int pid) throws Exception {
        return service.viewSpecificSession(pid);
    }

    @DeleteMapping("/cancel/{id}")
    public void cancelRegistration(@PathVariable int id) {
        service.cancelRegistration(id);
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
