package com.example.demo.loginapi.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.loginapi.dto.AuthResponse;
import com.example.demo.loginapi.dto.CadastroRequest;
import com.example.demo.loginapi.dto.LoginRequest;
import com.example.demo.loginapi.model.Usuario;
import com.example.demo.loginapi.repository.UsuarioRepository;
import com.example.demo.loginapi.security.JwtService;
import com.example.demo.loginapi.security.TokenService;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public AuthResponse logar(LoginRequest request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getSenha()
            )
        );

        String token = tokenService.gerarToken(request.getEmail());
        return new AuthResponse(true, token, "Login realizado com sucesso");
    }

    public AuthResponse registrar(CadastroRequest request) {

        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            return new AuthResponse(false, null, "E-mail já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setRoles("ROLE_USER");

        usuarioRepository.save(usuario);

        String token = tokenService.gerarToken(usuario.getEmail());
        return new AuthResponse(true, token, "Usuário cadastrado com sucesso");
    }
}
