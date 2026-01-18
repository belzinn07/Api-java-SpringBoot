package com.example.demo.loginapi.security;

public interface TokenService {
    String gerarToken(String subject);
    boolean validarToken(String token);
    String getSubject(String token);
   
}
