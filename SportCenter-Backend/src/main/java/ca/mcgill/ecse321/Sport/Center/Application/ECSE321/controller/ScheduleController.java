package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.SessionResponseDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service.SchedulingService;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.SessionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ScheduleController {
    @Autowired
    private SchedulingService service;

    @PostMapping("/sessions")
    public ResponseEntity<SessionResponseDTO> createSession(@RequestBody SessionDTO request) {
        Session newSession = service.createSession(request.getId(), request.getLength(), request.getStartTime(), request.getEndTime(), request.getDate(), request.getIsRepeating(), request.getMaxParticipants(), request.getClassType(), null);
        if (newSession == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(new SessionResponseDTO(newSession), HttpStatus.CREATED);
    }

    @GetMapping("/sessions/{id}")
    public ResponseEntity<?> findSessionById(@PathVariable int id) {
        Session session = service.findSessionById(id);
        if (session == null) {
            return new ResponseEntity<>("Session not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(SessionResponseDTO.create(session));
    }

    @GetMapping("/sessions")
    public List<SessionResponseDTO> findAllSessions() {
        List<SessionResponseDTO> dtos = new ArrayList<>();
        for (Session s : service.findAllSessions()) {
            dtos.add(SessionResponseDTO.create(s));
        }
        return dtos;
    }
}

