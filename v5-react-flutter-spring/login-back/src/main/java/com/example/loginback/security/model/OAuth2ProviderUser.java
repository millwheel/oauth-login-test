package com.example.loginback.security.model;

import com.example.loginback.security.authority.AuthorityFormatter;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class OAuth2ProviderUser implements ProviderUser {

    private String registrationId;
    private Map<String, Object> attributes;
    private String sub;
    private String email;
    private String name;
    private List<String> authorities;

    public OAuth2ProviderUser(Map<String, Object> attributes, OAuth2User oAuth2User, String registrationId) {
        this.registrationId = registrationId;
        this.attributes = attributes;
        this.sub = UUID.randomUUID().toString();
        this.email = attributes.get("email") != null ? attributes.get("email").toString() : null;
        this.name = attributes.get("name") != null ? attributes.get("name").toString() : null;
        this.authorities = oAuth2User.getAuthorities().stream()
                .map(authority -> AuthorityFormatter.trimAuthorityString(authority.getAuthority()))
                .toList();
    }

    @Override
    public String getSub(){
        return sub;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getProvider() {
        return registrationId;
    }

    @Override
    public List<String> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

}
