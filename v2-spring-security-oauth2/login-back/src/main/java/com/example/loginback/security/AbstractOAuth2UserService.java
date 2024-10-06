package com.example.loginback.security;

import com.example.loginback.entity.User;
import com.example.loginback.security.model.GoogleUser;
import com.example.loginback.security.model.KakaoUser;
import com.example.loginback.security.model.NaverUser;
import com.example.loginback.security.model.ProviderUser;
import com.example.loginback.repository.UserRepository;
import com.example.loginback.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Getter
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractOAuth2UserService {

    private final UserRepository userRepository;
    private final UserService userService;

    public ProviderUser constructProviderUserFromOAuth2User(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
        String registrationId = clientRegistration.getRegistrationId();
        switch (registrationId) {
            case "google" -> {
                return new GoogleUser(oAuth2User, clientRegistration);
            }
            case "naver" -> {
                return new NaverUser(oAuth2User, clientRegistration);
            }
            case "kakao" -> {
                return new KakaoUser(oAuth2User, clientRegistration);
            }
            default -> throw new RuntimeException("Unsupported registration id " + registrationId);
        }
    }

    public void register(ClientRegistration clientRegistration, ProviderUser providerUser) {
        Optional<User> user = userService.getUser(providerUser.getUsername());
        if (user.isEmpty()) {
            userService.AddUser(clientRegistration.getRegistrationId(), providerUser);
        }
    }
}
