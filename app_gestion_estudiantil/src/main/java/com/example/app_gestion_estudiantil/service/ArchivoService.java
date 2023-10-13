package com.example.app_gestion_estudiantil.service;

import com.example.app_gestion_estudiantil.entity.Archivo;
import com.example.app_gestion_estudiantil.entity.Foro;
import com.example.app_gestion_estudiantil.repository.ArchivoCrudRepository;
import com.example.app_gestion_estudiantil.repository.ArchivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArchivoService {

    @Autowired
    private ArchivoRepository repository;

    @Autowired
    private ArchivoCrudRepository archivoCrudRepository;
    public Archivo leerArchivo(MultipartFile archivo, Foro foro) throws IOException {
        if (!archivo.isEmpty()) {
            String nombreArchivo = archivo.getOriginalFilename();
            nombreArchivo = foro.getId() + nombreArchivo;
            String rutaCarpetaDestino = "src/main/resources/static/images/";
            String rutaArchivoDestino = rutaCarpetaDestino +  nombreArchivo ;

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
                        .url(rutaArchivoDestino)  // Almacena solo el nombre del archivo
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


}
