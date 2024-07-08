package com.javatech.javamail.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javatech.javamail.enums.TypeUtilisateur;
import jakarta.persistence.*;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "pseudo", nullable = false, unique = true, length = 32)
    private String pseudo;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "mot_de_passe", nullable = false, length = 255)
    @JsonIgnore
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, columnDefinition = "enum('ADMINISTRATEUR','UTILISATEUR')")
    private TypeUtilisateur type = TypeUtilisateur.UTILISATEUR;

    @Column(name = "est_supprime", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean estSupprime = false;

    public Utilisateur(int id, String pseudo, String email, String motDePasse, TypeUtilisateur type, boolean estSupprime) {
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
        this.motDePasse = motDePasse;
        this.type = type;
        this.estSupprime = estSupprime;
    }

    public Utilisateur(String pseudo, String email, String motDePasse) {
        this.pseudo = pseudo;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public Utilisateur(String pseudo, String motDePasse) {
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
    }

    public Utilisateur() {
    }

    public int getId() {
        return id;
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

    public TypeUtilisateur getType() {
        return type;
    }

    public void setType(TypeUtilisateur type) {
        this.type = type;
    }

    public boolean estSupprime() {
        return estSupprime;
    }

    public void setEstSupprime(boolean estSupprime) {
        this.estSupprime = estSupprime;
    }
}
