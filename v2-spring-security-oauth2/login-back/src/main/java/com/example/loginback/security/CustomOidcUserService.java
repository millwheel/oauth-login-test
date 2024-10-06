package com.example.loginback.security;

import com.example.loginback.security.model.ProviderUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final OAuth2UserConverter oAuth2UserConverter;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService = new OidcUserService();
        OidcUser oidcUser = oidcUserService.loadUser(userRequest);

        // --- Delete this code block if you don't need the saving the user into Database ---
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        ProviderUser providerUser = oAuth2UserConverter.constructProviderUserFromOAuth2User(registrationId, oidcUser);
        oAuth2UserConverter.register(providerUser);
        // -----------------------------------------------------------------------------------

        return oidcUser;
    }
}
