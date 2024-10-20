package com.example.loginback.security.model;


import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleUser extends OAuth2ProviderUser {

    public GoogleUser(OAuth2User oAuth2User, String registrationId) {
        super(oAuth2User.getAttributes(), oAuth2User, registrationId);
    }

    @Override
    public String getPid() {
        return getAttributes().get("sub").toString();
    }

    @Override
    public String getName() {
        return (String) getAttributes().get("name");
    }

}
