package com.ptmc.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class AllocateRole {

    @Id
    private UUID allocateRoleId;

    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private Long allocationNumber;

    private String memberNumber;

    private String roleName;

    private Long meetingNumber;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP" , name="timestmp" , insertable = false)
    private LocalDateTime timestamp;

    private boolean deleted = Boolean.FALSE;
}
