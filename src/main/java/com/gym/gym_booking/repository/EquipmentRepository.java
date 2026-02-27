package com.gym.gym_booking.repository;

import com.gym.gym_booking.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    Optional<Equipment> findByQrCode(String qrCode);
}

//pt a  putea căuta sesiunile active pe un aparat.