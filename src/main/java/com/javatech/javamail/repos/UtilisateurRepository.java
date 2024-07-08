package com.javatech.javamail.repos;

import com.javatech.javamail.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer>{
    Optional<Utilisateur> findByEmail(String email);
    Optional<Utilisateur> findByPseudo(String pseudo);
    Optional<Utilisateur> findByPseudoAndMotDePasse(String pseudo, String motDePasse);
}
