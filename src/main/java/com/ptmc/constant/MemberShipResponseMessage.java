package com.ptmc.constant;

public enum MemberShipResponseMessage {
    MEMBERSHIP_NOT_FOUND("MemberShip with title %s not found"),
    MEMBERSHIP_ALREADY_EXITS("MemberShip with title %s already exits"),
    MEMBERSHIP_UPDATED_SUCCESSFULLY("MemberShip with title %s updated successfully"),
    MEMBERSHIP_DELETED_SUCCESSFULLY("MemberShip with title %s deleted successfully"),
    FAILED_TO_RETRIEVE_MEMBERSHIP_LIST("Failed to fetch membership list");

    private final String message;

    private MemberShipResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(String title)
    {   return String.format(title, message);
    }
}
