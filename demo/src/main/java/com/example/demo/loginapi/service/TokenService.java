package com.example.demo.loginapi.service;

import com.example.demo.loginapi.model.Usuario;

public interface TokenService {
    
    String gerarToken(Usuario usuario);
    String validarToken(String token);
    
}
