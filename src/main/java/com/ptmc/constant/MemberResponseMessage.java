package com.ptmc.constant;


public enum MemberResponseMessage {
    MEMBER_UPDATED_SUCCESSFULLY("Member with member number %s updated successfully"),
    MEMBER_DELETED_SUCCESSFULLY("Member with member number %s deleted successfully"),
    MEMBER_NOT_FOUND("Member with member number %s not found"),
    MEMBER_EXITS_ALREADY("Member with member number %s exits already"), 
    FAILED_TO_FETCH_MEMBER_LIST("Failed to fetch list of member");

    private final String message;

    public String getMessage() {
        return message;
    }

    private MemberResponseMessage(String message) {
        this.message = message;
    }
    
    public String getMessage(String memberNumber) {
        return String.format(message, memberNumber);
    }
}
