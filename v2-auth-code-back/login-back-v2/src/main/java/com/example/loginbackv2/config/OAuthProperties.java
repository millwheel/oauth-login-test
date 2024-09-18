package com.example.loginbackv2.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "key.oauth")
public class OAuthProperties {

    private final Provider google = new Provider();
    private final Provider naver = new Provider();
    private final Provider kakao = new Provider();

    @Setter
    @Getter
    public static class Provider {
        private String clientId;
        private String clientSecret;
        private String redirectUri;
    }

}
