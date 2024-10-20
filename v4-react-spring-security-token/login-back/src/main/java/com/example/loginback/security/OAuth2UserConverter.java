package com.example.loginback.security;

import com.example.loginback.security.model.GoogleUser;
import com.example.loginback.security.model.KakaoUser;
import com.example.loginback.security.model.NaverUser;
import com.example.loginback.security.model.ProviderUser;
import com.example.loginback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserConverter {

    private final UserService userService;

    public ProviderUser constructProviderUserFromOAuth2User(String registrationId, OAuth2User oAuth2User) {
        switch (registrationId) {
            case "google" -> {
                return new GoogleUser(oAuth2User, registrationId);
            }
            case "naver" -> {
                return new NaverUser(oAuth2User, registrationId);
            }
            case "kakao" -> {
                return new KakaoUser(oAuth2User, registrationId);
            }
            default -> throw new RuntimeException("Unsupported registration id " + registrationId);
        }
    }

    public void register(ProviderUser providerUser) {
        if (!userService.isExist(providerUser.getId())){
            userService.AddUser(providerUser);
        }
    }
}
