package com.gym.gym_booking.service;

import com.gym.gym_booking.model.Equipment;
import com.gym.gym_booking.repository.EquipmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository repository;

    public EquipmentService(EquipmentRepository repository) {
        this.repository = repository;
    }

    public Equipment addEquipment(Equipment eq) {
        eq.setStatus("AVAILABLE");
        return repository.save(eq);
    }

    public List<Equipment> getAll() {
        return repository.findAll();
    }
}