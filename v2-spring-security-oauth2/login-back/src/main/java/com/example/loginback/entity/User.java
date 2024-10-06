package com.example.loginback.entity;

import com.example.loginback.security.model.ProviderUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String pid;
    private String username;
    private String password;
    private String provider;
    private String email;
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authority")
    private List<String> authorities;

    public User(ProviderUser providerUser) {
        this.pid = providerUser.getId();
        this.username = providerUser.getUsername();
        this.password = providerUser.getPassword();
        this.provider = providerUser.getProvider();
        this.email = providerUser.getEmail();
        this.name = providerUser.getName();
        this.authorities = providerUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();;
    }
}
