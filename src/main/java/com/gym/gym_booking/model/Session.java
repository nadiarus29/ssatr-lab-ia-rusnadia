package com.gym.gym_booking.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String status; //ACTIVE, EXPIRED, CANCELLED

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;
//    astfel se creeaza o noua coloana in db
}

