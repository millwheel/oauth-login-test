package com.example.loginback.security.model;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class KakaoUser extends OAuth2ProviderUser {

    public KakaoUser(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super((Map<String, Object>) oAuth2User.getAttributes().get("kakao_account"), oAuth2User, clientRegistration);
    }


    @Override
    public String getId() {
        return (String) getAttributes().get("id");
    }

    // TODO Enroll the Kakao App to biz app in Kakao Developer to activate the email and name
    @Override
    public String getUsername() {
        return "sample email";
    }

}
