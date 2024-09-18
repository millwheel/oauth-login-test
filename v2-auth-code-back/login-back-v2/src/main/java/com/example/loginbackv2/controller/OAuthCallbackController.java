package com.example.loginbackv2.controller;

import com.example.loginbackv2.config.OAuthProperties;
import com.example.loginbackv2.controller.sender.GoogleApiSender;
import com.example.loginbackv2.controller.sender.KakaoApiSender;
import com.example.loginbackv2.controller.sender.NaverApiSender;
import com.example.loginbackv2.dto.LoginResponseDto;
import com.example.loginbackv2.dto.TokenInfoDto;
import com.example.loginbackv2.dto.UserInfoDto;
import com.example.loginbackv2.entity.AuthProvider;
import com.example.loginbackv2.entity.User;
import com.example.loginbackv2.exception.EmptyTokenException;
import com.example.loginbackv2.exception.RequestFailException;
import com.example.loginbackv2.exception.UserInfoEmptyException;
import com.example.loginbackv2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final OAuthProperties oAuthProperties;
    private final UserService userService;
    private final GoogleApiSender googleApiSender;
    private final NaverApiSender naverApiSender;
    private final KakaoApiSender kakaoApiSender;

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
            userInfo = naverApiSender.getUserInfo(tokenInfoDto.getAccessToken());
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

    @GetMapping("/kakao/token")
    public ResponseEntity<LoginResponseDto> getKakaoToken(@RequestParam("code") String code){
        String clientId = oAuthProperties.getKakao().getClientId();
        String clientSecret = oAuthProperties.getKakao().getClientSecret();
        String redirectUri = oAuthProperties.getKakao().getRedirectUri();

        TokenInfoDto tokenInfoDto;
        try {
            tokenInfoDto = kakaoApiSender.exchangeToken(code, clientId, clientSecret, redirectUri);
        } catch (RequestFailException | EmptyTokenException e) {
            LoginResponseDto loginResponseDto = new LoginResponseDto(e.getMessage());
            return new ResponseEntity<>(loginResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserInfoDto userInfo;
        try {
            userInfo = kakaoApiSender.getUserInfo(tokenInfoDto.getAccessToken());
        } catch (RequestFailException | UserInfoEmptyException e) {
            LoginResponseDto loginResponseDto = new LoginResponseDto(e.getMessage());
            return new ResponseEntity<>(loginResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user = userService.processOAuthPostLogin(userInfo.getEmail(), userInfo.getName(), AuthProvider.KAKAO);

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .accessToken(tokenInfoDto.getAccessToken())
                .expiresIn(Integer.parseInt(tokenInfoDto.getExpiresIn()))
                .build();

        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

}
