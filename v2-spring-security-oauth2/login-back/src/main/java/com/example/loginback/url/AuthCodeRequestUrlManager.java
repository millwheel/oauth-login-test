package com.example.loginback.url;

import com.example.loginback.types.OAuthProviderType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class AuthCodeRequestUrlManager {

    private final Map<OAuthProviderType, AuthCodeRequestUrlProvider> mapping;

    public AuthCodeRequestUrlManager(Set<AuthCodeRequestUrlProvider> providers) {
        mapping = providers.stream()
                .collect(toMap(
                        AuthCodeRequestUrlProvider::getOauthProviderType,
                        identity()
                ));
    }

    public String getUrl(OAuthProviderType oauthServerType) {
        return getProvider(oauthServerType).getUrl();
    }

    private AuthCodeRequestUrlProvider getProvider(OAuthProviderType oauthServerType) {
        return Optional.ofNullable(mapping.get(oauthServerType))
                .orElseThrow(() -> new RuntimeException("지원하지 않는 소셜 로그인 타입입니다."));
    }
}