package com.iw.application.security;

import com.iw.application.data.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    public final UserEntity userEntity;

    /**
     * Creates a new Instance for the given User
     * @param userEntity
     */
    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if (this.isEnabled()) {
            userEntity.getUserGroupsAsString().stream().forEach(userGroup ->
                    authorities.add(new SimpleGrantedAuthority(userGroup)));
        }
        return  authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getMail();
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

    /**
     * Returns the user for more details not needed by Spring Security
     * @return the user which this class represents
     */
    public UserEntity getUser() {
        return userEntity;
    }
}