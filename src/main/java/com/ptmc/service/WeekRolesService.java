package com.ptmc.service;

import java.util.List;

import com.ptmc.entity.WeekRoles;
import com.ptmc.response.WeekRoleResponse;

public interface WeekRolesService {

    WeekRoles createWeekRoles(WeekRoles weekRoles);

    WeekRoles updateWeekRoles(String title, WeekRoles weekRoles);

    void deleteWeekRoles(String title);

    WeekRoles getWeekRoles(String title);

    List<WeekRoleResponse> getAllWeekRoles();

    List<WeekRoles> getAllWeekRolesList();

    Integer getCountOfRoles();
}
