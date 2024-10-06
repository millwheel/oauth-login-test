package com.example.loginback.config;

import com.example.loginback.service.CustomOAuth2UserService;
import com.example.loginback.service.CustomOidcUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;

    // static 파일을 보안체크 하지 않도록 하는 설정들. 추후 프론트 구현 후에 제거할 것
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/static/js/**", "/static/images/**", "/static/css/**","/static/scss/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated());
        http.oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(customOAuth2UserService)
                        .oidcUserService(customOidcUserService)));
        http.logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll());
        return http.build();
    }

    @Bean
    public GrantedAuthoritiesMapper customAuthoritiesMapper() {
        return new CustomAuthorityMapper();
    }

}
