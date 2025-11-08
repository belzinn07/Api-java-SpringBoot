 package com.example.demo.loginapi.service;

import org.springframework.beans.factory.annotation.Value;

import com.example.demo.loginapi.model.Usuario;

public class GerenciadorTokenJwt implements TokenService {

    @Value("${security.jwt.secret}")
    private String chaveSecreta;
    @Value("${security.jwt.expiration-hours}")
    private String tempoExpiracao;

    @Override
    public String gerarToken(Usuario usuario) {
        
    }

    @Override
    public String validarToken(String token) {
      
    }
    

}