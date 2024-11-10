package com.example.loginback.security.authority;


public class AuthorityFormatter {

    private static String prefix = "ROLE_";


    public static String trimAuthorityString(String name) {

        if (name.lastIndexOf(".") > 0){
            int index = name.lastIndexOf(".");
            name = "SCOPE_" + name.substring(index + 1);
        }

        if (prefix.length() > 0 && !name.startsWith(prefix)){
            name = prefix + name;
        }

        return name;
    }
}
