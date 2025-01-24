package com.ptmc.request;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class AllocateRoleRequest {

    private Long allocationNumber;

    private String memberName;

    private String roleName;

    private Long meetingNumber;

}
