package com.ptmc.repository;

import com.ptmc.entity.BackOutMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BackOutMemberRepository extends JpaRepository<BackOutMembers, UUID> {
}
