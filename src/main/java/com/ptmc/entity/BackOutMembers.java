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
public class BackOutMembers {

    @Id
    private UUID backOutMemberId;

    private String memberNumber;

    private LocalDate backOutDate;

    private LocalDateTime backOutTime;
}
