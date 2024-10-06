package com.example.loginback.entity;

import com.example.loginback.security.model.ProviderUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String registrationId;
    private String idFromProvider;
    private String username;
    private String password;
    private String provider;
    private String email;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authority")
    private List<String> authorities;

    public User(String registrationId, ProviderUser providerUser) {
        this.registrationId = registrationId;
        this.idFromProvider = providerUser.getId();
        this.username = providerUser.getUsername();
        this.password = providerUser.getPassword();
        this.provider = providerUser.getProvider();
        this.email = providerUser.getEmail();
        this.authorities = providerUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();;
    }
}
