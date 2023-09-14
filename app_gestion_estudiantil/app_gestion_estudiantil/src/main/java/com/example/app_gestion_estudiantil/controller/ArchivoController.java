package com.example.app_gestion_estudiantil.controller;

import com.example.app_gestion_estudiantil.service.ArchivoService;
import com.example.app_gestion_estudiantil.service.ForoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/archivo")
public class ArchivoController {

    @Autowired
    private ArchivoService archivoService;

   /* @PostMapping("/subir")


    public ResponseEntity<?> subirArchivo(@RequestParam("archivo") MultipartFile archivo) {
        try {
           archivoService.leerArchivo(archivo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }*/
}
