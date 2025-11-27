package com.example.demo.loginapi.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.loginapi.dto.AuthResponse;
import com.example.demo.loginapi.dto.CadastroRequest;
import com.example.demo.loginapi.dto.LoginRequest;
import com.example.demo.loginapi.model.Usuario;
import com.example.demo.loginapi.repository.UsuarioRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {


    private static final String SECRET_KEY = "chave_super_secreta_12345678901234567890";

    
    private final UsuarioRepository usuarioRepository;

    public AuthService( UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public AuthResponse logar(LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(request.getEmail());

        if (usuarioOpt.isEmpty()) {
            return new AuthResponse(false, null, "Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuario.getSenha().equals(request.getSenha())) {
            return new AuthResponse(false, null, "Senha incorreta");
        }

        String token = gerarTokenJwt(usuario);
        return new AuthResponse(true, token, "Login bem-sucedido");
    }

    public AuthResponse registrar(CadastroRequest request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            return new AuthResponse(false, null, "E-mail já cadastrado");
        }

        Usuario novoUsuario = request.usuario();
        Usuario salvo = usuarioRepository.save(novoUsuario);
        String token = gerarTokenJwt(salvo);

        return new AuthResponse(true, token, "Usuário cadastrado com sucesso");
    }


    @SuppressWarnings("deprecation")
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
