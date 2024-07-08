package com.javatech.javamail.dtos;

import com.javatech.javamail.enums.TypeUtilisateur;

public class UtilisateurCreationDto {
    private String pseudo;
    private String email;
    private String motDePasse;

    @Override
    public String toString() {
        return "UtilisateurDto{" +
                "pseudo='" + pseudo + '\'' +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                '}';
    }

    public UtilisateurCreationDto(String pseudo, String email, String motDePasse) {
        this.pseudo = pseudo;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public UtilisateurCreationDto() {
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

}
