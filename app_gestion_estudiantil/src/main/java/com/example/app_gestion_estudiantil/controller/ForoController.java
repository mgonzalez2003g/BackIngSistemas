package com.example.app_gestion_estudiantil.controller;

import com.example.app_gestion_estudiantil.entity.Foro;
import com.example.app_gestion_estudiantil.service.ForoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/foros")
public class ForoController {

    @Autowired
    private ForoService foroService;


    @PostMapping("/guardar")
    public ResponseEntity<?> guardarEstado(@RequestParam("contenido") String contenido,
                                           @RequestParam(value = "archivo", required = false) MultipartFile archivo,
                                           @RequestParam("idUsuario") Long idUsuario) {
        try {
            System.out.println("Archivo recibido: " + archivo);
            foroService.guardarForo(contenido, archivo, idUsuario);
            return ResponseEntity.ok("Estado guardado exitosamente");
        } catch (IOException | ParseException e) {
            System.out.println("Archivo recibido: " + archivo);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el estado");
        }

    }

    @PutMapping("/actulizar")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Foro>actulizarForo(@RequestBody Foro foro){
       Foro foroActulizado= foroService.update(foro);
       if (foroActulizado != null) {
           //dedito arriba
           return ResponseEntity.ok(foroActulizado);
       }
       else{
           //dedito abajo
           return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/getall")
    public List<Foro> getall(){
        System.out.println("controlador");
        return  foroService.getall();
    }

}





