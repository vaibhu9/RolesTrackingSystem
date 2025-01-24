package com.ptmc.constant;

public enum AllocateRoleResponseMessage {
    ALLOCATE_ROLE_UPDATED_SUCCESSFULLY("Allocate Role with allocation number %s updated successfully"),
    ALLOCATE_ROLE_DELETED_SUCCESSFULLY("Allocate Role with allocation %s deleted successfully"),
    ALLOCATE_ROLE_NOT_FOUND("Allocate Role with allocation %s not found"),
    ALLOCATE_ROLE_EXITS_ALREADY("Allocate Role with allocation %s exits already"), 
    FAILED_TO_FETCH_ALLOCATE_ROLE_LIST("Failed to fetch list of AllocateRole"), 
    LIST_OF_ALLOCATE_ROLE_SAVED_SUCCESSFULLY("List Saved SuccessFully");

    private final String message;

    public String getMessage() {
        return message;
    }

    public String getMessage(Long allocationNumber) {
        return String.format(message, allocationNumber);
    }

    private AllocateRoleResponseMessage(String message) {
        this.message = message;
    }

    
}
