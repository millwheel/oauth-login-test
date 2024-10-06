package com.example.loginback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @GetMapping("/api/user")
    @PreAuthorize("hasAnyRole('OAUTH2_USER', 'OIDC_USER')")
    public Authentication user(Authentication authentication,
                               @AuthenticationPrincipal OAuth2User oAuth2User,
                               @AuthenticationPrincipal OidcUser oidcUser) {
        if (oidcUser != null) {
            // OIDC 사용자 정보 로그
            log.info("OIDC user authenticated. Authentication = {}, OidcUser = {}", authentication, oidcUser);
        } else if (oAuth2User != null) {
            // OAuth2 사용자 정보 로그
            log.info("OAuth2 user authenticated. Authentication = {}, OAuth2User = {}", authentication, oAuth2User);
        } else {
            // 사용자 인증 정보가 없을 경우
            log.info("No authenticated user found.");
        }
        return authentication;
    }
}
