package com.example.loginback.provider;

import com.example.loginback.types.OAuthProviderType;

public interface AuthCodeRequestUrlProvider {

    OAuthProviderType getOauthProviderType();

    String getUrl();

}
