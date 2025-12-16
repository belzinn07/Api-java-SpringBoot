package com.example.demo.loginapi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.loginapi.dto.AuthResponse;
import com.example.demo.loginapi.dto.CadastroRequest;
import com.example.demo.loginapi.dto.LoginRequest;
import com.example.demo.loginapi.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {
  
    
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }   


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.logar(request));
    }

     @PostMapping("/cadastrar")
     public ResponseEntity<AuthResponse> register(@RequestBody CadastroRequest request) {
        return ResponseEntity.ok(authService.registrar(request));
    }
    
    
    

}