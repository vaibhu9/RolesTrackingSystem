package com.ptmc.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Attendance {

    @Id
    private Integer attendanceId;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP" , name="timestmp" , insertable = false)
    private LocalDateTime timestamp;
    
    private Long meetingNumber;

    private LocalDate dateOfMeeting;

    private String memberNumber;

    private boolean isPresent;

}
