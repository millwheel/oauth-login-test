package com.example.loginback.security.model;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class KakaoUser extends OAuth2ProviderUser {

    public KakaoUser(OAuth2User oAuth2User, String registrationId) {
        super((Map<String, Object>) oAuth2User.getAttributes().get("kakao_account"), oAuth2User, registrationId);
    }

    @Override
    public String getPid() {
        return "";
    }

    @Override
    public String getName() {
        Map<String, Object> profile = (Map) getAttributes().get("profile");
        return (String) profile.get("nickname");
    }

}
