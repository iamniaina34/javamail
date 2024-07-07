package com.javatech.javamail.controllers;

import com.javatech.javamail.models.Utilisateur;
import com.javatech.javamail.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable int id) {
        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateurById(id);
        return utilisateur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.saveUtilisateur(utilisateur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable int id, @RequestBody Utilisateur utilisateurDetails) {
        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateurById(id);

        if (utilisateur.isPresent()) {
            Utilisateur updatedUtilisateur = utilisateur.get();
            updatedUtilisateur.setPseudo(utilisateurDetails.getPseudo());
            updatedUtilisateur.setMail(utilisateurDetails.getMail());
            updatedUtilisateur.setMotDePasse(utilisateurDetails.getMotDePasse());
            updatedUtilisateur.setType(utilisateurDetails.getType());
            updatedUtilisateur.setEstSupprime(utilisateurDetails.estSupprime());
            return ResponseEntity.ok(utilisateurService.saveUtilisateur(updatedUtilisateur));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable int id) {
        if (utilisateurService.getUtilisateurById(id).isPresent()) {
            utilisateurService.deleteUtilisateur(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}