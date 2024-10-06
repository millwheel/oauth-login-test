package com.example.loginback.security;

import com.example.loginback.security.model.ProviderUser;
import com.example.loginback.repository.UserRepository;
import com.example.loginback.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomOidcUserService extends AbstractOAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    public CustomOidcUserService(UserRepository userRepository, UserService userService) {
        super(userRepository, userService);
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService = new OidcUserService();
        OidcUser oidcUser = oidcUserService.loadUser(userRequest);

        // --- Delete this code block if you don't need the saving the user into Database ---
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        ProviderUser providerUser = super.constructProviderUserFromOAuth2User(clientRegistration, oidcUser);
        super.register(clientRegistration, providerUser);
        // -----------------------------------------------------------------------------------

        return oidcUser;
    }
}
