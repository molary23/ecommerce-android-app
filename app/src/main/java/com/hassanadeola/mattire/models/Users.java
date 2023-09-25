package com.hassanadeola.mattire.models;

import java.time.LocalDateTime;
import java.util.Calendar;

public class Users {

    private String username, email, phone, token;
    Long createdAt, updatedAt;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Users() {
    }



    public Users(String username, String email, String phone, String token) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.token = token;
        this.createdAt = Calendar.getInstance().getTimeInMillis();
    }
}
