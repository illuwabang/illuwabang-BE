package com.gdsc.illuwabang.domain.user.google;

import com.gdsc.illuwabang.domain.user.TokenDto;
import com.gdsc.illuwabang.domain.user.User;
import com.gdsc.illuwabang.domain.user.UserResponseDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


public class CustomOauth2UserDetails implements UserDetails, OAuth2User {

    private final User user;
    private Map<String, Object> attributes;

    @Getter
    private final UserResponseDto userResponseDto;

    public CustomOauth2UserDetails(User user, Map<String, Object> attributes, UserResponseDto userResponseDto) {
        this.user = user;
        this.attributes = attributes;
        this.userResponseDto = userResponseDto;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> "ROLE_USER");
        return collection;
    }

    @Override
    public String getPassword() {
        return null; // OAuth2 인증은 비밀번호를 사용하지 않음
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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