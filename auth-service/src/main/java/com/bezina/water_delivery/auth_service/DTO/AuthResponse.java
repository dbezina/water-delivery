package com.bezina.water_delivery.auth_service.DTO;

import com.bezina.water_delivery.auth_service.entity.enums.Role;

import java.util.Objects;

public class AuthResponse {
    private String token;
    private String userRole;

    public AuthResponse(String token, String userRole) {
        this.token = token;
        this.userRole = userRole;
    }

    public AuthResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthResponse that = (AuthResponse) o;
        return Objects.equals(token, that.token) && userRole == that.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, userRole);
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + token + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
