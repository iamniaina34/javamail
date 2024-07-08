package com.javatech.javamail.services;

import com.javatech.javamail.configs.SecurityConfiguration;
import com.javatech.javamail.dtos.UtilisateurChangeMotDePasseDto;
import com.javatech.javamail.dtos.UtilisateurCreationDto;
import com.javatech.javamail.dtos.UtilisateurLoginDto;
import com.javatech.javamail.models.Utilisateur;
import com.javatech.javamail.repos.UtilisateurRepository;
import com.javatech.javamail.responses.LoginResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(int id) {
        return utilisateurRepository.findById(id);
    }

    public Optional<Utilisateur> getUtilisateurByMail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public Optional<Utilisateur> getUtilisateurByPseudo(String pseudo) {
        return utilisateurRepository.findByPseudo(pseudo);
    }

    public Utilisateur saveUtilisateur(@NotNull Utilisateur utilisateur) {
        utilisateur.setMotDePasse(SecurityConfiguration.passwordEncoder().encode(utilisateur.getMotDePasse()));
        return utilisateurRepository.save(utilisateur);
    }

    public void deleteUtilisateur(int id) {
        utilisateurRepository.deleteById(id);
    }

    public ResponseEntity<LoginResponse> login(@NotNull UtilisateurLoginDto dto) {
        LoginResponse response = new LoginResponse();
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByPseudo(dto.getPseudo());
        if (utilisateur.isPresent()) {
            Utilisateur u = utilisateur.get();
            boolean isMotDePasseMatching = SecurityConfiguration.passwordEncoder().matches(dto.getMotDePasse(), u.getMotDePasse());
            if (isMotDePasseMatching) {
                response.setLogged(true);
                response.setMessage("Connecté en tant que " + dto.getPseudo());
                response.setUtilisateur(u);
            } else {
                response.setLogged(false);
                response.setMessage("Mot de passe erroné");
                response.setUtilisateur(null);
            }
        } else {
            response.setLogged(false);
            response.setMessage("utilisateur inconnu");
            response.setUtilisateur(null);
        }
        return ResponseEntity.ok(response);
    }

    public Utilisateur createUser(@NotNull UtilisateurCreationDto dto) {
        Utilisateur utilisateur = new Utilisateur(dto.getPseudo(), dto.getEmail(), dto.getMotDePasse());
        utilisateur.setMotDePasse(SecurityConfiguration.passwordEncoder().encode(utilisateur.getMotDePasse()));
        return utilisateurRepository.save(utilisateur);
    }

    public ResponseEntity<Utilisateur> updateUtilisateur(int id, Utilisateur utilisateurDetails) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        if (utilisateur.isPresent()) {
            Utilisateur updatedUtilisateur = utilisateur.get();
            updatedUtilisateur.setPseudo(utilisateurDetails.getPseudo());
            updatedUtilisateur.setEmail(utilisateurDetails.getEmail());
            updatedUtilisateur.setMotDePasse(SecurityConfiguration.passwordEncoder().encode(utilisateurDetails.getMotDePasse()));
            updatedUtilisateur.setType(utilisateurDetails.getType());
            updatedUtilisateur.setEstSupprime(utilisateurDetails.estSupprime());
            return ResponseEntity.ok(utilisateurRepository.save(updatedUtilisateur));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Utilisateur> changeMotDePasse(int id, UtilisateurChangeMotDePasseDto dto) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        if (utilisateur.isPresent()) {
            Utilisateur updatedUtilisateur = utilisateur.get();
            if (SecurityConfiguration.passwordEncoder().matches(dto.getAncienMotDePasse(), updatedUtilisateur.getMotDePasse()) && dto.getNouveauMotDePasse().equals(dto.getConfirmerMotDePasse())) {
                updatedUtilisateur.setMotDePasse(SecurityConfiguration.passwordEncoder().encode(dto.getConfirmerMotDePasse()));
                return ResponseEntity.ok(utilisateurRepository.save(updatedUtilisateur));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}