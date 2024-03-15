package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.SessionResponseDTO;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.*;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service.SchedulingService;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto.SessionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ScheduleController {
    @Autowired
    private SchedulingService service;

    @PostMapping("/sessions")
    public SessionResponseDTO createSession(@RequestBody SessionDTO request) {
        Session newSession = service.createSession(request.getId(), request.getLength(), request.getStartTime(), request.getEndTime(), request.getDate(), request.getIsRepeating(), request.getMaxParticipants(), request.getClassType(), null);
        return new SessionResponseDTO(newSession);
    }
    @GetMapping("/sessions/{id}")
    public SessionResponseDTO findSessionById(@PathVariable int id) {
        Session session = service.findSessionById(id);
        return SessionResponseDTO.create(session);
    }
    @GetMapping("/sessions")
    public List<SessionResponseDTO> findAllEvents() {
        List<SessionResponseDTO> dtos = new ArrayList<SessionResponseDTO>();
        for (Session s : service.findAllSessions()) {
            dtos.add(SessionResponseDTO.create(s));
        }
        return dtos;
    }
}
