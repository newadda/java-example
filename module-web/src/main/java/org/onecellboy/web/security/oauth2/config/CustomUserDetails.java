package org.onecellboy.web.security.oauth2.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User에 정보로 구현해야한다.
 * 필요에 따라 더 많은 정보를 가지고 이용할 수 있다.
 */
public class CustomUserDetails implements UserDetails {

    String username;
    String password;
    public CustomUserDetails()
    {
        username = "shh";
        password = "shh";
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection< GrantedAuthority> authorityList= new ArrayList<>();

        authorityList.add(new SimpleGrantedAuthority("USER"));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return new CustomPasswordEncoder().encode( password);
    }

    @Override
    public String getUsername() {
        return username;
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
