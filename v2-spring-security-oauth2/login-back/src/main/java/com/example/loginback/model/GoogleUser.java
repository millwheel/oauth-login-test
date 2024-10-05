package com.example.loginback.model;


import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleUser extends OAuth2ProviderUser {

    public GoogleUser(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super(oAuth2User.getAttributes(), oAuth2User, clientRegistration);
    }

    // id and username is unique by provider
    @Override
    public String getId() {
        return (String) getAttributes().get("sub");
    }

    // id == username in case of google
    @Override
    public String getUsername() {
        return (String) getAttributes().get("sub");
    }

}
