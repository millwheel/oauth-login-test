package com.example.loginback.security.model;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class KakaoUser extends OAuth2ProviderUser {

    public KakaoUser(OAuth2User oAuth2User, String registrationId) {
        super((Map<String, Object>) oAuth2User.getAttributes().get("kakao_account"), oAuth2User, registrationId);
    }


    @Override
    public String getId() {
        return getOAuth2User().getAttributes().get("id").toString();
    }

    // TODO Enroll the biz app in Kakao Developer to activate the email information from kakao
    @Override
    public String getUsername() {
        return "sample email";
    }

    @Override
    public String getName() {
        Map<String, Object> profile = (Map) getAttributes().get("profile");
        return (String) profile.get("nickname");
    }

}
