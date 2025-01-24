package com.ptmc.repository;


import java.util.List;
import java.util.UUID;

import com.ptmc.request.SearchCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ptmc.entity.Member;


@Repository
public interface MemberRepository extends JpaRepository<Member , UUID>{

    Member findByMemberNumber(String memberNumber);

    @Modifying
    @Query("UPDATE Member m SET m.deleted = true WHERE m.memberNumber = :memberNumber")
    void softDeleteMember(@Param("memberNumber") String memberNumber);

    Member findByFirstNameAndLastName(String firstName, String lastName);

    @Query("""
       SELECT m FROM Member m
        WHERE (:memberNumber IS NULL OR m.memberNumber = :memberNumber) AND
              (:firstName IS NULL OR m.firstName = :firstName) AND
              (:lastName IS NULL OR m.lastName = :lastName) AND
              (:phoneNumber IS NULL OR m.phoneNumber = :phoneNumber) AND
              (:emailAddress IS NULL OR m.emailAddress = :emailAddress) AND
              m.deleted = false
        """)
    Member findBySearchCriteria(@Param("memberNumber") String memberNumber,
                                      @Param("firstName") String firstName,
                                      @Param("lastName") String lastName,
                                      @Param("phoneNumber") Long phoneNumber,
                                      @Param("emailAddress") String emailAddress);

    @Query("SELECT m FROM Member m WHERE m.deleted = false")
    List<Member> findAllMembers();

    @Query("SELECT m FROM Member m WHERE m.deleted = true")
    List<Member> findDeletedMembers();

    @Query("SELECT COUNT(m) FROM Member m WHERE m.deleted = false")
    Integer getCountOfMembers();
}
