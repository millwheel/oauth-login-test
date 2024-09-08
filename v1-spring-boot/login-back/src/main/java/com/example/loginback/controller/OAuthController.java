package com.example.loginback.controller;

import com.example.loginback.config.OAuthProperties;
import com.example.loginback.controller.sender.GoogleApiSender;
import com.example.loginback.controller.sender.NaverApiSender;
import com.example.loginback.dto.LoginResponseDto;
import com.example.loginback.dto.TokenInfoDto;
import com.example.loginback.dto.UserInfoDto;
import com.example.loginback.entity.AuthProvider;
import com.example.loginback.entity.User;
import com.example.loginback.exception.EmptyTokenException;
import com.example.loginback.exception.RequestFailException;
import com.example.loginback.exception.UserInfoEmptyException;
import com.example.loginback.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final OAuthProperties oAuthProperties;
    private final UserService userService;
    private final GoogleApiSender googleApiSender;
    private final NaverApiSender naverApiSender;

    @GetMapping("/google/token")
    public ResponseEntity<LoginResponseDto> getGoogleToken(@RequestParam("code") String code){
        String clientId = oAuthProperties.getGoogle().getClientId();
        String clientSecret = oAuthProperties.getGoogle().getClientSecret();
        String redirectUri = oAuthProperties.getGoogle().getRedirectUri();

        TokenInfoDto tokenInfoDto;
        try {
            tokenInfoDto = googleApiSender.exchangeToken(code, clientId, clientSecret, redirectUri);
        } catch (RequestFailException | EmptyTokenException e) {
            LoginResponseDto loginResponseDto = new LoginResponseDto(e.getMessage());
            return new ResponseEntity<>(loginResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserInfoDto userInfo;
        try {
            userInfo = googleApiSender.getUserInfo(tokenInfoDto.getIdToken());
        } catch (RequestFailException | UserInfoEmptyException e) {
            LoginResponseDto loginResponseDto = new LoginResponseDto(e.getMessage());
            return new ResponseEntity<>(loginResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
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

    @GetMapping("/naver/token")
    public ResponseEntity<LoginResponseDto> getNaverToken(@RequestParam("code") String code,
                                                          @RequestParam("state") String state){
        String clientId = oAuthProperties.getNaver().getClientId();
        String clientSecret = oAuthProperties.getNaver().getClientSecret();

        TokenInfoDto tokenInfoDto;
        try {
            tokenInfoDto = naverApiSender.exchangeToken(code, clientId, clientSecret, state);
        } catch (RequestFailException | EmptyTokenException e) {
            LoginResponseDto loginResponseDto = new LoginResponseDto(e.getMessage());
            return new ResponseEntity<>(loginResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserInfoDto userInfo;
        try {
            String userUrl = "https://openapi.naver.com/v1/nid/me";
            userInfo = naverApiSender.getUserInfo(userUrl, tokenInfoDto.getAccessToken());
        } catch (RequestFailException | UserInfoEmptyException e) {
            LoginResponseDto loginResponseDto = new LoginResponseDto(e.getMessage());
            return new ResponseEntity<>(loginResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user = userService.processOAuthPostLogin(userInfo.getEmail(), userInfo.getName(), AuthProvider.NAVER);

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .accessToken(tokenInfoDto.getAccessToken())
                .expiresIn(Integer.parseInt(tokenInfoDto.getExpiresIn()))
                .build();

        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }



}
