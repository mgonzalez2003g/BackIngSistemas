package com.example.app_gestion_estudiantil.controller;

import com.example.app_gestion_estudiantil.entity.Encuesta;
import com.example.app_gestion_estudiantil.service.EncuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/encuestas")
public class EncuestaController {

    @Autowired
    private EncuestaService encuestaService;

        @PostMapping("/crear")
        public ResponseEntity<String> crearEncuesta(@RequestParam("titulo") String titulo,
                                                    @RequestParam("pregunta1") String pregunta1,
                                                    @RequestParam("pregunta2") String pregunta2,
                                                    @RequestParam("pregunta3") String pregunta3,
                                                    @RequestParam("pregunta4") String pregunta4,
                                                    @RequestParam("esOpcionUnica1") boolean esOpcionUnica1,
                                                    @RequestParam("esOpcionUnica2") boolean esOpcionUnica2,
                                                    @RequestParam("esOpcionUnica3") boolean esOpcionUnica3,
                                                    @RequestParam("esOpcionUnica4") boolean esOpcionUnica4,
                                                    @RequestParam("opciones1") String opciones1,
                                                    @RequestParam("opciones2") String opciones2,
                                                    @RequestParam("opciones3") String opciones3,
                                                    @RequestParam("opciones4") String opciones4,
                                                    @RequestParam("idUsuario") Long idUsuario) {
            try {
                encuestaService.crearEncuesta(titulo, pregunta1, pregunta2, pregunta3, pregunta4,
                        esOpcionUnica1, esOpcionUnica2, esOpcionUnica3, esOpcionUnica4,
                        opciones1, opciones2, opciones3, opciones4, idUsuario);
                return ResponseEntity.ok("Encuesta creada con éxito.");
            } catch (Exception e) {
                System.out.println(titulo + pregunta1 + pregunta2 + pregunta3 + pregunta4 +
                        esOpcionUnica1 + esOpcionUnica2 + esOpcionUnica3 + esOpcionUnica4 +
                        opciones1 + opciones2 + opciones3 + opciones4 + idUsuario);
                return ResponseEntity.badRequest().body("Error al crear la encuesta: " + e.getMessage());
            }
        }

}
