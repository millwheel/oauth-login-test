package com.example.loginback.security.model;

import java.util.*;
import org.springframework.security.core.GrantedAuthority;

public interface ProviderUser {

    String getId();
    String getUsername();
    String getPassword();
    String getEmail();
    String getProvider();
    List<? extends GrantedAuthority> getAuthorities();
    Map<String, Object> getAttributes();

}
