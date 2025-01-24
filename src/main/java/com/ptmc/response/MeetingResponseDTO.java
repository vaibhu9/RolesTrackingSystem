package com.ptmc.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
@ToString
public class MeetingResponseDTO {
    private Long meetingNumber;
    private LocalDate meetingDate;
    private LocalDateTime meetingTime;
    private String meetingTheme;
    private String firstName;
    private String lastName;
    private String memberNumber;
    private String response;
    private String reason;
    private LocalDateTime timestmp;


    public MeetingResponseDTO(Long meetingNumber, LocalDate meetingDate, LocalDateTime meetingTime,
                              String meetingTheme, String firstName, String lastName, String memberNumber,
                              String response, String reason, LocalDateTime timestmp) {
        this.meetingNumber = meetingNumber;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.meetingTheme = meetingTheme;
        this.firstName = firstName;
        this.lastName = lastName;
        this.memberNumber = memberNumber;
        this.response = response;
        this.reason = reason;
        this.timestmp = timestmp;
    }

}
