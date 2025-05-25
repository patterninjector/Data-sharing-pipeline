package com.tub.ise.commondtos;

public class AuthResponse {
    private boolean authenticated;

    public AuthResponse() {
    }

    public AuthResponse(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}

