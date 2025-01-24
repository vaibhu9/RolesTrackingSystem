package com.ptmc.controller;

import com.ptmc.constant.MeetingResponseMessage;
import com.ptmc.entity.Meeting;
import com.ptmc.service.MeetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meeting")
@CrossOrigin(origins = "*")
public class MeetingController {

    private static final Logger logger = LoggerFactory.getLogger(MeetingController.class);

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping
    public ResponseEntity<Meeting> createMeeting(@RequestBody Meeting meeting) {
        logger.info("Request received for creating meeting with number: {}", meeting.getMeetingNumber());
        Meeting createdMeeting = meetingService.createMeeting(meeting);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMeeting);
    }

    @GetMapping("/{meeting-number}")
    public ResponseEntity<Meeting> getMeeting(@PathVariable("meeting-number") Long meetingNumber) {
        logger.info("Request received for fetching meeting with number: {}", meetingNumber);
        Meeting meeting = meetingService.getMeetingByNumber(meetingNumber);
        return ResponseEntity.status(HttpStatus.OK).body(meeting);
    }

    @PutMapping("/{meeting-number}")
    public ResponseEntity<Meeting> updateMeeting(@PathVariable("meeting-number") Long meetingNumber,
            @RequestBody Meeting meeting) {
        logger.info("Request received for updating meeting with number: {}", meetingNumber);
        meeting.setMeetingNumber(meetingNumber);
        Meeting updatedMeeting = meetingService.updateMeeting(meeting);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMeeting);
    }

    @DeleteMapping("/{meeting-number}")
    public ResponseEntity<String> deleteMeeting(@PathVariable("meeting-number") Long meetingNumber) {
        logger.info("Request received for deleting meeting with number: {}", meetingNumber);
        meetingService.deleteMeeting(meetingNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(MeetingResponseMessage.MEETING_DELETED_SUCCESSFULLY.getMessage(meetingNumber));
    }

    @GetMapping
    public ResponseEntity<List<Meeting>> getAllMeetings() {
        logger.info("Request received for fetching all meetings");
        List<Meeting> meetingsList = meetingService.getAllMeetings();
        return ResponseEntity.status(HttpStatus.OK).body(meetingsList);
    }

    @GetMapping("/meeting-count")
    public ResponseEntity<Integer> getMeetingCount()
    {
        Integer count = meetingService.getMeetingCount();
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }
}
