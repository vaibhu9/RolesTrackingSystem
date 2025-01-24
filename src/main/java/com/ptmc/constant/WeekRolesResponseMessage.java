package com.ptmc.constant;



public enum WeekRolesResponseMessage {
    WEEK_ROLES_UPDATED_SUCCESSFULLY("Week Roles with %s title updated successfully"), 
    WEEK_ROLES_NOT_FOUND("Week Roles with %s title not found"),
    WEEK_ROLES_DELETED_SUCCESSFULLY("Week Roles with %s title deleted successfully"), 
    WEEK_ROLES_EXISTS_ALREADY("Week Roles with %s title already exits"), 
    WEEK_ROLES_FAILED_TO_FETCH("Failed to fetch list of week roles");

    private final String message;

    public String getMessage() {
        return message;
    }

    private WeekRolesResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage(String title) {
        return String.format(message, title);
    }

    

}
