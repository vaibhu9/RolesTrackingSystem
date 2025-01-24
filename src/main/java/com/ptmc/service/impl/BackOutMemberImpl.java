package com.ptmc.service.impl;

import com.ptmc.entity.BackOutMembers;
import com.ptmc.repository.BackOutMemberRepository;
import com.ptmc.service.BackOutMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackOutMemberImpl implements BackOutMember {

    @Autowired
    private BackOutMemberRepository backOutMemberRepository;

    @Override
    public BackOutMembers createBackOutMember(BackOutMembers backOutMember) {
        return backOutMemberRepository.save(backOutMember);
    }

}
