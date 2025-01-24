package com.ptmc.constant;

public enum MeetingResponseMessage {
    MEETING_UPDATED_SUCCESSFULLY("Meeting with meeting number %s updated successfully"),
    MEETING_DELETED_SUCCESSFULLY("Meeting with meeting number %s deleted successfully"),
    MEETING_NOT_FOUND("Meeting with meeting number %s not found"),
    MEETING_EXITS_ALREADY("Meeting with meeting number %s exits already"), 
    FAILED_TO_FETCH_MEETING_LIST("Failed to fetch list of meeting");

    private final String message;

    public String getMessage() {
        return message;
    }

    public String getMessage(Long allocationNumber) {
        return String.format(message, allocationNumber);
    }

    private MeetingResponseMessage(String message) {
        this.message = message;
    }
}
