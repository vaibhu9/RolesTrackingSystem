package com.ptmc.service;

import java.util.List;

import com.ptmc.entity.MeetingResponse;
import com.ptmc.entity.Member;
import com.ptmc.response.MeetingResponseDTO;
import com.ptmc.response.MemberResponse;

public interface MeetingResponseService {

    MeetingResponse createMeetingResponse(MeetingResponse request);

    MeetingResponse getMeetingResponse(String memberNumber, Long meetingNumber);

    MeetingResponse updateMeetingResponse(String memberNumber, Long meetingNumber, MeetingResponse request);

    void deleteMeetingResponse(String memberNumber, Long meetingNumber);

    List<MeetingResponse> getAllMeetingResponses();

    List<MemberResponse> getAllMembers(Long meetingNumber);

    List<MeetingResponse> getAllMemberResponse(String memberNumber);

    List<MeetingResponseDTO> getUpcomingMeetingsWithResponses();
}
