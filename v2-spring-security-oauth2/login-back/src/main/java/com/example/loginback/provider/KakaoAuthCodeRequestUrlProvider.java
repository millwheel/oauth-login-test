package com.example.loginback.provider;


import com.example.loginback.config.OAuthProperties;
import com.example.loginback.types.OAuthProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoAuthCodeRequestUrlProvider implements AuthCodeRequestUrlProvider{

    private final OAuthProperties oAuthProperties;

    @Override
    public OAuthProviderType getOauthProviderType() {
        return OAuthProviderType.KAKAO;
    }

    @Override
    public String getUrl() {
        return UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com/oauth/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", oAuthProperties.getKakao().getClientId())
                .queryParam("redirect_uri", oAuthProperties.getKakao().getRedirectUri())
                .queryParam("scope", String.join(",", oAuthProperties.getKakao().getScope()))
                .toUriString();
    }
}
