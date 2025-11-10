package com.example.demo.loginapi.service;

import com.example.demo.loginapi.model.Usuario;

public interface TokenService {
    String gerartoken(Usuario usuario);
    String validarToken(String token);
}
