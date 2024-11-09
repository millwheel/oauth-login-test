package com.example.loginback.security.model;

import java.util.List;
import java.util.Map;

public interface ProviderUser {

    String getSub();
    String getPid();
    String getEmail();
    String getName();
    String getProvider();
    List<String> getAuthorities();
    Map<String, Object> getAttributes();

}
