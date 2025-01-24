package com.ptmc.service;

import java.util.List;

import com.ptmc.entity.Meeting;

public interface MeetingService {

    Meeting createMeeting(Meeting meeting);

    Meeting getMeetingByNumber(Long meetingNumber);

    Meeting updateMeeting(Meeting meeting);

    void deleteMeeting(Long meetingNumber);

    List<Meeting> getAllMeetings();

    Integer getMeetingCount();
}
