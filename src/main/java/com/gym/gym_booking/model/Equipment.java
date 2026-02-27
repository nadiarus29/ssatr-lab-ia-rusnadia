package com.gym.gym_booking.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "max_session_minutes")
    private Integer maxSessionMinutes;

    private String status; // AVAILABLE, IN_USE, MAINTENANCE

    private boolean active = true;

    @Column(name = "in_use")
    private boolean inUse = false;
}