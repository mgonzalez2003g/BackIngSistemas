package com.example.app_gestion_estudiantil.service;


import com.example.app_gestion_estudiantil.entity.Archivo;
import com.example.app_gestion_estudiantil.entity.Foro;
import com.example.app_gestion_estudiantil.repository.ArchivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.nio.file.Files;

@Service
public class ArchivoService {

    @Autowired
    private ArchivoRepository repository;

    public Archivo leerArchivo(MultipartFile archivo, Foro foro) throws IOException {
        if (!archivo.isEmpty()) {
            String nombreArchivo = StringUtils.cleanPath(archivo.getOriginalFilename());
            String rutaCarpetaDestino = "/B:/andre/Docuemntos/Guardados de app_gestion_estudiantil";
            String rutaArchivoDestino = rutaCarpetaDestino + + foro.getId_foro() + nombreArchivo ;

            File carpetaDestino = new File(rutaCarpetaDestino);
            if (!carpetaDestino.exists()) {
                carpetaDestino.mkdirs(); // Crea la carpeta de destino si no existe
            }

            File archivoDestino = new File(rutaArchivoDestino);

            try (InputStream inputStream = archivo.getInputStream();
                 OutputStream outputStream = new FileOutputStream(archivoDestino)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                System.out.println("Archivo guardado exitosamente en: " + rutaArchivoDestino);
                Archivo archivito = Archivo.builder()
                        .nombre_archivo(nombreArchivo)
                        .url(rutaArchivoDestino)
                        .foro(foro)
                        .build();
                repository.save(archivito);
                System.out.println("Objeto File guardado exitosamente en la base de datos");
                return archivito;
            }
        } else {
            System.out.println("El archivo está vacío.");
            return null;
        }
    }

    public ResponseEntity<Resource> mostrarArchivo(String rutaArchivo)  throws IOException {
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            Resource resource = new FileSystemResource(archivo);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + archivo.getName());
            headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(archivo.toPath()));

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(archivo.length())
                    .contentType(MediaType.parseMediaType(Files.probeContentType(archivo.toPath())))
                    .body(resource);
        } else {
            // El archivo no existe
            return ResponseEntity.notFound().build();
        }
    }
}
