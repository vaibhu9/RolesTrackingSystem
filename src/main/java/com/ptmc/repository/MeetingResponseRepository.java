package com.ptmc.repository;

import java.util.List;
import java.util.UUID;

import com.ptmc.entity.Member;
import com.ptmc.response.MeetingResponseDTO;
import com.ptmc.response.MemberResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ptmc.entity.MeetingResponse;

@Repository
public interface MeetingResponseRepository extends JpaRepository<MeetingResponse , UUID>{

    MeetingResponse findByMemberNumberAndMeetingNumber(String memberNumber, Long meetingNumber);

    @Query("""
    SELECT m 
    FROM MeetingResponse mr
    JOIN Member m ON
    mr.memberNumber = m.memberNumber
    WHERE mr.meetingNumber = :meetingNumber AND
    mr.response = 'Available'
    """)
    List<Member> findMembersWithAvailableResponse(@Param("meetingNumber") Long meetingNumber);

    List<MeetingResponse> findByMemberNumber(String memberNumber);

    @Query(value = """
    SELECT new com.ptmc.response.MeetingResponseDTO(
        m.meetingNumber, 
        m.meetingDate, 
        m.meetingTime, 
        m.meetingTheme, 
        mem.firstName, 
        mem.lastName, 
        mem.memberNumber,
        mr.response, 
        mr.reason, 
        mr.timestmp
    )
    FROM Meeting m
    CROSS JOIN Member mem
    LEFT JOIN MeetingResponse mr ON m.meetingNumber = mr.meetingNumber AND mem.memberNumber = mr.memberNumber
    WHERE m.meetingDate >= CURRENT_DATE
    AND m.deleted = false
    AND mem.deleted = false
    ORDER BY m.meetingNumber DESC, mem.lastName, mem.firstName
""")
    List<MeetingResponseDTO> findUpcomingMeetingsWithResponses();
}
