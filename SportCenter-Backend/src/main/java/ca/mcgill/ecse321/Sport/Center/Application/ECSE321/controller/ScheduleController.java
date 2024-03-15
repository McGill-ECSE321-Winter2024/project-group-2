package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.controller;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.service.SchedulingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {
    @Autowired
    private SchedulingService service;
}
