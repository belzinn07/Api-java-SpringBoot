package com.example.demo.loginapi.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.loginapi.model.LoginRequest;
import com.example.demo.loginapi.model.LoginResponse;
import com.example.demo.loginapi.model.Usuario;
import com.example.demo.loginapi.repository.UsuarioRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {


    private static final String SECRET_KEY = "chave_super_secreta_12345678901234567890";

    @Autowired
    private UsuarioRepository usuarioRepository;

    public LoginResponse autenticar(LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(request.getEmail());

        if (usuarioOpt.isEmpty()) {
            return new LoginResponse(false, null, "Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuario.getSenha().equals(request.getSenha())) {
            return new LoginResponse(false, null, "Senha incorreta");
        }

        String token = gerarTokenJwt(usuario);
        return new LoginResponse(true, token, "Login bem-sucedido");
    }

    public LoginResponse registrar(Usuario novo) {
        if (usuarioRepository.findByEmail(novo.getEmail()).isPresent()) {
            return new LoginResponse(false, null, "E-mail já cadastrado");
        }

        Usuario salvo = usuarioRepository.save(novo);
        String token = gerarTokenJwt(salvo);

        return new LoginResponse(true, token, "Usuário cadastrado com sucesso");
    }


    private String gerarTokenJwt(Usuario usuario) {
        Key chave = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("id", usuario.getId())
                .claim("nome", usuario.getNome())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) 
                .signWith(chave, SignatureAlgorithm.HS256)
                .compact();
    }
}
