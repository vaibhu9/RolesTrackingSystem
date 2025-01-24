package com.ptmc.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ptmc.constant.MemberShipResponseMessage;
import com.ptmc.entity.MemberShip;
import com.ptmc.exception.MemberShipException;
import com.ptmc.mapper.MemberShipMapper;
import com.ptmc.repository.MemberShipRepository;
import com.ptmc.response.MemberShipResponse;
import com.ptmc.service.MemberShipService;

@Service
public class MemberShipServiceImpl implements MemberShipService {

    private final MemberShipRepository memberShipRepository;

    public MemberShipServiceImpl(MemberShipRepository memberShipRepository) {
        this.memberShipRepository = memberShipRepository;
    }

    @Override
    public MemberShip createMemberShip(MemberShip memberShip) {
        String workFlow = "MemberShipServiceImpl.createMemberShip";

        memberShip.setMemberShipId(UUID.randomUUID());
        MemberShip existingMemberShip = memberShipRepository.findByMemberShipTitle(memberShip.getMemberShipTitle());
        if (existingMemberShip != null) {
            throw new MemberShipException(MemberShipResponseMessage.MEMBERSHIP_ALREADY_EXITS.getMessage(memberShip.getMemberShipTitle()), HttpStatus.CONFLICT , HttpStatus.CONFLICT.value() , workFlow);
        }
        memberShip.setTimestamp(LocalDateTime.now());
        return memberShipRepository.save(memberShip);
    }

    @Override
    public MemberShip getMemberShip(String memberShipTitle) {
        String workFlow = "MemberShipServiceImpl.getMemberShip";

        MemberShip memberShip = memberShipRepository.findByMemberShipTitle(memberShipTitle);
        if (memberShip == null) {
            throw new MemberShipException(MemberShipResponseMessage.MEMBERSHIP_NOT_FOUND.getMessage(memberShipTitle), HttpStatus.NOT_FOUND , HttpStatus.NOT_FOUND.value() , workFlow);
        }
        return memberShip;
    }

    @Override
    public MemberShip updateMemberShip(String memberShipTitle, MemberShip updatedMemberShip) {
        String workFlow = "MemberShipServiceImpl.updateMemberShip";

        MemberShip existingMemberShip = memberShipRepository.findByMemberShipTitle(memberShipTitle);
        if (existingMemberShip == null) {
            throw new MemberShipException(MemberShipResponseMessage.MEMBERSHIP_NOT_FOUND.getMessage(memberShipTitle), HttpStatus.NOT_FOUND , HttpStatus.NOT_FOUND.value() , workFlow);
        }
        existingMemberShip.setMemberShipTitle(updatedMemberShip.getMemberShipTitle());
        existingMemberShip.setMemberShipDescription(updatedMemberShip.getMemberShipDescription());
        existingMemberShip.setMemberShipFees(updatedMemberShip.getMemberShipFees());
        existingMemberShip.setTimestamp(LocalDateTime.now());
        return memberShipRepository.save(existingMemberShip);
    }

    @Override
    public void deleteMemberShip(String memberShipTitle) {
        String workFlow = "MemberShipServiceImpl.deleteMemberShip";

        MemberShip existingMemberShip = memberShipRepository.findByMemberShipTitle(memberShipTitle);
        if (existingMemberShip == null) {
            throw new MemberShipException(MemberShipResponseMessage.MEMBERSHIP_NOT_FOUND.getMessage(memberShipTitle), HttpStatus.NOT_FOUND , HttpStatus.NOT_FOUND.value() , workFlow);
        }
        memberShipRepository.delete(existingMemberShip);
    }

    @Override
    public List<MemberShipResponse> getAllMemberShip() {
        String workFlow = "MemberShipServiceImpl.getAllMemberShip";
        try {
            List<MemberShip> responseMemberShips = memberShipRepository.findAll();
            return MemberShipMapper.memberShipToMemberShipResponse(responseMemberShips);
        } catch (MemberShipException e) {
            throw new MemberShipException(MemberShipResponseMessage.FAILED_TO_RETRIEVE_MEMBERSHIP_LIST.getMessage(), HttpStatus.CONFLICT , HttpStatus.CONFLICT.value() , workFlow);
        }
    }
}
