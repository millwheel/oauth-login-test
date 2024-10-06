package com.example.loginback.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class OAuth2ProviderUser implements ProviderUser {

    private Map<String, Object> attributes;
    private OAuth2User oAuth2User;
    private String registrationId;

    public OAuth2ProviderUser(Map<String, Object> attributes, OAuth2User oAuth2User, String registrationId) {
        this.attributes = attributes;
        this.oAuth2User = oAuth2User;
        this.registrationId = registrationId;
    }

    @Override
    public String getPassword() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String getEmail() {
        return (String) getAttributes().get("email");
    }

    @Override
    public String getProvider() {
        return registrationId;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .toList();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public OAuth2User getOAuth2User() {
        return oAuth2User;
    }
}
