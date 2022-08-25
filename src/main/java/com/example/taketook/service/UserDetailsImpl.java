package com.example.taketook.service;

import com.example.taketook.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String avaUrl;
    private Double rating;
    private Set<String> userRatings;
    private List<String> commentIds;
    private Collection<? extends GrantedAuthority> roles;

    private String verifyCode;
    private Long verifyExpireDate;

    @JsonIgnore
    private String password;

    public UserDetailsImpl(String id, String name, String surname, String email, String phone, String address, String city, Double rating, Set<String> userRatings, Collection<? extends GrantedAuthority> roles, String password, String avaUrl, List<String> commentIds, String verifyCode, Long verifyExpireDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.rating = rating;
        this.userRatings = userRatings;
        this.roles = roles;
        this.password = password;
        this.avaUrl = avaUrl;
        this.commentIds = commentIds;
        this.verifyCode = verifyCode;
        this.verifyExpireDate = verifyExpireDate;
    }

    static UserDetailsImpl builder(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getPhone(), user.getAddress(), user.getCity(), user.getRating(), user.getUserRatings(), authorities, user.getPassword(), user.getAvaUrl(), user.getCommentIds(), user.getVerifyCode(), user.getVerifyExpireDate());
    }

    public String getId() {
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

    public String getAvaUrl() {
        return avaUrl;
    }

    public Set<String> getUserRatings() {
        return userRatings;
    }

    public Double getRating() {
        return rating;
    }

    public List<String> getCommentIds() {
        return commentIds;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public Long getVerifyExpireDate() {
        return verifyExpireDate;
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
        return email;
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
