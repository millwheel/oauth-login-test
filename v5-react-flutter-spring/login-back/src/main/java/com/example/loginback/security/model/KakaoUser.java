package com.example.loginback.security.model;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class KakaoUser extends OAuth2ProviderUser {

    public KakaoUser(OAuth2User oAuth2User, String registrationId) {
        super(oAuth2User.getAttributes(), oAuth2User, registrationId);
    }

    @Override
    public String getPid() {
        return getAttributes().get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) getAttributes().get("kakao_account");
        Map<String, Object> profile = (Map) kakaoAccount.get("profile");
        return (String) profile.get("nickname");
    }

}
