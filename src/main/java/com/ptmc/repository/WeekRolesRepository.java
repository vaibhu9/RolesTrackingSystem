package com.ptmc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ptmc.entity.WeekRoles;

import jakarta.transaction.Transactional;

import java.util.UUID;

public interface WeekRolesRepository extends JpaRepository<WeekRoles , UUID>{

    WeekRoles findByTitle(String title);

    @Modifying
    @Transactional
    @Query("UPDATE WeekRoles wr SET wr.deleted = true WHERE wr.title = :title")
    void deleteByTitle(String title);

    @Query("SELECT COUNT(wr) FROM WeekRoles wr WHERE wr.deleted = false")
    Integer getCountOfRoles();
}
