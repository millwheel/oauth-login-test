package com.example.loginback.model;

import java.util.*;
import org.springframework.security.core.GrantedAuthority;

public interface ProviderUser {

    String getId();
    String getUsername();
    String getPassword();
    String getEmail();
    String getProvider();
    String getAuthorities();
    List<? extends GrantedAuthority> getAuthoritiesList();
    Map<String, Object> getAttributes();

}
