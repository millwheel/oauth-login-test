package com.example.loginback.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.example.loginback.security.AuthorityFormatter.trimAuthorityString;

public class CustomAuthorityMapper implements GrantedAuthoritiesMapper {

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {

        Set<GrantedAuthority> set = new HashSet<>(authorities.size());
        for (GrantedAuthority grantedAuthority : authorities) {
            String authorityString = trimAuthorityString(grantedAuthority.getAuthority());
            set.add(new SimpleGrantedAuthority(authorityString));
        }

        return set;
    }
    
}
