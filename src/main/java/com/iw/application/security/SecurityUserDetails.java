package com.iw.application.security;

import com.iw.application.data.entity.account.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class SecurityUserDetails implements UserDetails {
    public final User user;

    /**
     * Creates a new Instance for the given User
     * @param user
     */
    public SecurityUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if (this.isEnabled()) {
            user.getUserGroupsAsString().stream().forEach(userGroup ->
                authorities.add(new SimpleGrantedAuthority(userGroup)));
        }
        return  authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getMail();
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
    public User getUser() {
        return user;
    }
}
