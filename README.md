# login-test

This project simply provides OAuth2 login process (Provider: Google, Naver and Kakao)  
All login user information are stored in in-memory H2 database.

## v1 - ReactJS + Spring boot

OAuth2 Login with ReactJS and Spring Boot

It doesn't use Spring Security.  
ReactJS directly get the authorization code from OAuth2 Provider.  
Spring Boot server uses the authorization code and sends the request for token exchange.

## v2 - Spring security oauth2 client + html

OAuth2 Login with Spring Security OAuth2 Client

It uses Spring Security OAuth2 Client.  
All OAuth2 login process is controlled by Spring Security.  
It doesn't use ReactJS. It has the html file in the resources folder.

## v3 - ReactJS + Spring security oauth2 client

OAuth2 Login with ReactJS And Spring Security OAuth2 Client

It uses Spring Security OAuth2 Client.  
Auth information is stored in backend server session.  
Front app should call the "/auth" endpoint to check its login status.

## v4 - ReactJS + Spring security oauth2 client (JWT)

It uses Spring Security OAuth2 Client.  
The server doesn't use the session to store the authentication status.
The server provides JWT to client and uses it to distinguish authentication by parsing it with JWT security key
