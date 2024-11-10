package com.example.loginback.security;

import com.example.loginback.security.jwt.JwtCookieCreator;
import com.example.loginback.security.jwt.JwtGenerator;
import com.example.loginback.security.model.ProviderUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtGenerator jwtGenerator;
    private final JwtCookieCreator jwtCookieCreator;
    private final OAuth2UserConverter oAuth2UserConverter;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oauthToken.getPrincipal();
        String registrationId = oauthToken.getAuthorizedClientRegistrationId();
        ProviderUser providerUser = oAuth2UserConverter.constructProviderUserFromOAuth2User(registrationId, oAuth2User);
        oAuth2UserConverter.register(providerUser);
        String token = jwtGenerator.generateToken(providerUser, registrationId);
        String state = request.getParameter("state");
        String landingUri = null;
        if (state != null) {
            Map<String, String> params = Arrays.stream(state.split("&"))
                    .map(param -> param.split("="))
                    .filter(entry -> entry.length == 2)
                    .collect(Collectors.toMap(
                            entry -> entry[0],
                            entry -> URLDecoder.decode(entry[1], StandardCharsets.UTF_8)
                    ));
            landingUri = params.get("landing_uri");
        }
        if (landingUri != null) {
            Cookie jwtCookie = jwtCookieCreator.createLoginCookie(token);
            response.addCookie(jwtCookie);
            response.sendRedirect(URLDecoder.decode(landingUri, StandardCharsets.UTF_8));
        } else {
            response.setContentType("application/json");
            response.getWriter().write("{\"token\":\"" + token + "\"}");
            response.getWriter().flush();
        }
    }

}
