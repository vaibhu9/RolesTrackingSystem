package com.ptmc.response;

public class LoginResponse {

    private boolean success;

    public LoginResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    
}
