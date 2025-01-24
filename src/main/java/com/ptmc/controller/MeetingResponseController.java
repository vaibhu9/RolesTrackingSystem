package com.ptmc.controller;

import java.util.List;

import com.ptmc.entity.Member;
import com.ptmc.response.MeetingResponseDTO;
import com.ptmc.response.MemberResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptmc.entity.MeetingResponse;
import com.ptmc.service.MeetingResponseService;

@RestController
@RequestMapping("/api/meeting-response")
@CrossOrigin(origins = "*")
public class MeetingResponseController {

    private static final Logger logger = LoggerFactory.getLogger(MeetingResponseController.class);

    private final MeetingResponseService meetingResponseService;

    public MeetingResponseController(MeetingResponseService meetingResponseService) {
        this.meetingResponseService = meetingResponseService;
    }

    @PostMapping
    public ResponseEntity<MeetingResponse> createMeetingResponse(@RequestBody MeetingResponse request) {
        logger.info("Request received for creating meeting response with member number: {}", request.getMeetingNumber());
        MeetingResponse meetingResponse = meetingResponseService.createMeetingResponse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingResponse);
    }

    @GetMapping("/{member-number}/{meeting-number}")
    public ResponseEntity<MeetingResponse> getMeetingResponse(
            @PathVariable("member-number") String memberNumber,
            @PathVariable("meeting-number") Long meetingNumber) {
        logger.info("Request received for fetching meeting response with member number: {} and meeting number: {}", memberNumber, meetingNumber);
        MeetingResponse meetingResponse = meetingResponseService.getMeetingResponse(memberNumber, meetingNumber);
        return ResponseEntity.status(HttpStatus.OK).body(meetingResponse);
    }

    @PutMapping("/{member-number}/{meeting-number}")
    public ResponseEntity<MeetingResponse> updateMeetingResponse(
            @PathVariable("member-number") String memberNumber,
            @PathVariable("meeting-number") Long meetingNumber,
            @RequestBody MeetingResponse request) {
        logger.info("Request received for updating meeting response with member number: {} and meeting number: {}", memberNumber, meetingNumber);
        MeetingResponse updatedMeetingResponse = meetingResponseService.updateMeetingResponse(memberNumber, meetingNumber, request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMeetingResponse);
    }

    @DeleteMapping("/{member-number}/{meeting-number}")
    public ResponseEntity<String> deleteMeetingResponse(
            @PathVariable("member-number") String memberNumber,
            @PathVariable("meeting-number") Long meetingNumber) {
        logger.info("Request received for deleting meeting response with member number: {} and meeting number: {}", memberNumber, meetingNumber);
        meetingResponseService.deleteMeetingResponse(memberNumber, meetingNumber);
        return ResponseEntity.status(HttpStatus.OK).body("Meeting response deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<List<MeetingResponse>> getAllMeetingResponses() {
        logger.info("Request received for fetching all meeting responses");
        List<MeetingResponse> meetingResponsesList = meetingResponseService.getAllMeetingResponses();
        return ResponseEntity.status(HttpStatus.OK).body(meetingResponsesList);
    }

    @GetMapping("/{meeting-number}")
    public ResponseEntity<List<MemberResponse>> getAllMembers(@PathVariable("meeting-number")Long meetingNumber)
    {
        logger.info("Request received for fetching all members from meeting response : {}" , meetingNumber);
        List<MemberResponse> members = meetingResponseService.getAllMembers(meetingNumber);
        return ResponseEntity.status(HttpStatus.OK).body(members);
    }

    @GetMapping("/member/{member-number}")
    public ResponseEntity<List<MeetingResponse>> getAllMemberResponse(@PathVariable("member-number") String memberNumber)
    {
        logger.info("Request received for fetching member -response : {}",memberNumber);
        List<MeetingResponse> meetingResponses = meetingResponseService.getAllMemberResponse(memberNumber);
        return ResponseEntity.status(HttpStatus.OK).body(meetingResponses);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MeetingResponseDTO>> getUpcomingMeetingsWithResponses() {
        logger.info("Request received for get all info");
        List<MeetingResponseDTO> meetings = meetingResponseService.getUpcomingMeetingsWithResponses();
        return ResponseEntity.status(HttpStatus.OK).body(meetings);
    }
}
