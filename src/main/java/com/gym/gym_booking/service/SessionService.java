package com.gym.gym_booking.service;

import com.gym.gym_booking.model.Session;
import com.gym.gym_booking.model.Equipment;
import com.gym.gym_booking.repository.SessionRepository;
import com.gym.gym_booking.repository.EquipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final EquipmentRepository equipmentRepository;
    // cozi pe echipament: equipmentId -> coada de sessionIds sau userIds
    private Map<Long, Queue<Long>> equipmentQueue = new ConcurrentHashMap<>();

    // Constructor pentru Dependency Injection
    public SessionService(SessionRepository sessionRepository,
                          EquipmentRepository equipmentRepository) {
        this.sessionRepository = sessionRepository;
        this.equipmentRepository = equipmentRepository;

    }

    // Returnează sesiunile ACTIVE pentru un echipament
    public List<Session> getActiveSessions(Long equipmentId) {
        return sessionRepository.findByEquipment_IdAndStatus(equipmentId, "ACTIVE");
    }

    // Pornirea unei sesiuni
    public Session startSession(Long equipmentId) {
        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        if (!equipment.isActive()) {
            throw new RuntimeException("Equipment is disabled");
        }

        if (equipment.isInUse()) {

            // adaugam in coada
            equipmentQueue.putIfAbsent(equipmentId, new LinkedList<>());
            Queue<Long> queue = equipmentQueue.get(equipmentId);

            // salvam  sessionId
            Session waitingSession = new Session();
            waitingSession.setEquipment(equipment);
            waitingSession.setStatus("WAITING");
            Session savedSession = sessionRepository.save(waitingSession);

            queue.add(savedSession.getId());

            return savedSession; // status WAITING
        }

        Session session = new Session();
        session.setStartTime(LocalDateTime.now());
        session.setStatus("ACTIVE");
        session.setEquipment(equipment);

        equipment.setInUse(true);

        equipmentRepository.save(equipment);
        return sessionRepository.save(session);
    }

    // Oprirea unei sesiuni
    public Session stopSession(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        if (!"ACTIVE".equals(session.getStatus())) {
            throw new RuntimeException("Session already stopped");
        }

        session.setStatus("FINISHED");
        session.setEndTime(LocalDateTime.now());

        Equipment equipment = session.getEquipment();
        equipment.setInUse(false);

        equipmentRepository.save(equipment);

        equipment = session.getEquipment();
        equipment.setInUse(false);

        equipmentRepository.save(equipment);

// verificam daca e coada
        Long eqId = equipment.getId();   // <--- ia ID-ul echipamentului de la session
        Queue<Long> queue = equipmentQueue.get(eqId);

        if (queue != null && !queue.isEmpty()) {
            Long nextSessionId = queue.poll();
            Session nextSession = sessionRepository.findById(nextSessionId)
                    .orElseThrow(() -> new RuntimeException("Session not found"));

            // pornim urmatoarea sesiune
            nextSession.setStatus("ACTIVE");
            nextSession.setStartTime(LocalDateTime.now());
            sessionRepository.save(nextSession);

            equipment.setInUse(true);
            equipmentRepository.save(equipment);
        }

        return sessionRepository.save(session);
    }
}