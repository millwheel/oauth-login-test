package com.example.loginback.controller.sender;

import com.example.loginback.dto.UserInfoDto;
import com.example.loginback.exception.RequestFailException;
import com.example.loginback.exception.UserInfoEmptyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class CommonApiSender {

    public UserInfoDto getUserInfo(String userUrl, String accessToken){
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
