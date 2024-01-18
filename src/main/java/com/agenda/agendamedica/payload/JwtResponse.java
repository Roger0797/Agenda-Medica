package com.agenda.agendamedica.payload;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private Set<SimpleGrantedAuthority> authorities; // Cambiado de 'roles' a 'authorities'

    public JwtResponse(String accessToken, Long id, String username, String email, Set<SimpleGrantedAuthority> authorities) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.authorities = authorities;
    }

    public JwtResponse(String jwt, Long id2, String username2, String email2, SimpleGrantedAuthority authority) {
        //TODO Auto-generated constructor stub
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }
}