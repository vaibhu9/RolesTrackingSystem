package com.ptmc.repository;

import java.util.List;
import java.util.UUID;

import com.ptmc.entity.Meeting;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ptmc.entity.AllocateRole;

@Repository
public interface AllocateRoleRepository extends JpaRepository<AllocateRole , UUID>{

    AllocateRole findByAllocationNumber(Long allocationNumber);

    @Query("SELECT COALESCE(MAX(a.allocationNumber), 0) FROM AllocateRole a")
    Long getMaxAllocationNumber();

    @Query("SELECT a FROM AllocateRole a WHERE a.meetingNumber = :meetingNumber AND a.memberNumber = :memberNumber AND a.roleName = :roleName")
    List<AllocateRole> findByMeetingNumberAndMemberNumberAndRoleName(@Param("meetingNumber") Long meetingNumber, 
                                                                @Param("memberNumber") String memberNumber, 
                                                                @Param("roleName") String roleName);


    @Query("SELECT a FROM AllocateRole a WHERE a.meetingNumber = :meetingNumber")
    List<AllocateRole> getPairs(@Param("meetingNumber") Long meetingNumber);

    @Query("""
        SELECT m
        FROM AllocateRole ar
        JOIN Meeting m ON ar.meetingNumber = m.meetingNumber
        WHERE ar.memberNumber = :memberNumber
          AND ar.deleted = false
          AND m.deleted = false
       """)
    List<Meeting> findMeetings(@Param("memberNumber") String memberNumber);

    List<AllocateRole> findByMeetingNumber(Long meetingNumber);

    List<AllocateRole> findByMemberNumber(String memberNumber);

    @Query("SELECT ar FROM AllocateRole ar WHERE ar.memberNumber = :memberNumber " +
            "ORDER BY ar.meetingNumber DESC")
    List<AllocateRole> findLastFourAttendedMeetingsRolesByMemberNumber(@Param("memberNumber") String memberNumber, Pageable pageable);

    default List<AllocateRole> findLastFourAttendedMeetingsRolesByMemberNumber(String memberNumber) {
        return findLastFourAttendedMeetingsRolesByMemberNumber(memberNumber, PageRequest.of(0, 4));
    }
}
