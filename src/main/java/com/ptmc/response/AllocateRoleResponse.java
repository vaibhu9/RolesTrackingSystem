package com.ptmc.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AllocateRoleResponse {

    private String memberName;

    private String roleName;

    private Long meetingNumber;

}
