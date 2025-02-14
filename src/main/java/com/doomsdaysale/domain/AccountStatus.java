package com.doomsdaysale.domain;

public enum AccountStatus {
    PENDING_VERIFICATION, // User account is created but not verified
    ACTIVE, // User account is active
    SUSPENDED, // User account is temporarily suspended possibly due to terms and agreement violations
    DEACTIVATED, //User account is deactivated
    BANNED, //User account is permanently banned due to severe violations of terms and agreement violations
    CLOSED //User account is permanently closed due to users request.
}
