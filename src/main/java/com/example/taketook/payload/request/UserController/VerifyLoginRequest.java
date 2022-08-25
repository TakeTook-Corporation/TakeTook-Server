package com.example.taketook.payload.request.UserController;

public class VerifyLoginRequest {
    private String phone;
    private String verifyCode;

    public String getPhone() {
        return phone;
    }

    public String getVerifyCode() {
        return verifyCode;
    }
}
