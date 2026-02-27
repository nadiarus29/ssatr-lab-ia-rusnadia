package com.gym.gym_booking.controller;

import com.gym.gym_booking.model.Equipment;
import com.gym.gym_booking.repository.EquipmentRepository;
import com.gym.gym_booking.service.EquipmentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentService service;
    private final EquipmentRepository equipmentRepository;

    public EquipmentController(EquipmentService service, EquipmentRepository equipmentRepository) {
        this.service = service;
        this.equipmentRepository = equipmentRepository;
    }

    @PostMapping
    public Equipment add(@RequestBody Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @GetMapping
    public List<Equipment> all() {
        return service.getAll();
    }
}