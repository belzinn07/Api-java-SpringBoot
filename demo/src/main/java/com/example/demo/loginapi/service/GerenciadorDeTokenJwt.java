package com.example.demo.loginapi.service;

import com.example.demo.loginapi.execeptions.InvalidTokenException;
import com.example.demo.loginapi.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders; // <<< ESSENCIAL
import io.jsonwebtoken.security.Keys;   // <<< ESSENCIAL
import io.jsonwebtoken.security.SignatureException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class GerenciadorDeTokenJwt implements TokenService {

    private final String chaveSecreta;
    private final long tempoExpiracao;
    private final Key chaveDeAssinatura; 

    public GerenciadorDeTokenJwt(
        @Value("${security.jwt.secret}") String chaveSecreta, 
        @Value("${security.jwt.expiration-hours}") long tempoExpiracao) {
        
        this.chaveSecreta = chaveSecreta;
        this.tempoExpiracao = tempoExpiracao;
        this.chaveDeAssinatura = this.obterChaveDeAssinatura(); 
    }

    private Key obterChaveDeAssinatura() {
        // O erro estava aqui, mas é causado pela falta dos imports!
        byte[] keyBytes = Decoders.BASE64.decode(this.chaveSecreta); 
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    @Override
    public String gerartoken(Usuario usuario) {
        Instant atual = Instant.now();
        Instant expiracao = atual.plus(this.tempoExpiracao, ChronoUnit.HOURS);

        return Jwts.builder()
            .setSubject(usuario.getEmail())
            .claim("id", usuario.getId())
            .claim("nome", usuario.getNome())
            .setIssuedAt(Date.from(atual))
            .setExpiration(Date.from(expiracao))
            .signWith(this.chaveDeAssinatura, io.jsonwebtoken.SignatureAlgorithm.HS256)
            .compact();
    }

    // -------------------------------------------------------------------------
    // Implementação da Validação de Token (Sintaxe Simplificada para Compilação)
    // -------------------------------------------------------------------------

    @Override
    public String validarToken(String token) {
        try {
            // Usa a sintaxe parse() e cast, que deve funcionar em ambientes de classpath conflitantes
            io.jsonwebtoken.Jwt<Header, Claims> jwt = Jwts.parser()
                .setSigningKey(this.chaveDeAssinatura)
                .parse(token); // Tenta usar o método parse() genérico
            
            return jwt.getBody().getSubject();

        } catch (ExpiredJwtException e) {
            // Fluxo de erro limpo (Anti-if)
            throw new InvalidTokenException("Token expirado.", e);
        } catch (SignatureException e) {
            throw new InvalidTokenException("Assinatura do token inválida ou chave secreta incorreta.", e);
        } catch (MalformedJwtException | IllegalArgumentException | UnsupportedJwtException e) {
            throw new InvalidTokenException("Token malformado.", e);
        }
    }
}