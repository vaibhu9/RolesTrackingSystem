package com.ptmc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ptmc.entity.MemberShip;

import java.util.UUID;

@Repository
public interface MemberShipRepository extends JpaRepository<MemberShip , UUID>{

    MemberShip findByMemberShipTitle(String memberShipTitle);

}
