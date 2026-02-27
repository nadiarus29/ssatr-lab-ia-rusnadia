package com.gym.gym_booking.controller;

import com.gym.gym_booking.model.Session;
import com.gym.gym_booking.service.SessionService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
@CrossOrigin // permite apeluri din frontend (JavaScript)
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    // START SESSION
    @PostMapping("/start/{equipmentId}")
    public Session startSession(@PathVariable Long equipmentId) {
        return sessionService.startSession(equipmentId);
    }

    // STOP SESSION
    @PostMapping("/stop/{sessionId}")
    public Session stopSession(@PathVariable Long sessionId) {
        return sessionService.stopSession(sessionId);
    }

    // VEZI SESIUNILE ACTIVE PENTRU UN APARAT
    @GetMapping("/active/{equipmentId}")
    public List<Session> getActiveSessions(@PathVariable Long equipmentId) {
        return sessionService.getActiveSessions(equipmentId);
    }
}