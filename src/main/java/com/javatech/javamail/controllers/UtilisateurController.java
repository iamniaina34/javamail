package com.javatech.javamail.controllers;

import com.javatech.javamail.configs.SecurityConfiguration;
import com.javatech.javamail.dtos.UtilisateurChangeMotDePasseDto;
import com.javatech.javamail.dtos.UtilisateurCreationDto;
import com.javatech.javamail.dtos.UtilisateurLoginDto;
import com.javatech.javamail.models.Utilisateur;
import com.javatech.javamail.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private final UtilisateurService utilisateurService;

    public UtilisateurController (UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping({"", "/", "/index"})
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }

    @GetMapping("/{id}")
    public Optional<Utilisateur> getUtilisateurById(@PathVariable int id) {
        return utilisateurService.getUtilisateurById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> logInUtilisateur (@RequestBody UtilisateurLoginDto dto) {
        return utilisateurService.login(dto);
    }

    @PostMapping("/create")
    public Utilisateur createUtilisateur(@RequestBody UtilisateurCreationDto dto) {
        return utilisateurService.createUser(dto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable int id, @RequestBody Utilisateur utilisateurDetails) {
        return utilisateurService.updateUtilisateur(id, utilisateurDetails);
    }

    @PutMapping("/reset/{id}")
    public ResponseEntity<Utilisateur> changeMotDePasse (@PathVariable int id, @RequestBody UtilisateurChangeMotDePasseDto dto) {
        return utilisateurService.changeMotDePasse(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable int id) {
        if (utilisateurService.getUtilisateurById(id).isPresent()) {
            utilisateurService.deleteUtilisateur(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}