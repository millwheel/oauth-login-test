package com.example.loginback.controller.sender;

import com.example.loginback.dto.TokenInfoDto;
import com.example.loginback.dto.UserInfoDto;
import com.example.loginback.exception.EmptyTokenException;
import com.example.loginback.exception.RequestFailException;
import com.example.loginback.exception.UserInfoEmptyException;
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
public class GoogleApiSender {

    public TokenInfoDto exchangeToken(String code, String clientId, String clientSecret, String redirectUri){
        String url = "https://oauth2.googleapis.com/token";

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
        Map responseBody = responseEntity.getBody();
        assert responseBody != null;

        if (statusCode != HttpStatus.OK) {
            throw new RequestFailException(statusCode);
        }

        String accessToken = (String) responseBody.get("access_token");
        String expiresIn = (String) responseBody.get("expires_in");

        if (accessToken == null || expiresIn == null){
            throw new EmptyTokenException();
        }

        return new TokenInfoDto(accessToken, expiresIn);
    }

}
