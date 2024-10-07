package com.example.loginback.controller.sender;

import com.example.loginback.dto.TokenInfoDto;
import com.example.loginback.dto.UserInfoDto;
import com.example.loginback.exception.EmptyTokenException;
import com.example.loginback.exception.RequestFailException;
import com.example.loginback.exception.UserInfoEmptyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class NaverApiSender {

    public TokenInfoDto exchangeToken(String code, String clientId, String clientSecret, String state){
        String baseUrl = "https://nid.naver.com/oauth2.0/token";
        String url = baseUrl +
                "?grant_type=authorization_code&client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&code=" + code
                + "&state=" + state;

        RestClient restClient = RestClient.create();
        ResponseEntity<Map> responseEntity = restClient.get()
                .uri(url)
                .retrieve()
                .toEntity(Map.class);

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        if (statusCode != HttpStatus.OK) {
            throw new RequestFailException(statusCode);
        }

        Map responseBody = responseEntity.getBody();
        assert responseBody != null;
        String accessToken = (String) responseBody.get("access_token");
        String expiresIn = (String) responseBody.get("expires_in");

        if (accessToken == null || expiresIn == null) {
            throw new EmptyTokenException();
        }

        return new TokenInfoDto(accessToken, expiresIn);
    }

    public UserInfoDto getUserInfo(String accessToken){
        String userInfoUrl = "https://openapi.naver.com/v1/nid/me";

        RestClient restClient = RestClient.create();
        ResponseEntity<Map> responseEntity = restClient.get()
                .uri(userInfoUrl)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .toEntity(Map.class);

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        Map responseBody = responseEntity.getBody();

        if (statusCode != HttpStatus.OK) {
            throw new RequestFailException(statusCode);
        }

        assert responseBody != null;
        Map<String, String> response = (Map<String, String>) responseBody.get("response");
        String email = response.get("email");
        String name = response.get("name");

        if (email == null || name == null) {
            throw new UserInfoEmptyException();
        }

        return new UserInfoDto(email, name);
    }

}
