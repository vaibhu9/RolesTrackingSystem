package com.ptmc.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
public class WeekRoles {
    
    @Id
    private UUID weekRolesId;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP" , name="timestmp" , insertable = false)
    private LocalDateTime timestamp;
    
    private String title;

    private String description;

    private Integer inActiveCount;

    private boolean deleted;
}
