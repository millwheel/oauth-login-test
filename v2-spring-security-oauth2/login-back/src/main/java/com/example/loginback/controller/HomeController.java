package com.example.loginback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        if (authentication == null) {
            return "home";
        }

        String email = null;
        String name = null;

        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;

        if (authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
            email = oAuth2User.getAttribute("email");
            name = oAuth2User.getAttribute("name");

            Map<String, Object> attributes = oAuth2User.getAttributes();
            if (authenticationToken.getAuthorizedClientRegistrationId().equals("naver")){
                Map<String, Object> response = (Map)attributes.get("response");
                email = (String) response.get("email");
                name = (String) response.get("name");
            }
            log.info("email: {}, name: {}", email, name);

        }

        model.addAttribute("email", email);
        model.addAttribute("name", name);
        return "home";
    }

}
