package com.example.app_gestion_estudiantil.controller;

import com.example.app_gestion_estudiantil.entity.Archivo;
import com.example.app_gestion_estudiantil.entity.Foro;
import com.example.app_gestion_estudiantil.service.ArchivoService;
import com.example.app_gestion_estudiantil.service.ForoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/archivo")
public class ArchivoController {

    @Autowired
    private ArchivoService archivoService;

    @GetMapping("/getbuscarlista/{id_foro}")
    public ResponseEntity<List<Archivo>> getArchivosPorForo(@PathVariable Long id_foro){
        List<Archivo> archivos = archivoService.getArchivo(id_foro);

        if (archivos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Devuelve 404 si no se encontraron archivos
        }
        return new ResponseEntity<>(archivos, HttpStatus.OK);
    }


}
