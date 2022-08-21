package com.example.taketook.payload.request.UserController;

public class SignUpRequest {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String password;
    private String pin;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPassword() {
        return password;
    }

    public String getPin() {
        return pin;
    }
}
