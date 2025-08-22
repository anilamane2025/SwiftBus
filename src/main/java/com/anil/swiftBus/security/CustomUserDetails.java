package com.anil.swiftBus.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.anil.swiftBus.entity.User;

public class CustomUserDetails implements UserDetails {
    private final User user;   // entity reference

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // Expose full name
    public String getFullName() {
        return user.getFirstName().concat(" "+user.getLastName()).toUpperCase(); // assuming your entity has fullName
    }

    // Expose id if needed
    public Long getId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> (GrantedAuthority) () -> "ROLE_" + role.getRole())
                //.toList();
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return user.isEnabled(); }
}