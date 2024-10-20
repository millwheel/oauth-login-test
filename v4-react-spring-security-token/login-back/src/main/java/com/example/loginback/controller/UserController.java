package com.example.loginback.controller;

import com.example.loginback.dto.UserResponseDto;
import com.example.loginback.entity.User;
import com.example.loginback.security.OAuth2UserConverter;
import com.example.loginback.security.model.ProviderUser;
import com.example.loginback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final OAuth2UserConverter oAuth2UserConverter;
    private final UserService userService;

    @GetMapping("/oauth")
    @PreAuthorize("hasAnyRole('OAUTH2_USER', 'OIDC_USER')")
    public OAuth2User oauth2user(Authentication authentication,
                               @AuthenticationPrincipal OAuth2User oAuth2User,
                               @AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser != null) {
            log.info("OIDC user authenticated. Authentication = {}, OidcUser = {}", authentication, oidcUser);
            return oidcUser;
        } else if (oAuth2User != null) {
            log.info("OAuth2 user authenticated. Authentication = {}, OAuth2User = {}", authentication, oAuth2User);
            return oAuth2User;
        }
        throw new RuntimeException("No authenticated user found.");
    }

    @GetMapping("/db")
    @PreAuthorize("hasAnyRole('OAUTH2_USER', 'OIDC_USER')")
    public UserResponseDto dbUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        User user = userService.getUser(attributes.get("sub").toString());
        return new UserResponseDto(user);
    }
}
