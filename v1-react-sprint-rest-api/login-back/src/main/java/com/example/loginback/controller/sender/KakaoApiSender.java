package com.example.loginback.controller.sender;

import com.example.loginback.dto.TokenInfoDto;
import com.example.loginback.dto.UserInfoDto;
import com.example.loginback.exception.EmptyTokenException;
import com.example.loginback.exception.RequestFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
@Slf4j
public class KakaoApiSender {

    public TokenInfoDto exchangeToken(String code, String clientId, String clientSecret, String redirectUri){
        String url = "https://kauth.kakao.com/oauth/token";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("redirect_uri", redirectUri);
        formData.add("grant_type", "authorization_code");

        RestClient restClient = RestClient.create();

        ResponseEntity<Map> responseEntity = restClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .toEntity(Map.class);

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        if (statusCode != HttpStatus.OK) {
            throw new RequestFailException(statusCode);
        }

        Map responseBody = responseEntity.getBody();
        assert responseBody != null;
        String accessToken = (String) responseBody.get("access_token");
        Integer expiresIn = (Integer) responseBody.get("expires_in");
        String refreshToken = (String) responseBody.get("refresh_token");

        if (accessToken == null || expiresIn == null) {
            throw new EmptyTokenException();
        }

        return new TokenInfoDto(accessToken, String.valueOf(expiresIn));
    }

    public UserInfoDto getUserInfo(String accessToken){
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

        RestClient restClient = RestClient.create();
        ResponseEntity<Map> responseEntity = restClient.get()
                .uri(userInfoUrl)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .toEntity(Map.class);

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        if (statusCode != HttpStatus.OK) {
            throw new RequestFailException(statusCode);
        }

        Map responseBody = responseEntity.getBody();
        assert responseBody != null;
        String email = "Not Available";
        Map<String, Object> kakaoAccount = (Map<String, Object>) responseBody.get("kakao_account");
        Map<String, String> profile = (Map<String, String>) kakaoAccount.get("profile");
        String name = profile.get("nickname");

        return new UserInfoDto(email, name);
    }


}
