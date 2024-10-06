package com.example.loginback.service;

import com.example.loginback.entity.User;
import com.example.loginback.model.GoogleUser;
import com.example.loginback.model.KakaoUser;
import com.example.loginback.model.NaverUser;
import com.example.loginback.model.ProviderUser;
import com.example.loginback.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractOAuth2UserService {

    private final UserRepository userRepository;
    private final UserService userService;

    public ProviderUser getProviderUserByProvider(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
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

    public void register(ProviderUser providerUser, OAuth2UserRequest userRequest) {
        User user = userRepository.findByUsername(providerUser.getUsername());
        if (user == null){
            userService.register(userRequest.getClientRegistration().getRegistrationId(), providerUser);
        }
    }
}
