package com.example.app_gestion_estudiantil.controller;

import com.example.app_gestion_estudiantil.entity.Voto;
import com.example.app_gestion_estudiantil.repository.VotacionesCRUDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Voto")
public class VotoController {

    @Autowired
    private VotacionesCRUDRepository votoRepository;

    @Autowired
    public VotoController(VotacionesCRUDRepository votoRepository) {
        this.votoRepository = votoRepository;
    }

    @PostMapping("/votar")
    public ResponseEntity<String> votar(@RequestBody Voto voto) {
        // Aquí puedes agregar validaciones para asegurarte de que el usuario solo pueda votar una vez, si es necesario
        votoRepository.save(voto);
        return ResponseEntity.ok("Voto registrado correctamente.");
    }
}
