package com.example.loginback.model;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class KakaoUser extends OAuth2ProviderUser {

    public KakaoUser(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super(oAuth2User.getAttributes(), oAuth2User, clientRegistration);
    }

    // id와 username 은 제공자 별로 차이가 난다.
    @Override
    public String getId() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
