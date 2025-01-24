package com.ptmc.mapper;

import java.util.ArrayList;
import java.util.List;

import com.ptmc.entity.WeekRoles;
import com.ptmc.response.WeekRoleResponse;

public class WeekRoleMapper {

    private WeekRoleMapper(){}

    public static List<WeekRoleResponse> weekRolesToWeekRoleResponse(List<WeekRoles> weekRoles)
    {
        List<WeekRoleResponse> responses = new ArrayList<>();
        for(int i=0;i<weekRoles.size();i++)
        {
            WeekRoleResponse weekRoleResponse = new WeekRoleResponse();
            weekRoleResponse.setRoleName(weekRoles.get(i).getTitle());
            responses.add(weekRoleResponse);
        }
        return responses;
    }
}
