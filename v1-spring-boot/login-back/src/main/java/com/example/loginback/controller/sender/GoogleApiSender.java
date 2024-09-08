package com.example.loginback.controller.sender;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.loginback.dto.GoogleTokenResponse;
import com.example.loginback.dto.TokenInfoDto;
import com.example.loginback.dto.UserInfoDto;
import com.example.loginback.exception.EmptyTokenException;
import com.example.loginback.exception.RequestFailException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Slf4j
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

        ResponseEntity<String> responseEntity = restClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .toEntity(String.class);

        HttpStatusCode statusCode = responseEntity.getStatusCode();

        if (statusCode != HttpStatus.OK) {
            throw new RequestFailException(statusCode);
        }

        String responseBody = responseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        GoogleTokenResponse googleTokenResponse = null;
        try {
            googleTokenResponse = objectMapper.readValue(responseBody, GoogleTokenResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        assert responseBody != null;

        if (googleTokenResponse == null){
            throw new EmptyTokenException();
        }

        String accessToken = googleTokenResponse.getAccess_token();
        Integer expiresIn = googleTokenResponse.getExpires_in();
        String idToken = googleTokenResponse.getId_token();

        if (accessToken == null || idToken == null){
            throw new EmptyTokenException();
        }

        return new TokenInfoDto(accessToken, idToken, String.valueOf(expiresIn));
    }

    public UserInfoDto getUserInfo(String idToken){
        DecodedJWT jwt = JWT.decode(idToken);

        String email = jwt.getClaim("email").asString();
        String givenName = jwt.getClaim("given_name").asString();
        String familyName = jwt.getClaim("family_name").asString();

        return new UserInfoDto(email, givenName + " " + familyName);
    }

}
