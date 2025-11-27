package com.example.demo.loginapi.dto;

public class AuthResponse {
    private boolean sucess;
    private  String token;
    private String tipoToken = "Bearer ";
    private String mensagem;

    public AuthResponse(boolean sucess, String token, String mensagem){
        this.sucess = sucess;
        this.token = token;
        this.tipoToken = tipoToken;
        this.mensagem = mensagem;
    }

    public boolean isSucess(){
        return sucess;
    }


    public String getToken(){
        return token;
    }

    public String getTipoToken(){
        return tipoToken;
    }

    public String getMensagem(){
        return mensagem;
    }

    
}
