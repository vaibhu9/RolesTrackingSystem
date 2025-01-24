package com.ptmc.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ptmc.entity.Member;
import com.ptmc.response.MemberResponse;

public class MemberMapper {

    private MemberMapper(){

    }

    public static List<MemberResponse> memberToMemberResponse(List<Member> member)
    {
        List<MemberResponse> memberResponse  = new ArrayList<>();
        for(int i=0;i<member.size();i++)
        {
            MemberResponse response = new MemberResponse();
            response.setFirstName(member.get(i).getFirstName());
            response.setLastName(member.get(i).getLastName());
            response.setMemberNumber(member.get(i).getMemberNumber());
            memberResponse.add(response);
        }
        return memberResponse;
    }
}
