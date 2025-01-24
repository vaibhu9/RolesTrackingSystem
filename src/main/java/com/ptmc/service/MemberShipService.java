package com.ptmc.service;

import java.util.List;

import com.ptmc.entity.MemberShip;
import com.ptmc.response.MemberShipResponse;

public interface MemberShipService {

    MemberShip createMemberShip(MemberShip memberShip);

    MemberShip getMemberShip(String memberShipTitle);

    MemberShip updateMemberShip(String memberShipTitle, MemberShip memberShip);

    void deleteMemberShip(String memberShipTitle);

    List<MemberShipResponse> getAllMemberShip();

}
