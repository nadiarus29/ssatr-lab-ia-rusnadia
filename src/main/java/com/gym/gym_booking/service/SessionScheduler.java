package com.gym.gym_booking.service;

import com.gym.gym_booking.model.Session;
import com.gym.gym_booking.model.Equipment;
import com.gym.gym_booking.repository.SessionRepository;
import com.gym.gym_booking.repository.EquipmentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SessionScheduler {

    private final SessionRepository sessionRepository;
    private final EquipmentRepository equipmentRepository;

    public SessionScheduler(SessionRepository sessionRepository,
                            EquipmentRepository equipmentRepository) {
        this.sessionRepository = sessionRepository;
        this.equipmentRepository = equipmentRepository;
    }

    //  la fiecare 1 minut
    @Scheduled(fixedRate = 60000)
    public void expireSessions() {

        // Preluăm toate sesiunile ACTIVE
        List<Session> activeSessions = sessionRepository.findByStatus("ACTIVE");

        for (Session session : activeSessions) {
            Equipment equipment = session.getEquipment();

            // Calculăm timpul scurs
            long minutesUsed = java.time.Duration.between(
                    session.getStartTime(), LocalDateTime.now()
            ).toMinutes();

            // Comparăm cu maxSessionMinutes al echipamentului
            if (minutesUsed >= equipment.getMaxSessionMinutes()) {
                // Marcăm sesiunea ca EXPIRED
                session.setStatus("EXPIRED");
                session.setEndTime(LocalDateTime.now());
                sessionRepository.save(session);

                // Eliberăm aparatul
                equipment.setInUse(false);
                equipmentRepository.save(equipment);

                System.out.println("Session expired for equipment: " + equipment.getName());
            }
        }
    }
}