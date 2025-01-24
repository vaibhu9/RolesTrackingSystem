package com.ptmc.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ptmc.entity.MemberShip;
import com.ptmc.response.MemberShipResponse;

public class MemberShipMapper {

    private MemberShipMapper(){}

    public static List<MemberShipResponse> memberShipToMemberShipResponse(List<MemberShip> memberShips)
    {
        List<MemberShipResponse> memberShipResponse = new ArrayList<>();
        for(int i=0;i<memberShips.size();i++)
        {
            MemberShipResponse response = new MemberShipResponse();
            response.setMemberShipTitle(memberShips.get(i).getMemberShipTitle());
            memberShipResponse.add(response);
        }
        return memberShipResponse;
    }
}
