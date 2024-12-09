package com.frankefelipee.myissuertracker.auth;

public record AuthResponse(String token, Long expiresIn) {

}
