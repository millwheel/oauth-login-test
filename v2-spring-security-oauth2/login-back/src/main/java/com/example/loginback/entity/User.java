package com.example.loginback.entity;

import com.example.loginback.security.AuthorityFormatter;
import com.example.loginback.security.model.ProviderUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Getter
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
                .map(authority -> AuthorityFormatter.trimAuthorityString(authority.getAuthority()))
                .toList();;
    }
}
