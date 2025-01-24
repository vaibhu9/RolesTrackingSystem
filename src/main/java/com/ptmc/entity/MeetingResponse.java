package com.ptmc.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class MeetingResponse {

    @Id
    private UUID meetingResponseId;

    private Long meetingNumber;

    private String memberNumber;

    private String response;

    private String reason;

    private LocalDateTime timestmp;
}
