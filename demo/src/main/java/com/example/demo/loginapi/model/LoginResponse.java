package com.example.demo.loginapi.model;

public class LoginResponse {
    private boolean sucess;
    private  String token;
    private String mensagem;

    public LoginResponse(boolean sucess, String token, String mensagem){
        this.sucess = sucess;
        this.token = token;
        this.mensagem = mensagem;
    }

    public boolean isSucess(){
        return sucess;
    }

    public String getToken(){
        return token;
    }

    public String getMensagem(){
        return mensagem;
    }

    
}
