package com.ptmc.service.impl;

import com.ptmc.constant.MeetingResponseMessage;
import com.ptmc.entity.Meeting;
import com.ptmc.exception.MeetingException;
import com.ptmc.repository.MeetingRepository;
import com.ptmc.service.MeetingService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;

    public MeetingServiceImpl(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    public Meeting createMeeting(Meeting meeting) {
        String workFlow = "MeetingServiceImpl.createMeeting";

        meeting.setMeetingId(UUID.randomUUID());
//        meeting.setMeetingDate(LocalDate.now());
//        meeting.setMeetingTime(LocalDateTime.now());

        Meeting existingMeeting = meetingRepository.findByMeetingNumber(meeting.getMeetingNumber());
        if (existingMeeting != null) {
            throw new MeetingException(MeetingResponseMessage.MEETING_EXITS_ALREADY.getMessage(meeting.getMeetingNumber()),
                    HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(), workFlow);
        }
        return meetingRepository.save(meeting);
    }

    @Override
    public Meeting getMeetingByNumber(Long meetingNumber) {
        String workFlow = "MeetingServiceImpl.getMeetingByNumber";

        Meeting meeting = meetingRepository.findByMeetingNumber(meetingNumber);
        if (meeting == null) {
            throw new MeetingException(MeetingResponseMessage.MEETING_NOT_FOUND.getMessage(meetingNumber),
                    HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(),  workFlow);
        }
        return meeting;
    }

    @Override
    public Meeting updateMeeting(Meeting meeting) {
        String workFlow = "MeetingServiceImpl.updateMeeting";

        Meeting existingMeeting = meetingRepository.findByMeetingNumber(meeting.getMeetingNumber());
        if (existingMeeting == null) {
            throw new MeetingException(MeetingResponseMessage.MEETING_NOT_FOUND.getMessage(meeting.getMeetingNumber()),
                    HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), workFlow);
        }
       meeting.setMeetingId(existingMeeting.getMeetingId());
        meeting.setDeleted(existingMeeting.isDeleted());
        meeting.setMemberShipTitle(existingMeeting.getMemberShipTitle());


        return meetingRepository.save(meeting);
    }

    @Override
    public void deleteMeeting(Long meetingNumber) {
        String workFlow = "MeetingServiceImpl.deleteMeeting";

        Meeting existingMeeting = meetingRepository.findByMeetingNumber(meetingNumber);
        if (existingMeeting == null) {
            throw new MeetingException(MeetingResponseMessage.MEETING_NOT_FOUND.getMessage(meetingNumber),
                    HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), workFlow);
        }
        existingMeeting.setDeleted(true);
        meetingRepository.save(existingMeeting);
    }

    @Override
    public List<Meeting> getAllMeetings() {
        String workFlow = "MeetingServiceImpl.getAllMeetings";

        try {
            return meetingRepository.findAll(Sort.by(Sort.Direction.DESC, "meetingNumber"));
        } catch (Exception e) {
            throw new MeetingException(MeetingResponseMessage.FAILED_TO_FETCH_MEETING_LIST.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), workFlow);
        }
    }

    @Override
    public Integer getMeetingCount() {
        return meetingRepository.getMeetingCount();
    }
}
