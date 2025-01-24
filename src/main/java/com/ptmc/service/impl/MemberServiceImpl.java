package com.ptmc.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.ptmc.request.SearchCriteria;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ptmc.constant.MemberResponseMessage;
import com.ptmc.entity.Member;
import com.ptmc.exception.MemberException;
import com.ptmc.mapper.MemberMapper;
import com.ptmc.repository.MemberRepository;
import com.ptmc.response.LoginResponse;
import com.ptmc.response.MemberResponse;
import com.ptmc.service.MemberService;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService{

    private MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public Member createMember(Member member) {
        String workFlow = "MemberServiceImpl.createMember";

        member.setMemberId(UUID.randomUUID());
        member.setMemberNumber(member.getFirstName()+member.getPhoneNumber());
        member.setTimestamp(LocalDateTime.now());
        
        Member existingMember = memberRepository.findByMemberNumber(member.getMemberNumber());
        if (existingMember != null) {
            throw new MemberException(MemberResponseMessage.MEMBER_EXITS_ALREADY.getMessage(member.getMemberNumber()),
                    HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, workFlow);
        }
        return  memberRepository.save(member);
    }


    @Override
    public Member getMember(String memberNumber) {
        String workFlow = "MemberServiceImpl.getMember";

        Member existingMember = memberRepository.findByMemberNumber(memberNumber);
        if (existingMember == null) {
            throw new MemberException(MemberResponseMessage.MEMBER_NOT_FOUND.getMessage(memberNumber),
                    HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, workFlow);
        }
        return memberRepository.findByMemberNumber(memberNumber);
    }


    @Override
    public void updateMember(String memberNumber, Member member) {
        String workFlow = "MemberServiceImpl.updateMember";

        Member existingMember = memberRepository.findByMemberNumber(memberNumber);
        if (existingMember == null) {
            throw new MemberException(MemberResponseMessage.MEMBER_NOT_FOUND.getMessage(memberNumber),
                    HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, workFlow);
        }
        member.setMemberId(existingMember.getMemberId());
        member.setMemberNumber(existingMember.getMemberNumber());
        member.setDeleted(existingMember.isDeleted());
        memberRepository.save(member);
    }

    @Transactional
    @Override
    public void deleteMember(String memberNumber) {
        String workFlow = "MemberServiceImpl.deleteMember";

        Member existingMember = memberRepository.findByMemberNumber(memberNumber);

        if (existingMember == null) {
            log.info("Existing member in Delete : {}",existingMember);
            throw new MemberException(MemberResponseMessage.MEMBER_NOT_FOUND.getMessage(memberNumber),
                    HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, workFlow);
        }
        log.info("Soft-Deleting Delete Member");
        memberRepository.softDeleteMember(memberNumber);
    }


    @Override
    public List<MemberResponse> getAllMembers() {
        String workFlow = "MemberServiceImpl.getAllMembers()";
        try{
            List<Member> member = memberRepository.findAllMembers();
             return MemberMapper.memberToMemberResponse(member);
        }catch(MemberException exception){
            throw new MemberException(MemberResponseMessage.FAILED_TO_FETCH_MEMBER_LIST.getMessage(), HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, workFlow);
        }
    }

    @Override
    public List<Member> getAllMembersList() {
        String workFlow = "MemberServiceImpl.getAllMembers()";
        try{
            return memberRepository.findAllMembers();
        }catch(MemberException exception){
            throw new MemberException(MemberResponseMessage.FAILED_TO_FETCH_MEMBER_LIST.getMessage(), HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, workFlow);
        }
    }


    @Override
    public boolean validateMember(String memberNumber) {
       if(memberRepository.findByMemberNumber(memberNumber)!=null)
            return true;
        else 
            return false;
    }

    @Override
    public Member getMemberbySearchCriteria(SearchCriteria searchCriteria) {
        try {
            // Call the repository method
            return memberRepository.findBySearchCriteria(
                    searchCriteria.getMemberNumber(),
                    searchCriteria.getFirstName(),
                    searchCriteria.getLastName(),
                    searchCriteria.getPhoneNumber(),
                    searchCriteria.getEmailAddress()
            );
        } catch (Exception e) {
            // Log the error (optional)
            log.error("Error fetching member by search criteria", e);
            throw new RuntimeException("An unexpected error occurred while searching for members.");
        }
    }

    @Override
    public List<Member> getDeletedMembers() {
        return memberRepository.findDeletedMembers();
    }

    @Override
    public Integer getCountOfMembers() {
        return memberRepository.getCountOfMembers();
    }
}
