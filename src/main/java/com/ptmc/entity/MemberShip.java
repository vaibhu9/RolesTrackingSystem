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
public class MemberShip {
    
    @Id
    private UUID memberShipId;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP" , name="timestmp" , insertable = false)
    private LocalDateTime timestamp;

    private String memberShipTitle;

    private String memberShipDescription;

    private Integer memberShipFees;

    private boolean deleted = Boolean.FALSE;

}
