package com.javatech.javamail.dtos;

public class UtilisateurLoginDto {
    private String pseudo;
    private String motDePasse;

    public UtilisateurLoginDto(String pseudo, String motDePasse) {
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
    }

    public UtilisateurLoginDto() {
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "UtilisateurLoginDto{" +
                "pseudo='" + pseudo + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                '}';
    }
}
