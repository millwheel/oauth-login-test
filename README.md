# login-test

## v1 - react + spring boot

OAuth2 Login with ReactJS and Spring Boot

It doesn't use Spring Security.
ReactJS directly get the authorization code from OAuth2 Provider.
Spring Boot server uses the authorization code and sends the request for token exchange.

## v2 - spring security oauth2 client

OAuth2 Login with Spring Security OAuth2 Client

It uses Spring Security. All OAuth2 login process is controlled by Spring Security.
It doesn't use ReactJS. It has the html file in the resources folder.
