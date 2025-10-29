package com.example.demo.loginapi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.loginapi.model.LoginRequest;
import com.example.demo.loginapi.model.LoginResponse;
import com.example.demo.loginapi.model.Usuario;
import com.example.demo.loginapi.repository.UsuarioRepository;
import com.example.demo.loginapi.service.AuthService;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {
  
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
   public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.autenticar(request);
    }

     @PostMapping("/register")
    public LoginResponse register(@RequestBody Usuario usuario) {
        return authService.registrar(usuario);
    }
    
    
    

}
