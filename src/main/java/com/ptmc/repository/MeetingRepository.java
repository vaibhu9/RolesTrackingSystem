package com.ptmc.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ptmc.entity.Meeting;
import org.springframework.data.jpa.repository.Query;

public interface MeetingRepository extends JpaRepository<Meeting , UUID>{

    Meeting findByMeetingNumber(Long meetingNumber);

    @Query("SELECT COUNT(m) FROM Meeting m WHERE m.deleted = false")
    Integer getMeetingCount();

    List<Meeting> findAll(Sort sort);
}
