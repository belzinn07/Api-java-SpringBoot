package com.example.demo; // use o mesmo pacote da sua classe principal

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String hello() {
        return "API rodando com sucesso 🚀";
    }
}
