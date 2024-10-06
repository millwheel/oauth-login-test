package com.example.loginback.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomAuthorityMapper implements GrantedAuthoritiesMapper {

    private String prefix = "ROLE_";

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {

        Set<GrantedAuthority> set = new HashSet<>(authorities.size());
        for (GrantedAuthority grantedAuthority : authorities) {
            set.add(formatAuthority(grantedAuthority.getAuthority()));
        }

        return set;
    }

    private GrantedAuthority formatAuthority(String name) {

        if (name.lastIndexOf(".") > 0){
            int index = name.lastIndexOf(".");
            name = "SCOPE_" + name.substring(index + 1);
        }

        if (prefix.length() > 0 && !name.startsWith(prefix)){
            name = prefix + name;
        }

        return new SimpleGrantedAuthority(name);
    }
}
