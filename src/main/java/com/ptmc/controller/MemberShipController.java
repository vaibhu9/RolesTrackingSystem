package com.ptmc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptmc.constant.MemberShipResponseMessage;
import com.ptmc.entity.MemberShip;
import com.ptmc.response.MemberShipResponse;
import com.ptmc.service.MemberShipService;

@RestController
@RequestMapping("/api/membership")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class MemberShipController {

    private static final Logger logger = LoggerFactory.getLogger(MemberShipController.class);

    private final MemberShipService memberShipService;

    public MemberShipController(MemberShipService memberShipService) {
        this.memberShipService = memberShipService;
    }

    @PostMapping
    public ResponseEntity<MemberShip> createMemberShip(@RequestBody MemberShip memberShip)
    {   
        logger.info("Request received for creation of membership {}" , memberShip.getMemberShipTitle());
        MemberShip memberShipResponse = memberShipService.createMemberShip(memberShip);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(memberShipResponse);
    }

    @GetMapping("/{membership-title}")
    public ResponseEntity<MemberShip> getMemberShip(@PathVariable("membership-title")String memberShipTitle)
    {
        logger.info("Request received for fetch membership info");
        MemberShip memberShipResponse = memberShipService.getMemberShip(memberShipTitle);
        return ResponseEntity.status(HttpStatus.FOUND.value()).body(memberShipResponse);
    }

    @PutMapping("/{membership-title}")
    public ResponseEntity<MemberShip> updateMemberShip(@PathVariable("membership-title")String memberShipTitle , @RequestBody MemberShip memberShip)
    {
        logger.info("Request received for update membership Info");
        MemberShip memberShipResponse = memberShipService.updateMemberShip(memberShipTitle , memberShip);
        return ResponseEntity.status(HttpStatus.OK.value()).body(memberShipResponse);
    }

    @DeleteMapping("/{membership-title}")
    public ResponseEntity<String> deleteMemberShip(@PathVariable("membership-title")String memberShipTitle)
    {
        logger.info("Request received for delete membership Info");
        memberShipService.deleteMemberShip(memberShipTitle);
        return ResponseEntity.status(HttpStatus.OK.value()).body(MemberShipResponseMessage.MEMBERSHIP_DELETED_SUCCESSFULLY.getMessage(memberShipTitle));
    }

    @GetMapping
    public ResponseEntity<List<MemberShipResponse>> getAllMemberShip()
    {
        logger.info("Request received for get all membership Info");
        List<MemberShipResponse> memberShipList = memberShipService.getAllMemberShip();
        logger.info("Data Fetched : {}",memberShipList.getFirst().getMemberShipTitle());
        return ResponseEntity.status(HttpStatus.OK.value()).body(memberShipList);
    }
}
