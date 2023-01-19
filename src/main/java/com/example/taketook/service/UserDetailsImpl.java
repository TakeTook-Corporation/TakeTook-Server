package com.example.taketook.service;

import com.example.taketook.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String address;
    private String city;
    private Collection<? extends GrantedAuthority> roles;
    @JsonIgnore
    private String password;

    public UserDetailsImpl(Integer id, String name, String surname,
                           String email, String phone, String address,
                           String city, Collection<? extends GrantedAuthority> roles,
                           String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.roles = roles;
        this.password = password;
    }

    static UserDetailsImpl builder(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(
                                    user.getId(),
                                    user.getName(),
                                    user.getSurname(),
                                    user.getEmail(),
                                    user.getPhone(),
                                    user.getAddress(),
                                    user.getCity(),
                                    authorities,
                                    user.getPassword()
                                 );
    }

    public Integer getId() {
        return id;
    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name + " " + surname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
