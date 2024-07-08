package com.javatech.javamail.responses;

import com.javatech.javamail.models.Utilisateur;

public class LoginResponse {
    private Utilisateur utilisateur;
    private boolean logged;
    private String message;

    public LoginResponse(Utilisateur utilisateur, boolean logged, String message) {
        this.utilisateur = utilisateur;
        this.logged = logged;
        this.message = message;
    }

    public LoginResponse() {
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "utilisateur=" + utilisateur +
                ", logged=" + logged +
                ", message='" + message + '\'' +
                '}';
    }
}
