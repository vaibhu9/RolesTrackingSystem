package com.ptmc.service.impl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.ptmc.entity.Meeting;
import com.ptmc.response.MemberResponse;
import com.ptmc.response.WeekRoleResponse;
import com.ptmc.service.MeetingResponseService;
import com.ptmc.service.WeekRolesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ptmc.constant.AllocateRoleResponseMessage;
import com.ptmc.entity.AllocateRole;
import com.ptmc.entity.Member;
import com.ptmc.exception.AllocateRoleException;
import com.ptmc.repository.AllocateRoleRepository;
import com.ptmc.repository.MemberRepository;
import com.ptmc.request.AllocateRoleRequest;
import com.ptmc.service.AllocateRoleService;


@Slf4j
@Service
public class AllocateRoleServiceImpl implements AllocateRoleService {

    private final AllocateRoleRepository allocateRoleRepository;
    private final MemberRepository memberRepository;
    private final MeetingResponseService meetingResponseService;
    private final WeekRolesService weekRolesService;

    public AllocateRoleServiceImpl(AllocateRoleRepository allocateRoleRepository, MemberRepository memberRepository, MeetingResponseService meetingResponseService, WeekRolesService weekRolesService) {
        this.allocateRoleRepository = allocateRoleRepository;
        this.memberRepository = memberRepository;
        this.meetingResponseService = meetingResponseService;
        this.weekRolesService = weekRolesService;
    }

    @Override
    public AllocateRole createAllocateRole(AllocateRole allocateRole) {
        String workFlow = "AllocateRoleServiceImpl.createAllocateRole";

        Long allocateNumber = allocateRoleRepository.getMaxAllocationNumber()+1;
        allocateRole.setAllocationNumber(allocateNumber);
        allocateRole.setAllocateRoleId(UUID.randomUUID());

        AllocateRole existingAllocateRole = allocateRoleRepository.findByAllocationNumber(allocateNumber);

        if (existingAllocateRole != null) {
            throw new AllocateRoleException(
                AllocateRoleResponseMessage.ALLOCATE_ROLE_EXITS_ALREADY.getMessage(allocateRole.getAllocationNumber()),
                HttpStatus.CONFLICT,
                HttpStatus.CONFLICT.value(),
                workFlow
            );
        }
        allocateRole.setTimestamp(LocalDateTime.now());
        return allocateRoleRepository.save(allocateRole);
    }

    @Override
    public void createAllocateRoles(List<AllocateRoleRequest> allocateRoleRequests) {
        String workFlow = "AllocateRoleServiceImpl.createAllocateRoles";
        Set<AllocateRole> uniqueRoles = new HashSet<>();

        for (AllocateRoleRequest request : allocateRoleRequests) {
           
            String[] nameParts = request.getMemberName().split(" ", 2);
            if (nameParts.length < 2) {
                throw new RuntimeException("Invalid member name format: " + request.getMemberName());
            }

            String firstName = nameParts[0];
            String lastName = nameParts[1];

            Member member = memberRepository.findByFirstNameAndLastName(firstName, lastName);

            if (member != null) {
                
                String memberNumber = member.getMemberNumber();

                AllocateRole allocateRole = new AllocateRole();
                allocateRole.setAllocateRoleId(UUID.randomUUID()); 
                allocateRole.setAllocationNumber(allocateRoleRepository.getMaxAllocationNumber()+1L);
                allocateRole.setMemberNumber(memberNumber);
                allocateRole.setRoleName(request.getRoleName());
                allocateRole.setMeetingNumber(request.getMeetingNumber());
                allocateRole.setDeleted(false);

                if (uniqueRoles.add(allocateRole)) {
                    
                    List<AllocateRole> existingRoles = allocateRoleRepository.findByMeetingNumberAndMemberNumberAndRoleName(
                            allocateRole.getMeetingNumber(), allocateRole.getMemberNumber(), allocateRole.getRoleName());

                    if (existingRoles.isEmpty()) {
                        allocateRoleRepository.save(allocateRole);
                    }
                }
            } else {
                throw new AllocateRoleException(AllocateRoleResponseMessage.FAILED_TO_FETCH_ALLOCATE_ROLE_LIST.getMessage(), HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(), workFlow);
            }
        }
    }

    @Override
    public List<AllocateRole> getRoleMemberPairs(Long meetingNumber) {
        return allocateRoleRepository.getPairs(meetingNumber);
    }

    @Override
    public List<Meeting> getAllMeetings(String memberNumber) {
        return allocateRoleRepository.findMeetings(memberNumber);
    }


    @Override
    public AllocateRole getAllocateRoleByNumber(Long allocationNumber) {
        String workFlow = "AllocateRoleServiceImpl.getAllocateRoleByNumber";

        AllocateRole allocateRole = allocateRoleRepository.findByAllocationNumber(allocationNumber);
        if (allocateRole == null) {
            throw new AllocateRoleException(
                AllocateRoleResponseMessage.ALLOCATE_ROLE_NOT_FOUND.getMessage(allocationNumber),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                workFlow
            );
        }
        return allocateRole;
    }

    @Override
    public AllocateRole updateAllocateRole(Long allocationNumber, AllocateRole updatedAllocateRole) {
        String workFlow = "AllocateRoleServiceImpl.updateAllocateRole";

        AllocateRole existingAllocateRole = allocateRoleRepository.findByAllocationNumber(allocationNumber);
        if (existingAllocateRole == null) {
            throw new AllocateRoleException(
                AllocateRoleResponseMessage.ALLOCATE_ROLE_NOT_FOUND.getMessage(allocationNumber),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                workFlow
            );
        }
        existingAllocateRole.setMemberNumber(updatedAllocateRole.getMemberNumber());
        existingAllocateRole.setRoleName(updatedAllocateRole.getRoleName());
        existingAllocateRole.setMeetingNumber(updatedAllocateRole.getMeetingNumber());
        existingAllocateRole.setTimestamp(LocalDateTime.now());
        allocateRoleRepository.save(existingAllocateRole);

        return existingAllocateRole;
    }

    @Override
    public void deleteAllocateRole(Long allocationNumber) {
        String workFlow = "AllocateRoleServiceImpl.deleteAllocateRole";

        AllocateRole existingAllocateRole = allocateRoleRepository.findByAllocationNumber(allocationNumber);
        if (existingAllocateRole == null) {
            throw new AllocateRoleException(
                AllocateRoleResponseMessage.ALLOCATE_ROLE_NOT_FOUND.getMessage(allocationNumber),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value(),
                workFlow
            );
        }
        allocateRoleRepository.delete(existingAllocateRole);
    }

    @Override
    public List<AllocateRole> getAllAllocateRoles() {
        String workFlow = "AllocateRoleServiceImpl.getAllAllocateRoles";

        try {
            Sort sort = Sort.by(Sort.Order.desc("meetingNumber"));
            return allocateRoleRepository.findAll(sort);
        } catch (Exception e) {
            throw new AllocateRoleException(
                AllocateRoleResponseMessage.FAILED_TO_FETCH_ALLOCATE_ROLE_LIST.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                workFlow
            );
        }
    }


    @Override
    public List<AllocateRole> allocateRoles(Long meetingNumber, List<WeekRoleResponse> roles) {
        List<AllocateRole> createdAllocations = new ArrayList<>();

        List<MemberResponse> availableMembersResponse = fetchAvailableMembers(meetingNumber);
        List<String> availableMembers = availableMembersResponse.stream()
                .map(MemberResponse::getMemberNumber)
                .collect(Collectors.toList());

        List<String> allRoles = roles.stream()
                .map(WeekRoleResponse::getRoleName)
                .collect(Collectors.toList());

        Map<String, Set<String>> memberRoleHistory = fetchMemberRoleHistory(availableMembers);

        Map<String, Integer> memberRoleAllocationCount = new HashMap<>();
        for (String member : availableMembers) {
            memberRoleAllocationCount.put(member, 0);
        }

        for (String role : allRoles) {
            String memberToAllocate = findMemberForRole(role, memberRoleHistory, availableMembers, memberRoleAllocationCount);

            AllocateRole newAllocation = new AllocateRole();
            newAllocation.setAllocateRoleId(UUID.randomUUID());
            newAllocation.setTimestamp(LocalDateTime.now());
            newAllocation.setDeleted(false);
            newAllocation.setRoleName(role);
            newAllocation.setAllocationNumber(allocateRoleRepository.getMaxAllocationNumber() + 1);
            newAllocation.setMeetingNumber(meetingNumber);
            newAllocation.setMemberNumber(memberToAllocate);

            AllocateRole allocateRole = allocateRoleRepository.save(newAllocation);
            createdAllocations.add(allocateRole);

            memberRoleHistory.get(memberToAllocate).add(role);
            memberRoleAllocationCount.put(memberToAllocate, memberRoleAllocationCount.get(memberToAllocate) + 1);
        }

        return createdAllocations;
    }

    @Override
    public List<AllocateRole> getAllMemberAllocation(String memberNumber) {
        return allocateRoleRepository.findByMemberNumber(memberNumber);
    }

    private String findMemberForRole(String role, Map<String, Set<String>> memberRoleHistory,
                                     List<String> availableMembers, Map<String, Integer> memberRoleAllocationCount) {
        return availableMembers.stream()
                .filter(member -> !memberRoleHistory.get(member).contains(role))
                .min(Comparator.comparingInt(memberRoleAllocationCount::get))
                .orElseThrow(() -> new RuntimeException("No available member found for role: " + role));
    }

    private List<MemberResponse> fetchAvailableMembers(Long meetingNumber) {
        return meetingResponseService.getAllMembers(meetingNumber);
    }

    private Map<String, Set<String>> fetchMemberRoleHistory(List<String> members) {
        Map<String, Set<String>> memberRoleHistory = new HashMap<>();
        for (String member : members) {
            List<AllocateRole> lastFourAttendedMeetingsRoles = allocateRoleRepository.findLastFourAttendedMeetingsRolesByMemberNumber(member);
            Set<String> roleHistory = lastFourAttendedMeetingsRoles.stream()
                    .map(AllocateRole::getRoleName)
                    .collect(Collectors.toSet());
            memberRoleHistory.put(member, roleHistory);
        }
        return memberRoleHistory;
    }

}