package com.gym.gym_booking.repository;

import com.gym.gym_booking.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    //  pentru relația ManyToOne
    List<Session> findByStatus(String status);

    List<Session> findByEquipmentIdAndStatus(Long equipmentId, String status);

    List<Session> findByEquipment_IdAndStatus(Long equipmentId, String status);
}