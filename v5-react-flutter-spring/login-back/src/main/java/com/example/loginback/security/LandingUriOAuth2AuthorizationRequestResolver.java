package com.example.loginback.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

// NOTE: Extract landing uri from client request to callback after authentication
public class LandingUriOAuth2AuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    private final OAuth2AuthorizationRequestResolver defaultResolver;

    public LandingUriOAuth2AuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository) {
        this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, "/oauth2/auth");
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest authorizationRequest = defaultResolver.resolve(request);
        return putLandingUriToAuthorizationRequestState(authorizationRequest, request);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest authorizationRequest = defaultResolver.resolve(request, clientRegistrationId);
        return putLandingUriToAuthorizationRequestState(authorizationRequest, request);
    }

    private OAuth2AuthorizationRequest putLandingUriToAuthorizationRequestState(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request) {
        if (authorizationRequest != null) {
            String landingUri = request.getParameter("landing_uri");
            if (landingUri != null) {
                String state = authorizationRequest.getState() + "&landing_uri=" + URLEncoder.encode(landingUri, StandardCharsets.UTF_8);
                return OAuth2AuthorizationRequest.from(authorizationRequest)
                        .state(state)
                        .build();
            }
        }
        return null;
    }

}
