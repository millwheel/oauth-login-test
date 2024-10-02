package com.example.loginback.types;

public enum OAuthProviderType {

    GOOGLE, NAVER, KAKAO;

    public static OAuthProviderType fromName(String type){
        return OAuthProviderType.valueOf(type);
    }

}
