package com.example.loginbackv2.controller;

import com.example.loginbackv2.config.OAuthProperties;
import com.example.loginbackv2.controller.sender.GoogleApiSender;
import com.example.loginbackv2.dto.LoginResponseDto;
import com.example.loginbackv2.dto.TokenInfoDto;
import com.example.loginbackv2.dto.UserInfoDto;
import com.example.loginbackv2.entity.AuthProvider;
import com.example.loginbackv2.entity.User;
import com.example.loginbackv2.exception.EmptyTokenException;
import com.example.loginbackv2.exception.RequestFailException;
import com.example.loginbackv2.exception.UserInfoEmptyException;
import com.example.loginbackv2.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GoogleLoginController {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final UserService userService;
    private final GoogleApiSender googleApiSender;

    public GoogleLoginController(OAuthProperties oAuthProperties, UserService userService, GoogleApiSender googleApiSender) {
        this.clientId = oAuthProperties.getGoogle().getClientId();
        this.clientSecret = oAuthProperties.getGoogle().getClientSecret();
        this.redirectUri = oAuthProperties.getGoogle().getRedirectUri();
        this.userService = userService;
        this.googleApiSender = googleApiSender;
    }


    @GetMapping("/oauth2/login/google")
    public String googleLogin() {
        String authorizationRequest = "https://accounts.google.com/o/oauth2/auth" +
                "?response_type=code&client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=profile%20email";
        return "redirect:" + authorizationRequest;
    }

    @GetMapping("/oauth/google/callback")
    public ResponseEntity<LoginResponseDto> getGoogleToken(@RequestParam("code") String code){

        TokenInfoDto tokenInfoDto;
        try {
            tokenInfoDto = googleApiSender.exchangeToken(code, clientId, clientSecret, redirectUri);
        } catch (RequestFailException | EmptyTokenException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponseDto(e.getMessage()));
        }

        UserInfoDto userInfo;
        try {
            userInfo = googleApiSender.getUserInfo(tokenInfoDto.getIdToken());
        } catch (RequestFailException | UserInfoEmptyException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponseDto(e.getMessage()));
        }

        User user = userService.processOAuthPostLogin(userInfo.getEmail(), userInfo.getName(), AuthProvider.GOOGLE);

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .accessToken(tokenInfoDto.getAccessToken())
                .expiresIn(Integer.parseInt(tokenInfoDto.getExpiresIn()))
                .build();

        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

}
