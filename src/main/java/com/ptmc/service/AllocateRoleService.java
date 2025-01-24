package com.ptmc.service;

import java.util.List;


import com.ptmc.entity.AllocateRole;
import com.ptmc.entity.Meeting;
import com.ptmc.request.AllocateRoleRequest;
import com.ptmc.response.MemberResponse;
import com.ptmc.response.WeekRoleResponse;

public interface AllocateRoleService {

    List<AllocateRole> getAllAllocateRoles();

    void deleteAllocateRole(Long allocationNumber);

    AllocateRole updateAllocateRole(Long allocationNumber, AllocateRole allocateRole);

    AllocateRole getAllocateRoleByNumber(Long allocationNumber);

    AllocateRole createAllocateRole(AllocateRole allocateRole);

    void createAllocateRoles(List<AllocateRoleRequest> allocateRoles);

    List<AllocateRole> getRoleMemberPairs(Long meetingNumber);

    List<Meeting> getAllMeetings(String memberNumber);

    List<AllocateRole> allocateRoles(Long meetingNumber , List<WeekRoleResponse> roles);

    List<AllocateRole> getAllMemberAllocation(String memberNumber);
}
