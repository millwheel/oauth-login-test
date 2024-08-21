package com.example.loginback.controller;

import com.example.loginback.config.OAuthProperties;
import com.example.loginback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {

    private final OAuthProperties oAuthProperties;
    private final UserService userService;

    @PostMapping("/google/token")
    public ResponseEntity<?> getGoogleToken(@RequestParam("code") String code){
        String clientId = oAuthProperties.getGoogle().getClientId();
        String clientSecret = oAuthProperties.getGoogle().getClientSecret();
        String redirectUri = oAuthProperties.getGoogle().getRedirectUri();
        String tokenUrl = "https://oauth2.googleapis.com/token";

        RestClient restClient = RestClient.create();

        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("redirect_uri", redirectUri);
        params.put("grant_type", "authorization_code");

        ResponseEntity<Map> responseEntity = restClient.post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(params)
                .retrieve()
                .toEntity(Map.class);

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        log.info(statusCode.toString());
        Map responseBody = responseEntity.getBody();
        assert responseBody != null;
        String accessToken = (String) responseBody.get("access_token");
        Integer expiresIn = (Integer) responseBody.get("expires_in");
        String tokenType = (String) responseBody.get("token_type");
        String scope = (String) responseBody.get("scope");
        String refreshToken = (String) responseBody.get("refresh_token");
        log.info("access_token: {}", accessToken);
        log.info("expires_in: {}", expiresIn);
        log.info("token_type: {}", tokenType);
        log.info("scope: {}", scope);
        log.info("refresh_token: {}", refreshToken);

        return null;
    }

    @PostMapping("/naver/token")
    public ResponseEntity<?> getNaverToken(@RequestParam("code") String code, @RequestParam("state") String state){
        String clientId = oAuthProperties.getNaver().getClientId();
        String clientSecret = oAuthProperties.getNaver().getClientSecret();
        String redirectUri = oAuthProperties.getNaver().getRedirectUri();
        String baseUrl = "https://nid.naver.com/oauth2.0/token";

        String apiUrl = baseUrl +
                "?grant_type=authorization_code&client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&redirect_uri=" + redirectUri
                + "&code=" + code
                + "&state=" + state;

        RestClient restClient = RestClient.create();

        ResponseEntity<Map> responseEntity = restClient.get()
                .uri(apiUrl)
                .retrieve()
                .toEntity(Map.class);


        HttpStatusCode statusCode = responseEntity.getStatusCode();
        log.info(statusCode.toString());
        Map responseBody = responseEntity.getBody();
        assert responseBody != null;
        String accessToken = (String) responseBody.get("access_token");
        String refreshToken = (String) responseBody.get("refresh_token");
        String tokenType = (String) responseBody.get("token_type");
        Integer expiresIn = (Integer) responseBody.get("expires_in");
        String error = (String) responseBody.get("error");
        String errorDescription = (String) responseBody.get("error_description");
        log.info("access_token: {}", accessToken);
        log.info("expires_in: {}", expiresIn);
        log.info("token_type: {}", tokenType);
        log.info("refresh_token: {}", refreshToken);
        log.error("error: {}", error);
        log.error("errorDescription: {}", errorDescription);

        return null;
    }

}
