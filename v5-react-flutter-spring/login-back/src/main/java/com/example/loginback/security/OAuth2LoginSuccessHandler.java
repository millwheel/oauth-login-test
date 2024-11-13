package com.example.loginback.security;

import com.example.loginback.security.jwt.JwtGenerator;
import com.example.loginback.security.model.ProviderUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtGenerator jwtGenerator;
    private final OAuth2UserConverter oAuth2UserConverter;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oauthToken.getPrincipal();
        String registrationId = oauthToken.getAuthorizedClientRegistrationId();
        ProviderUser providerUser = oAuth2UserConverter.constructProviderUserFromOAuth2User(registrationId, oAuth2User);
        oAuth2UserConverter.register(providerUser);
        String token = jwtGenerator.generateToken(providerUser, registrationId);

        response.setContentType("application/json");
        response.getWriter().write("{\"jwt\":\"" + token + "\"}");
        response.getWriter().flush();
    }

}
