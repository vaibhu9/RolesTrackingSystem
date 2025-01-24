package com.ptmc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SearchCriteria {

    private String memberNumber;

    private String firstName;

    private String lastName;

    private Long phoneNumber;

    private String emailAddress;
}
