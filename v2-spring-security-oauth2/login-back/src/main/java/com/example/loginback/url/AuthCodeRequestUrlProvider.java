package com.example.loginback.url;

import com.example.loginback.types.OAuthProviderType;

public interface AuthCodeRequestUrlProvider {

    OAuthProviderType getOauthProviderType();

    String getUrl();

}
