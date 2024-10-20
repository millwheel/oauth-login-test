package com.example.loginback.security.model;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class NaverUser extends OAuth2ProviderUser {

    public NaverUser(OAuth2User oAuth2User, String registrationId) {
        super((Map<String, Object>) oAuth2User.getAttributes().get("response"), oAuth2User, registrationId);
    }

    @Override
    public String getPid() {
        return "";
    }

    @Override
    public String getName() {
        return (String) getAttributes().get("name");
    }
}
