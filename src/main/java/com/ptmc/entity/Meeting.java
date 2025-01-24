package com.ptmc.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Meeting {

    @Id
    private UUID meetingId;
    
    private Long meetingNumber;

    private String meetingTheme;

    private LocalDate meetingDate;

    private LocalDateTime meetingTime;

    private String memberShipTitle;

    private boolean deleted = Boolean.FALSE;
}
