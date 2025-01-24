package com.ptmc.service.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ptmc.entity.BackOutMembers;
import com.ptmc.entity.Member;
import com.ptmc.mapper.MemberMapper;
import com.ptmc.repository.BackOutMemberRepository;
import com.ptmc.response.MeetingResponseDTO;
import com.ptmc.response.MemberResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ptmc.entity.MeetingResponse;
import com.ptmc.repository.MeetingResponseRepository;
import com.ptmc.service.MeetingResponseService;

@Slf4j
@Service
public class MeetingResponseServiceImpl implements MeetingResponseService{

    private final MeetingResponseRepository meetingResponseRepository;

    private final BackOutMemberImpl backOutMemberImpl;

    public MeetingResponseServiceImpl(MeetingResponseRepository meetingResponseRepository , BackOutMemberImpl backOutMemberImpl) {
        this.meetingResponseRepository = meetingResponseRepository;
        this.backOutMemberImpl=backOutMemberImpl;
    }

    public MeetingResponse createMeetingResponse(MeetingResponse meetingResponse) {
        meetingResponse.setMeetingResponseId(UUID.randomUUID());
        meetingResponse.setTimestmp(LocalDateTime.now());
        log.info("Saving meeting-Response : {}",meetingResponse);
        return meetingResponseRepository.save(meetingResponse);
    }

    public MeetingResponse getMeetingResponse(String memberNumber, Long meetingNumber) {
        return meetingResponseRepository.findByMemberNumberAndMeetingNumber(memberNumber, meetingNumber);
    }

    public MeetingResponse updateMeetingResponse(String memberNumber, Long meetingNumber, MeetingResponse request) {
        MeetingResponse meetingResponse = meetingResponseRepository.findByMemberNumberAndMeetingNumber(memberNumber, meetingNumber);

        if (meetingResponse != null) {
            LocalDateTime existingTime = meetingResponse.getTimestmp();
            LocalDateTime nextTime = LocalDateTime.now();

            if ((Duration.between(existingTime, nextTime).toHours() <= 12) && (request.getResponse()!="Available")) {

                BackOutMembers backOutMember = new BackOutMembers();
                backOutMember.setBackOutMemberId(UUID.randomUUID());
                backOutMember.setMemberNumber(memberNumber);
                backOutMember.setBackOutDate(LocalDate.now());
                backOutMember.setBackOutTime(nextTime);

                backOutMemberImpl.createBackOutMember(backOutMember);
            }

            meetingResponse.setResponse(request.getResponse());
            meetingResponse.setTimestmp(nextTime);

            return meetingResponseRepository.save(meetingResponse);
        }
        return null;
    }

    public void deleteMeetingResponse(String memberNumber, Long meetingNumber) {
        MeetingResponse meetingResponse = meetingResponseRepository.findByMemberNumberAndMeetingNumber(memberNumber, meetingNumber);
        if (meetingResponse != null) {
            meetingResponseRepository.delete(meetingResponse);
        }
    }

    public List<MeetingResponse> getAllMeetingResponses() {
        return meetingResponseRepository.findAll();
    }

    @Override
    public List<MemberResponse> getAllMembers(Long meetingNumber) {
        List<Member> receivedMember = meetingResponseRepository.findMembersWithAvailableResponse(meetingNumber);
        return MemberMapper.memberToMemberResponse(receivedMember);
    }

    @Override
    public List<MeetingResponse> getAllMemberResponse(String memberNumber) {
        List<MeetingResponse> members = meetingResponseRepository.findByMemberNumber(memberNumber);
        return members;
    }

    @Override
    public List<MeetingResponseDTO> getUpcomingMeetingsWithResponses() {
        return meetingResponseRepository.findUpcomingMeetingsWithResponses();
    }
}
