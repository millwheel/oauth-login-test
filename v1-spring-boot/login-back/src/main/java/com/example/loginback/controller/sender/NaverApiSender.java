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

    public TokenInfoDto exchangeToken(String url){
        RestClient restClient = RestClient.create();
        ResponseEntity<Map> responseEntity = restClient.get()
                .uri(url)
                .retrieve()
                .toEntity(Map.class);

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        Map responseBody = responseEntity.getBody();

        if (statusCode != HttpStatus.OK) {
            throw new RequestFailException(statusCode);
        }

        assert responseBody != null;
        String accessToken = (String) responseBody.get("access_token");
        String refreshToken = (String) responseBody.get("refresh_token");
        String expiresIn = (String) responseBody.get("expires_in");

        if (accessToken == null || refreshToken == null || expiresIn == null) {
            throw new EmptyTokenException();
        }

        return new TokenInfoDto(accessToken, expiresIn);
    }

    public UserInfoDto getUserInfo(String accessToken){
        String userUrl = "https://openapi.naver.com/v1/nid/me";
        RestClient restClient = RestClient.create();
        ResponseEntity<Map> responseEntity = restClient.get()
                .uri(userUrl)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .toEntity(Map.class);

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        Map responseBody = responseEntity.getBody();

        if (statusCode != HttpStatus.OK) {
            throw new RequestFailException(statusCode);
        }

        assert responseBody != null;
        String email = (String) responseBody.get("email");
        String name = (String) responseBody.get("name");

        if (email == null || name == null) {
            throw new UserInfoEmptyException();
        }

        return new UserInfoDto(email, name);
    }

}
