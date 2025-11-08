package com.example.demo.loginapi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.loginapi.model.CadastroRequest;
import com.example.demo.loginapi.model.LoginRequest;
import com.example.demo.loginapi.model.LoginResponse;


import com.example.demo.loginapi.service.AuthService;



import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {
  
  
    private final AuthService authService;

    public AuthController( AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
   public LoginResponse login(@RequestBody LoginRequest request){
        return authService.logar(request);
    }

     @PostMapping("/cadastrar")
    public LoginResponse cadastrar(@RequestBody CadastroRequest request) {
        return authService.registrar(request);
    }
    
    
    

}
