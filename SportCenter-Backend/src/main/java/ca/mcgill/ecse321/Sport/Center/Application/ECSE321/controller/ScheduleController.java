package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.SessionResponseDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service.SchedulingService;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.ClassTypeDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.SessionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Schedule Controller, which handles the HTTP requests related to sessions.
 * It provides endpoints for creating, updating, deleting, and retrieving sessions.
 *
 * @author Pei Yan
 */
@CrossOrigin(origins = "*")
@RestController
public class ScheduleController {
    @Autowired
    private SchedulingService service;

    /**
     * Creates a new session based on the provided session data.
     *
     * @param request the session data
     * @return the created session DTO
     * @author Pei Yan
     */
    @PostMapping("/sessions")
    public ResponseEntity<?> createSession(@RequestBody SessionDTO request) {
        SessionDTO newSessionDTO = null;
        try {
            newSessionDTO = service.createSession(request.getLength(), request.getStartTime(), request.getEndTime(), request.getDate(), request.getIsRepeating(), request.getMaxParticipants(), request.getClassType(), request.getInstructorId());
            //newSessionDTO.setInstructor(request.getInstructor());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newSessionDTO, HttpStatus.CREATED);
    }

    /**
     * Updates an existing session with the provided session data.
     *
     * @param id      the ID of the session to update
     * @param request the updated session data
     * @return the updated session DTO
     * @author Pei Yan
     */
    @PutMapping("/sessions/{id}")
    public ResponseEntity<?> updateSession (@PathVariable int id, @RequestBody SessionDTO request) {
        SessionDTO newSession = null;
        try {
            newSession = service.updateSession(id, request.getLength(), request.getStartTime(), request.getEndTime(), request.getDate(), request.getIsRepeating(), request.getMaxParticipants(), request.getClassType(), request.getInstructorId());
            return new ResponseEntity<>(newSession,HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes the session with the specified ID.
     *
     * @param id the ID of the session to delete
     * @return the HTTP response status
     * @author Pei Yan
     */
    @DeleteMapping("/sessions/{id}") 
    public ResponseEntity<?> deleteSession (@PathVariable int id) {
        try {
            service.deleteSession(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves the session with the specified ID.
     *
     * @param id the ID of the session to retrieve
     * @return the session DTO if found, or an error message if not found
     * @author Pei Yan
     */
    @GetMapping("/sessions/{id}")
    public ResponseEntity<?> findSessionById(@PathVariable int id) {
        Session session = service.findSessionById(id);
        if (session == null) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        SessionDTO sessionDTO = new SessionDTO(session);
        return new ResponseEntity<>(sessionDTO, HttpStatus.OK);
    }

    /**
     * Retrieves all sessions.
     *
     * @return a list of session response DTOs
     * @author Pei Yan
     */
    @GetMapping("/sessions")
    public ResponseEntity<?> findAllSessions() {
        List<SessionDTO> dtos = new ArrayList<>();
        try{
            for (Session s : service.findAllSessions()) {
                dtos.add(new SessionDTO(s));
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/classTypes/{approval}")
    public ResponseEntity<?> getAllClassTypes(@PathVariable String approval) {
        Boolean approvalBoolean = null;
        try{
            approvalBoolean = Boolean.valueOf(approval);
        }catch (Exception e){
            return new ResponseEntity<>("Invalid input: "+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        List<ClassTypeDTO> classTypes = service.viewClassTypeByApproval(approvalBoolean);
        return new ResponseEntity<>(classTypes, HttpStatus.OK);
    }


    @PutMapping("/classTypes/{name}")
    public ResponseEntity<?> suggestClassType(@PathVariable String name){
        try{
            service.suggestClassType(name);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    
    @PutMapping("/classTypes/{name}/{approval}")
    public ResponseEntity<?> approveDisapproveClassType(@PathVariable String name, @PathVariable String approval){
        Boolean approvalBoolean = null;
        try{
            approvalBoolean = Boolean.valueOf(approval);
        }catch (Exception e){
            return new ResponseEntity<>("Invalid input: "+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        if(approvalBoolean){
            try{
                service.approveClassType(name);
            }catch (Exception e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }else{
            try{
                service.rejectClassType(name);
            }catch (Exception e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    
}