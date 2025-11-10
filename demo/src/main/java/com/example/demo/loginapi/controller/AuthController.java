package com.example.demo.loginapi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.loginapi.model.CadastroRequest;
import com.example.demo.loginapi.model.LoginRequest;
import com.example.demo.loginapi.model.AuthResponse;
import com.example.demo.loginapi.model.Usuario;

import com.example.demo.loginapi.service.AuthService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {
  
    @Autowired//injeção de dependencia feita pelo spring de forma automatica
    private AuthService authService;

    @PostMapping("/login")//mapeamento do endpoint
   public AuthResponse login(@RequestBody LoginRequest request){
        return authService.logar(request);
    }

     @PostMapping("/cadastrar")
    public AuthResponse register(@RequestBody CadastroRequest request) {
        return authService.registrar(request);
    }
    
    
    

}
