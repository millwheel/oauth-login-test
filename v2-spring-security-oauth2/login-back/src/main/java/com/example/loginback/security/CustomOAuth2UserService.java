package com.example.loginback.security;

import com.example.loginback.security.model.ProviderUser;
import com.example.loginback.repository.UserRepository;
import com.example.loginback.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomOAuth2UserService extends AbstractOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    public CustomOAuth2UserService(UserRepository userRepository, UserService userService) {
        super(userRepository, userService);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        // --- Delete this code block if you don't need the saving the user into Database ---
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        ProviderUser providerUser = super.constructProviderUserFromOAuth2User(clientRegistration, oAuth2User);
        super.register(clientRegistration, providerUser);
        // -----------------------------------------------------------------------------------

        return oAuth2User;
    }
}
