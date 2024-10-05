package com.example.loginback.model;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class KakaoUser extends OAuth2ProviderUser {

    public KakaoUser(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super(oAuth2User.getAttributes(), oAuth2User, clientRegistration);
    }

    // TODO You should fill in
    @Override
    public String getId() {
        return "sample id";
    }

    @Override
    public String getUsername() {
        return "sample username";
    }
}
