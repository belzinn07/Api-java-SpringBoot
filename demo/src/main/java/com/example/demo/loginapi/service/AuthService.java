package com.example.demo.loginapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.loginapi.model.LoginRequest;
import com.example.demo.loginapi.model.LoginResponse;
import com.example.demo.loginapi.model.Usuario;
import com.example.demo.loginapi.repository.UsuarioRepository;



@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public LoginResponse autenticar(LoginRequest request) {
        return usuarioRepository.findByEmail(request.getEmail())
                .map(usuario -> {
                    if (usuario.getSenha().equals(request.getSenha())) {
                        return new LoginResponse(true, "TOKEN_FAKE_" + usuario.getId(), "Login bem-sucedido");
                    } else {
                        return new LoginResponse(false, null, "Senha incorreta");
                    }
                })
                .orElse(new LoginResponse(false, null, "Usuário não encontrado"));
    }

    public LoginResponse registrar(Usuario novo) {
        if (usuarioRepository.findByEmail(novo.getEmail()).isPresent()) {
            return new LoginResponse(false, null, "E-mail já cadastrado");
        }
        usuarioRepository.save(novo);
        return new LoginResponse(true, "TOKEN_FAKE_" + novo.getId(), "Usuário cadastrado com sucesso");
    }

}
