package com.example.loginback.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class HomeController {

//    @GetMapping("/")
//    public String index(Model model, Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2User){
//        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken)authentication;
//        if (authenticationToken != null){
//            Map<String, Object> attributes = oAuth2User.getAttributes();
//            String userName = (String)attributes.get("name");
//
//            if (userName == null) {
//                userName = (String) attributes.get("given_name");
//            }
//
//            if(authenticationToken.getAuthorizedClientRegistrationId().equals("naver")){
//                Map<String, Object> response = (Map)attributes.get("response");
//                userName = (String)response.get("name");
//            }
//
//            model.addAttribute("user", userName);
//        }
//        return "index";
//    }

    @GetMapping("/")
    public String home(Authentication authentication, Model model,
                        @AuthenticationPrincipal OAuth2User oAuth2User,
                        @AuthenticationPrincipal OidcUser oidcUser) {
        if (authentication == null) {
            return "home";
        }

        String email = null;
        String name = null;

        if (oAuth2User != null) {
            email = oAuth2User.getAttribute("email");
            name = oAuth2User.getAttribute("name");
        } else if (oidcUser != null) {
            email = oidcUser.getEmail();
            name = oidcUser.getFullName();
        }

        model.addAttribute("email", email);
        model.addAttribute("name", name);
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}
