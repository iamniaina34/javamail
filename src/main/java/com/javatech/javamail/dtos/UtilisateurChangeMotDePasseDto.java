package com.javatech.javamail.dtos;

import org.jetbrains.annotations.NotNull;

public class UtilisateurChangeMotDePasseDto {

    @NotNull
    private String ancienMotDePasse;

    @NotNull
    private String nouveauMotDePasse;

    @NotNull
    private String confirmerMotDePasse;

    public UtilisateurChangeMotDePasseDto(@NotNull String ancienMotDePasse, @NotNull String nouveauMotDePasse, @NotNull String confirmerMotDePasse) {
        this.ancienMotDePasse = ancienMotDePasse;
        this.nouveauMotDePasse = nouveauMotDePasse;
        this.confirmerMotDePasse = confirmerMotDePasse;
    }

    public UtilisateurChangeMotDePasseDto() {
    }

    @NotNull
    public String getAncienMotDePasse() {
        return ancienMotDePasse;
    }

    public void setAncienMotDePasse(@NotNull String ancienMotDePasse) {
        this.ancienMotDePasse = ancienMotDePasse;
    }

    @NotNull
    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }

    public void setNouveauMotDePasse(@NotNull String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }

    @NotNull
    public String getConfirmerMotDePasse() {
        return confirmerMotDePasse;
    }

    public void setConfirmerMotDePasse(@NotNull String confirmerMotDePasse) {
        this.confirmerMotDePasse = confirmerMotDePasse;
    }
}
