package com.anil.swiftBus.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.anil.swiftBus.entity.RolePermission;
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
        if (user.getRole() == null || user.getRole().getRolePermissions() == null) {
            return java.util.Collections.emptySet();
        }

        // Add role as ROLE_{ROLE_NAME} and all permissions as authorities
        java.util.Set<GrantedAuthority> authorities = user.getRole().getRolePermissions()
            .stream()
            .map(RolePermission::getPermission)
            .map(p -> new SimpleGrantedAuthority(p.getName()))
            .collect(Collectors.toSet());

        // Also add role-based authority so hasRole(...) works:
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getUserType().name()));

        return authorities;
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